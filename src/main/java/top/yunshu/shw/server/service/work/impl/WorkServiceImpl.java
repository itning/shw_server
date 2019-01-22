package top.yunshu.shw.server.service.work.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.*;
import top.yunshu.shw.server.entity.*;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.model.WorkDetailsModel;
import top.yunshu.shw.server.service.work.WorkService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
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

    @Autowired
    public WorkServiceImpl(WorkDao workDao, StudentGroupDao studentGroupDao, UploadDao uploadDao, GroupDao groupDao, StudentDao studentDao) {
        this.workDao = workDao;
        this.studentGroupDao = studentGroupDao;
        this.uploadDao = uploadDao;
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    @Override
    public List<Work> getStudentUnDoneWork(String studentId) {
        return studentGroupDao.findGroupIdByStudentNumber(studentId).parallelStream().map(workDao::findAllByGroupIdAndEnabledIsTrue).flatMap(Collection::stream).filter(work -> !uploadDao.existsByStudentIdAndWorkId(studentId, work.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Work> getStudentDoneWork(String studentId) {
        return studentGroupDao.findGroupIdByStudentNumber(studentId).parallelStream().map(workDao::findAllByGroupId).flatMap(Collection::stream).filter(work -> uploadDao.existsByStudentIdAndWorkId(studentId, work.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Work> getTeacherAllWork(String teacherNumber) {
        return groupDao.findByTeacherNumber(teacherNumber).parallelStream().map(group -> workDao.findAllByGroupId(group.getId())).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public List<Work> getTeacherWork(String teacherNumber, String groupId) {
        Group group = groupDao.findById(groupId).orElseThrow(() -> new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND));
        return workDao.findAllByGroupId(group.getId());
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
    public List<WorkDetailsModel> getWorkDetailByWorkId(String teacherNumber, String workId) {
        Work work = workDao.findById(workId).orElseThrow(() -> new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.NOT_FOUND));
        Group group = groupDao.findById(work.getGroupId()).orElseThrow(() -> new NoSuchFiledValueException("群ID: " + work.getGroupId() + "不存在", HttpStatus.NOT_FOUND));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("作业ID: " + workId + "不存在", HttpStatus.FORBIDDEN);
        }
        //所有加入该群组的学生
        List<StudentGroup> studentGroupList = studentGroupDao.findAllByGroupID(work.getGroupId());
        return studentGroupList.parallelStream().map(studentGroup -> {
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
    }
}
