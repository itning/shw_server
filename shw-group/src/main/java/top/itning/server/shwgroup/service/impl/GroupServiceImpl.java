package top.itning.server.shwgroup.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwgroup.entity.Group;
import top.itning.server.shwgroup.repository.GroupRepository;
import top.itning.server.shwgroup.service.GroupService;
import top.itning.server.shwgroup.util.ReactiveMongoPageHelper;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author itning
 * @date 2019/4/29 12:34
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ReactiveMongoPageHelper reactiveMongoPageHelper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ReactiveMongoPageHelper reactiveMongoPageHelper) {
        this.groupRepository = groupRepository;
        this.reactiveMongoPageHelper = reactiveMongoPageHelper;
    }

    @Override
    public Mono<Group> createGroup(String groupName, String teacherName, String teacherId) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        return groupRepository.save(new Group(id, groupName, teacherName, teacherId, id, date, date));
    }

    @Override
    public Mono<Void> deleteGroup(String teacherId, String groupId) {
        return groupRepository.findById(groupId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("id: " + groupId + " not found", HttpStatus.NOT_FOUND)))
                .flatMap(group -> {
                    if (!group.getTeacherNumber().equals(teacherId)) {
                        throw new NoSuchFiledValueException("FORBIDDEN", HttpStatus.FORBIDDEN);
                    }
                    return groupRepository.deleteById(groupId);
                });
    }

    @Override
    public Mono<Group> updateGroupName(String teacherId, String groupId, String newName) {
        return groupRepository.findById(groupId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("id: " + groupId + " not found", HttpStatus.NOT_FOUND)))
                .flatMap(group -> {
                    group.setGroupName(newName);
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
        return reactiveMongoPageHelper.getAllWithCriteriaAndDescSortByPagination(page, size, "gmtCreate", map, Group.class);
    }

    @Override
    public Mono<Group> findOneGroupById(String groupId) {
        return groupRepository.findById(groupId);
    }
}
