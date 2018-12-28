package top.yunshu.shw.server.service.work.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.StudentGroupDao;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.dao.WorkDao;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.service.work.WorkService;

import java.util.Collection;
import java.util.List;
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

    @Autowired
    public WorkServiceImpl(WorkDao workDao, StudentGroupDao studentGroupDao, UploadDao uploadDao) {
        this.workDao = workDao;
        this.studentGroupDao = studentGroupDao;
        this.uploadDao = uploadDao;
    }

    @Override
    public List<Work> getStudentUnDoneWork(String studentId) {
        return studentGroupDao.findGroupIdByStudentNumber(studentId).parallelStream().map(workDao::findAllByGroupIdAndEnabledIsTrue).flatMap(Collection::stream).filter(work -> !uploadDao.existsByStudentIdAndWorkId(studentId, work.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Work> getStudentDoneWork(String studentId) {
        return studentGroupDao.findGroupIdByStudentNumber(studentId).parallelStream().map(workDao::findAllByGroupId).flatMap(Collection::stream).filter(work -> uploadDao.existsByStudentIdAndWorkId(studentId, work.getId())).collect(Collectors.toList());
    }
}
