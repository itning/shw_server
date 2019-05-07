package top.itning.server.shwwork.service.impl;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.common.exception.PermissionsException;
import top.itning.server.shwwork.client.GroupClient;
import top.itning.server.shwwork.client.SecurityClient;
import top.itning.server.shwwork.client.StudentGroupClient;
import top.itning.server.shwwork.client.UploadClient;
import top.itning.server.shwwork.client.entity.Group;
import top.itning.server.shwwork.client.entity.Student;
import top.itning.server.shwwork.client.entity.StudentGroup;
import top.itning.server.shwwork.client.entity.Upload;
import top.itning.server.shwwork.dto.WorkDTO;
import top.itning.server.shwwork.dto.WorkDetailsDTO;
import top.itning.server.shwwork.entity.Work;
import top.itning.server.shwwork.repository.WorkRepository;
import top.itning.server.shwwork.service.WorkService;
import top.itning.server.shwwork.stream.DelWorkMessage;
import top.itning.server.shwwork.util.ReactiveMongoHelper;
import top.itning.server.shwwork.util.Tuple3;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作业服务实现
 *
 * @author itning
 * @date 2019/5/1 9:49
 */
@Service
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;
    private final StudentGroupClient studentGroupClient;
    private final ReactiveMongoHelper reactiveMongoHelper;
    private final GroupClient groupClient;
    private final UploadClient uploadClient;
    private final SecurityClient securityClient;
    private final DelWorkMessage delWorkMessage;

    @Autowired
    public WorkServiceImpl(WorkRepository workRepository, StudentGroupClient studentGroupClient, ReactiveMongoHelper reactiveMongoHelper, GroupClient groupClient, UploadClient uploadClient, SecurityClient securityClient, DelWorkMessage delWorkMessage) {
        this.workRepository = workRepository;
        this.studentGroupClient = studentGroupClient;
        this.reactiveMongoHelper = reactiveMongoHelper;
        this.groupClient = groupClient;
        this.uploadClient = uploadClient;
        this.securityClient = securityClient;
        this.delWorkMessage = delWorkMessage;
    }

    @Override
    public Mono<Page<WorkDTO>> getStudentUnDoneWork(String studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Work w = new Work();
        w.setEnabled(true);
        return Flux.fromStream(studentGroupClient.findGroupIdByStudentNumber(studentId).stream())
                .flatMap(groupId -> {
                    Work work = w.clones();
                    work.setGroupId(groupId);
                    return workRepository.findAll(Example.of(work));
                })
                .filter(work -> !uploadClient.existsById(studentId + "|" + work.getId()))
                .sort(Comparator.comparing(Work::getGmtCreate).reversed())
                .collectList()
                .map(works -> reactiveMongoHelper.getPageWithAllContents(pageRequest, works, new TypeToken<List<WorkDTO>>() {
                }.getType()));
    }

    @Override
    public Mono<Page<WorkDTO>> getStudentDoneWork(String studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Work w = new Work();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("enabled");
        return Flux.fromStream(studentGroupClient.findGroupIdByStudentNumber(studentId).stream())
                .flatMap(groupId -> {
                    Work work = w.clones();
                    work.setGroupId(groupId);
                    return workRepository.findAll(Example.of(work, matcher));
                })
                .filter(work -> uploadClient.existsById(studentId + "|" + work.getId()))
                .map(work -> {
                    Upload upload = uploadClient.findOneById(studentId + "|" + work.getId()).orElseThrow(() -> new NoSuchFiledValueException("上传信息不存在", HttpStatus.NOT_FOUND));
                    work.setGmtCreate(upload.getGmtCreate());
                    work.setGmtModified(upload.getGmtModified());
                    return work;
                })
                .sort(Comparator.comparing(Work::getGmtCreate).reversed())
                .collectList()
                .map(works -> reactiveMongoHelper.getPageWithAllContents(pageRequest, works, new TypeToken<List<WorkDTO>>() {
                }.getType()));
    }

    @Override
    public Mono<Page<WorkDTO>> getTeacherAllWork(String teacherNumber, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Work w = new Work();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("enabled");
        return Flux.fromStream(groupClient.findByTeacherNumber(teacherNumber).stream())
                .flatMap(group -> {
                    w.setGroupId(group.getId());
                    return workRepository.findAll(Example.of(w, matcher));
                })
                .sort(Comparator.comparing(Work::getGmtCreate).reversed())
                .collectList()
                .map(works -> reactiveMongoHelper.getPageWithAllContents(pageRequest, works, new TypeToken<List<WorkDTO>>() {
                }.getType()));
    }

    @Override
    public Mono<Page<WorkDTO>> getTeacherWork(String teacherNumber, String groupId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Work w = new Work();
        w.setGroupId(groupId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("enabled");
        return Mono.justOrEmpty(groupClient.findOneGroupById(groupId))
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND)))
                .map(group -> {
                    if (!group.getTeacherNumber().equals(teacherNumber)) {
                        throw new PermissionsException("not found");
                    }
                    return workRepository.findAll(Example.of(w, matcher));
                })
                .flatMap(Flux::collectList)
                .map(works -> reactiveMongoHelper.getPageWithAllContents(pageRequest, works, new TypeToken<List<WorkDTO>>() {
                }.getType()));
    }

    @Override
    public Mono<Work> createWork(String teacherNumber, String workName, String groupId, String format, boolean enabled) {
        return Mono.justOrEmpty(groupClient.findOneGroupById(groupId))
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND)))
                .flatMap(group -> {
                    if (!group.getTeacherNumber().equals(teacherNumber)) {
                        throw new PermissionsException("not found");
                    }
                    return workRepository.save(new Work(groupId, workName, enabled, format));
                });
    }

    @Override
    public Mono<Work> changeWorkEnabledStatus(String teacherNumber, String workId, boolean enabled) {
        return workRepository.findById(workId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业ID " + workId + " 不存在", HttpStatus.NOT_FOUND)))
                .flatMap(work -> {
                    Group group = groupClient.findOneGroupById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID " + work.getGroupId() + " 不存在", HttpStatus.NOT_FOUND));
                    if (!group.getTeacherNumber().equals(teacherNumber)) {
                        throw new PermissionsException("not found");
                    }
                    work.setEnabled(enabled);
                    work.setGmtModified(new Date());
                    return workRepository.save(work);
                });
    }

    @Override
    public Mono<Work> changeWorkName(String teacherNumber, String workId, String workName) {
        return workRepository.findById(workId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业ID " + workId + " 不存在", HttpStatus.NOT_FOUND)))
                .flatMap(work -> {
                    Group group = groupClient.findOneGroupById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID " + work.getGroupId() + " 不存在", HttpStatus.NOT_FOUND));
                    if (!group.getTeacherNumber().equals(teacherNumber)) {
                        throw new PermissionsException("not found");
                    }
                    work.setWorkName(workName);
                    work.setGmtModified(new Date());
                    return workRepository.save(work);
                });
    }

    @Override
    public Mono<Void> delWork(String workId, String teacherNumber) {
        return workRepository.findById(workId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业ID " + workId + " 不存在", HttpStatus.NOT_FOUND)))
                .flatMap(work -> {
                    Group group = groupClient.findOneGroupById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID " + work.getGroupId() + " 不存在", HttpStatus.NOT_FOUND));
                    if (!group.getTeacherNumber().equals(teacherNumber)) {
                        throw new PermissionsException("not found");
                    }
                    delWorkMessage.delOutput().send(MessageBuilder.withPayload(work.getId()).build());
                    return workRepository.delete(work);
                });
    }

    @Override
    public Mono<Page<WorkDetailsDTO>> getWorkDetailByWorkId(String teacherNumber, String workId, int page, int size) {
        return workRepository.findById(workId)
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("作业ID " + workId + " 不存在", HttpStatus.NOT_FOUND)))
                .map(work -> {
                    Group group = groupClient.findOneGroupById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID: " + work.getGroupId() + "不存在", HttpStatus.NOT_FOUND));
                    if (!group.getTeacherNumber().equals(teacherNumber)) {
                        throw new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.FORBIDDEN);
                    }
                    List<StudentGroup> studentGroupPage = studentGroupClient.findAllByGroupID(group.getId(), page, size);
                    return new Tuple3<>(work, group, studentGroupPage);
                })
                .map(tuple3 -> {
                    long count = studentGroupClient.countAllByGroupID(tuple3.getT2().getId());
                    List<WorkDetailsDTO> workDetailsDTOList = tuple3.getT3().parallelStream().map(studentGroup -> {
                        Upload upload = uploadClient.findOneById(studentGroup.getStudentNumber() + "|" + workId).orElse(null);
                        Student student = securityClient.findStudentById(studentGroup.getStudentNumber()).orElse(new Student());
                        student.setNo(studentGroup.getStudentNumber());
                        student.setLoginName("");
                        WorkDetailsDTO workDetailsDTO = new WorkDetailsDTO();
                        workDetailsDTO.setUpload(upload);
                        workDetailsDTO.setStudent(student);
                        workDetailsDTO.setWorkName(tuple3.getT1().getWorkName());
                        workDetailsDTO.setUp(upload != null);
                        workDetailsDTO.setGroupName(tuple3.getT2().getGroupName());
                        return workDetailsDTO;
                    }).collect(Collectors.toList());
                    return new PageImpl<>(workDetailsDTOList, PageRequest.of(page, size), count);
                });
    }

    @Override
    public Flux<Work> getAllWorkInfoByGroupId(String groupId) {
        Work w = new Work();
        w.setGroupId(groupId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("enabled");
        return workRepository.findAll(Example.of(w, matcher));
    }

    @Override
    public Mono<Void> teacherDelGroupFromMessage(String groupId) {
        Work w = new Work();
        w.setGroupId(groupId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("enabled");
        Flux<Work> workFlux = workRepository.findAll(Example.of(w, matcher))
                .map(work -> {
                    delWorkMessage.delOutput().send(MessageBuilder.withPayload(work.getId()).build());
                    return work;
                });
        return workRepository.deleteAll(workFlux);
    }

    @Override
    public Mono<Work> getOneWorkById(String workId) {
        return workRepository.findById(workId);
    }
}
