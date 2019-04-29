package top.itning.server.shwgroup.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwgroup.entity.Group;
import top.itning.server.shwgroup.repository.GroupRepository;
import top.itning.server.shwgroup.service.GroupService;

import java.util.Date;
import java.util.UUID;

/**
 * @author itning
 * @date 2019/4/29 12:34
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
}
