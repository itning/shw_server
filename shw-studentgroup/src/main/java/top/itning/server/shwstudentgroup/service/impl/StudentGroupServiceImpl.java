package top.itning.server.shwstudentgroup.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwstudentgroup.client.GroupClient;
import top.itning.server.shwstudentgroup.client.entrty.Group;
import top.itning.server.shwstudentgroup.dto.StudentGroupDTO;
import top.itning.server.shwstudentgroup.entity.StudentGroup;
import top.itning.server.shwstudentgroup.repository.StudentGroupRepository;
import top.itning.server.shwstudentgroup.service.StudentGroupService;
import top.itning.server.shwstudentgroup.util.ReactiveMongoHelper;
import top.itning.server.shwstudentgroup.util.Tuple;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author itning
 * @date 2019/4/30 18:16
 */
@Service
public class StudentGroupServiceImpl implements StudentGroupService {
    private final GroupClient groupClient;
    private final StudentGroupRepository studentGroupRepository;
    private final ReactiveMongoHelper reactiveMongoHelper;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentGroupServiceImpl(GroupClient groupClient, StudentGroupRepository studentGroupRepository, ReactiveMongoHelper reactiveMongoHelper, ModelMapper modelMapper) {
        this.groupClient = groupClient;
        this.studentGroupRepository = studentGroupRepository;
        this.reactiveMongoHelper = reactiveMongoHelper;
        this.modelMapper = modelMapper;
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

    @Override
    public Mono<Void> dropOutGroup(String groupId, String studentId) {
        return studentGroupRepository.deleteById(studentId + "|" + groupId);
    }

    @Override
    public Mono<Page<StudentGroupDTO>> findStudentAllGroups(String studentNumber, int page, int size) {
        Map<String, Object> map = Collections.singletonMap("student_number", studentNumber);
        return reactiveMongoHelper.getAllWithCriteriaAndDescSortByPagination(page, size, "gmtCreate", map, StudentGroup.class)
                .map(p -> new Tuple<>(p.getContent()
                        .parallelStream()
                        //TODO Together Send Request With Cache
                        .map(s -> groupClient.findOneGroupById(s.getGroupID()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(group -> modelMapper.map(group, StudentGroupDTO.class))
                        .collect(Collectors.toList()), p))
                .map(t -> reactiveMongoHelper.getPage(t.getT2().getPageable(), t.getT1(), t.getT2().getTotalElements()));
    }

    @Override
    public Mono<Boolean> isHaveGroup(String studentNumber) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setStudentNumber(studentNumber);
        return studentGroupRepository.count(Example.of(studentGroup)).map(c -> c != 0L);
    }

    @Override
    public Flux<String> findGroupIdByStudentNumber(String studentNumber) {
        return reactiveMongoHelper.findFieldsByQuery("student_number", studentNumber, StudentGroup.class, "group_id").map(StudentGroup::getGroupID);
    }
}
