package top.itning.server.shwstudentgroup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwstudentgroup.client.GroupClient;
import top.itning.server.shwstudentgroup.client.entrty.Group;
import top.itning.server.shwstudentgroup.entity.StudentGroup;
import top.itning.server.shwstudentgroup.repository.StudentGroupRepository;
import top.itning.server.shwstudentgroup.service.StudentGroupService;

/**
 * @author itning
 * @date 2019/4/30 18:16
 */
@Service
public class StudentGroupServiceImpl implements StudentGroupService {
    private final GroupClient groupClient;
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupServiceImpl(GroupClient groupClient, StudentGroupRepository studentGroupRepository) {
        this.groupClient = groupClient;
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public Mono<StudentGroup> joinGroup(String code, String studentNumber) {
        Group group = groupClient.findOneGroupById(code).orElseThrow(() -> new NoSuchFiledValueException("id " + code + " 不存在", HttpStatus.NOT_FOUND));
        StudentGroup s = new StudentGroup();
        s.setId(studentNumber + "|" + group.getId());
        return studentGroupRepository.count(Example.of(s))
                .flatMap(count -> {
                    if (count != 0) {
                        throw new NoSuchFiledValueException("已加入过该群", HttpStatus.CONFLICT);
                    }
                    StudentGroup studentGroup = new StudentGroup(studentNumber, group.getId());
                    return studentGroupRepository.save(studentGroup);
                });
    }
}
