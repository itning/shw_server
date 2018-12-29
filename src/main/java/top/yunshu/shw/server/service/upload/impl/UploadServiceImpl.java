package top.yunshu.shw.server.service.upload.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.dao.WorkDao;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.exception.NullFiledException;
import top.yunshu.shw.server.exception.PermissionsException;
import top.yunshu.shw.server.service.upload.UploadService;

/**
 * 上传历史服务实现
 *
 * @author itning
 */
@Service
public class UploadServiceImpl implements UploadService {

    private final UploadDao uploadDao;

    private final WorkDao workDao;

    @Autowired
    public UploadServiceImpl(UploadDao uploadDao, WorkDao workDao) {
        this.uploadDao = uploadDao;
        this.workDao = workDao;
    }


    @Override
    public Upload getUploadInfoByWorkId(String studentId, String workId) {
        return uploadDao.findUploadByStudentIdAndWorkId(studentId, workId);
    }

    @Override
    public void delUploadInfoByWorkId(String studentId, String workId) {
        if (!uploadDao.existsByStudentIdAndWorkId(studentId, workId)) {
            throw new NullFiledException("不存在这个作业信息", HttpStatus.NOT_FOUND);
        }
        Work work = workDao.findById(workId).orElseThrow(() -> {
            //错误:学生Upload有但是教师已经删除这个Work
            //直接删除这个历史记录并抛异常
            uploadDao.delete(uploadDao.findUploadByStudentIdAndWorkId(studentId, workId));
            return new NullFiledException("不存在这个作业信息", HttpStatus.NOT_FOUND);
        });
        if (!work.isEnabled()) {
            throw new PermissionsException("不能删除未启用作业");
        }
        Upload upload = uploadDao.findUploadByStudentIdAndWorkId(studentId, workId);
        uploadDao.delete(upload);
    }
}
