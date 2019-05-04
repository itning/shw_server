package top.itning.server.shwgroup.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwgroup.entity.Group;
import top.itning.server.shwgroup.repository.GroupRepository;
import top.itning.server.shwgroup.service.GroupService;
import top.itning.server.shwgroup.stream.DelGroupMessage;
import top.itning.server.shwgroup.util.ReactiveMongoHelper;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author itning
 * @date 2019/4/29 12:34
 */
@Service
@EnableBinding(DelGroupMessage.class)
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ReactiveMongoHelper reactiveMongoHelper;
    private final DelGroupMessage delGroupMessage;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ReactiveMongoHelper reactiveMongoHelper, DelGroupMessage delGroupMessage) {
        this.groupRepository = groupRepository;
        this.reactiveMongoHelper = reactiveMongoHelper;
        this.delGroupMessage = delGroupMessage;
    }

    @Override
    public Mono<Group> createGroup(String groupName, String teacherName, String teacherId) {
        return groupRepository.save(new Group(groupName, teacherName, teacherId));
    }

    @Override
    public Mono<Void> deleteGroup(String teacherId, String groupId) {
        return groupRepository.findById(groupId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("id: " + groupId + " not found", HttpStatus.NOT_FOUND)))
                .flatMap(group -> {
                    if (!group.getTeacherNumber().equals(teacherId)) {
                        throw new NoSuchFiledValueException("FORBIDDEN", HttpStatus.FORBIDDEN);
                    }
                    delGroupMessage.delOutput().send(MessageBuilder.withPayload(group.getId()).build());
                    return groupRepository.deleteById(group.getId());
                });
    }

    @Override
    public Mono<Group> updateGroupName(String teacherId, String groupId, String newName) {
        return groupRepository.findById(groupId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("id: " + groupId + " not found", HttpStatus.NOT_FOUND)))
                .flatMap(group -> {
                    group.setGroupName(newName);
                    group.setGmtModified(new Date());
                    return groupRepository.save(group);
                });
    }

    @Override
    public Mono<Boolean> isHaveAnyGroup(String teacherNumber) {
        Group group = new Group();
        group.setTeacherNumber(teacherNumber);
        return groupRepository.exists(Example.of(group));
    }

    @Override
    public Mono<Page<Group>> findTeacherAllGroups(String teacherNumber, int page, int size) {
        Map<String, Object> map = Collections.singletonMap("teacher_number", teacherNumber);
        return reactiveMongoHelper.getAllWithCriteriaAndDescSortByPagination(page, size, "gmtCreate", map, Group.class);
    }

    @Override
    public Mono<Group> findOneGroupById(String groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public Flux<Group> findByTeacherNumber(String teacherNumber) {
        Group group = new Group();
        group.setTeacherNumber(teacherNumber);
        return groupRepository.findAll(Example.of(group));
    }

    @Override
    public Mono<String> findGroupNameByGroupId(String groupId) {
        return reactiveMongoHelper.findOneFieldsByQuery(Collections.singletonMap("id", groupId), Group.class, "groupName").map(Group::getGroupName);
    }

    @Override
    public Mono<String> findTeacherNameById(String groupId) {
        return reactiveMongoHelper.findOneFieldsByQuery(Collections.singletonMap("id", groupId), Group.class, "teacherName").map(Group::getTeacherName);
    }
}
