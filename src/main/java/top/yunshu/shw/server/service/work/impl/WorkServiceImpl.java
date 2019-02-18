package top.yunshu.shw.server.service.work.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.*;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.Student;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.model.WorkDetailsModel;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.work.WorkService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 作业服务实现类
 *
 * @author itning
 */
@Service
public class WorkServiceImpl implements WorkService {
    private static final Logger logger = LoggerFactory.getLogger(WorkServiceImpl.class);

    private final WorkDao workDao;

    private final StudentGroupDao studentGroupDao;

    private final UploadDao uploadDao;

    private final GroupDao groupDao;

    private final StudentDao studentDao;

    private final ModelMapper modelMapper;


    @Autowired
    public WorkServiceImpl(WorkDao workDao, StudentGroupDao studentGroupDao, UploadDao uploadDao, GroupDao groupDao, StudentDao studentDao, ModelMapper modelMapper) {
        this.workDao = workDao;
        this.studentGroupDao = studentGroupDao;
        this.uploadDao = uploadDao;
        this.groupDao = groupDao;
        this.studentDao = studentDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<WorkModel> getStudentUnDoneWork(String studentId, Pageable pageable) {
        List<Work> workList = studentGroupDao.findGroupIdByStudentNumber(studentId)
                .parallelStream()
                .map(workDao::findAllByGroupIdAndEnabledIsTrue)
                .flatMap(Collection::stream)
                .filter(work -> !uploadDao.existsByStudentIdAndWorkId(studentId, work.getId()))
                .sorted(Comparator.comparing(Work::getGmtCreate).reversed())
                .collect(Collectors.toList());
        return getWorkModels(pageable, workList);
    }

    @Override
    public Page<WorkModel> getStudentDoneWork(String studentId, Pageable pageable) {
        List<Work> workList = studentGroupDao.findGroupIdByStudentNumber(studentId)
                .parallelStream()
                .map(workDao::findAllByGroupId)
                .flatMap(Collection::stream)
                .filter(work -> uploadDao.existsByStudentIdAndWorkId(studentId, work.getId()))
                .peek(work -> {
                    work.setGmtCreate(uploadDao.findUploadByStudentIdAndWorkId(studentId, work.getId()).getGmtCreate());
                    work.setGmtModified(uploadDao.findUploadByStudentIdAndWorkId(studentId, work.getId()).getGmtModified());
                })
                .sorted(Comparator.comparing(Work::getGmtCreate).reversed())
                .collect(Collectors.toList());
        return getWorkModels(pageable, workList);
    }

    @Override
    public Page<WorkModel> getTeacherAllWork(String teacherNumber, Pageable pageable) {
        List<Work> workList = groupDao.findByTeacherNumber(teacherNumber)
                .parallelStream()
                .map(group -> workDao.findAllByGroupId(group.getId()))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Work::getGmtCreate).reversed())
                .collect(Collectors.toList());
        return getWorkModels(pageable, workList);
    }

    @Override
    public Page<WorkModel> getTeacherWork(String teacherNumber, String groupId, Pageable pageable) {
        Group group = groupDao.findById(groupId).orElseThrow(() -> new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND));
        Page<Work> workPage = workDao.findAllByGroupId(group.getId(), pageable);
        List<Work> workPageContent = workPage.getContent();
        return new PageImpl<>(modelMapper.map(workPageContent, new TypeToken<List<WorkModel>>() {
        }.getType()), pageable, workPage.getTotalElements());
    }

    @Override
    public Work createWork(String workName, String groupId, String format, boolean enabled) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND);
        }
        Work work = new Work();
        work.setId(UUID.randomUUID().toString().replace("-", ""));
        work.setGroupId(groupId);
        work.setWorkName(workName);
        work.setEnabled(enabled);
        work.setFileNameFormat(format);
        return workDao.save(work);
    }

    @Override
    public void changeEnabledWord(String workId, boolean enabled) {
        Work work = workDao.findById(workId).orElseThrow(() -> new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.NOT_FOUND));
        work.setEnabled(enabled);
        workDao.save(work);
    }

    @Override
    public void delWork(String workId, String teacherNumber) {
        Work work = workDao.findById(workId).orElseThrow(() -> new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.NOT_FOUND));
        Group group = groupDao.findById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID: " + work.getGroupId() + "不存在", HttpStatus.INTERNAL_SERVER_ERROR));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("Forbidden", HttpStatus.FORBIDDEN);
        }
        workDao.delete(work);
    }

    @Override
    public Page<WorkDetailsModel> getWorkDetailByWorkId(String teacherNumber, String workId, Pageable pageable) {
        Work work = workDao.findById(workId).orElseThrow(() -> new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.NOT_FOUND));
        Group group = groupDao.findById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID: " + work.getGroupId() + "不存在", HttpStatus.NOT_FOUND));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.FORBIDDEN);
        }
        //所有加入该群组的学生
        List<WorkDetailsModel> workDetailsModels = studentGroupDao.findAllByGroupID(work.getGroupId(), pageable).getContent().parallelStream().map(studentGroup -> {
            //根据学生学号和作业ID查找所有已上传的信息
            Upload upload = uploadDao.findUploadByStudentIdAndWorkId(studentGroup.getStudentNumber(), workId);
            //查找学生信息
            Student student = studentDao.findById(studentGroup.getStudentNumber()).orElse(new Student());
            student.setNo(studentGroup.getStudentNumber());
            student.setLoginName("");
            WorkDetailsModel workDetailsModel = new WorkDetailsModel();
            workDetailsModel.setUpload(upload);
            workDetailsModel.setStudent(student);
            workDetailsModel.setWorkName(work.getWorkName());
            workDetailsModel.setUp(upload != null);
            workDetailsModel.setGroupName(group.getGroupName());
            return workDetailsModel;
        }).collect(Collectors.toList());
        return new PageImpl<>(workDetailsModels, pageable, studentGroupDao.countAllByGroupID(work.getGroupId()));
    }

    @Override
    public Optional<Work> getOneWorkById(String workId) {
        return workDao.findById(workId);
    }

    /**
     * 分页获取WorkModel
     *
     * @param pageable {@link Pageable}
     * @param workList 作业集合
     * @return WorkModel
     */
    private Page<WorkModel> getWorkModels(Pageable pageable, List<Work> workList) {
        //TODO 直接查所有分页意义不大，解决：Cache List<Work>
        //页数*每页条数
        int to = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        int toIndex = to > workList.size() ? workList.size() : to;
        List<Work> works;
        try {
            works = workList.subList(Math.toIntExact(pageable.getOffset()), toIndex);
        } catch (Exception e) {
            works = new ArrayList<>(0);
        }
        return new PageImpl<>(modelMapper.map(works, new TypeToken<List<WorkModel>>() {
        }.getType()), pageable, workList.size());
    }
}
