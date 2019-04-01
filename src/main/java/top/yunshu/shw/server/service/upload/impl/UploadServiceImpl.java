package top.yunshu.shw.server.service.upload.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.dao.*;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.Notice;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.exception.NullFiledException;
import top.yunshu.shw.server.exception.PermissionsException;
import top.yunshu.shw.server.service.upload.UploadService;
import top.yunshu.shw.server.util.FileNameSpecificationUtils;
import top.yunshu.shw.server.util.FileUtils;

import javax.transaction.Transactional;

/**
 * 上传历史服务实现
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UploadServiceImpl implements UploadService {

    private final UploadDao uploadDao;

    private final WorkDao workDao;

    private final NoticeDao noticeDao;

    private final FileDao fileDao;

    private final StudentDao studentDao;

    @Autowired
    public UploadServiceImpl(UploadDao uploadDao, WorkDao workDao, NoticeDao noticeDao, FileDao fileDao, StudentDao studentDao) {
        this.uploadDao = uploadDao;
        this.workDao = workDao;
        this.noticeDao = noticeDao;
        this.fileDao = fileDao;
        this.studentDao = studentDao;
    }


    @Override
    public Upload getUploadInfoByWorkId(String studentId, String workId) {
        return uploadDao.findUploadByStudentIdAndWorkId(studentId, workId);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "studentDoneWork", allEntries = true),
            @CacheEvict(cacheNames = "studentUndoneWork", allEntries = true),
            @CacheEvict(cacheNames = "workDetail", allEntries = true)
    })
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
        String[] format = FileNameSpecificationUtils.safeGetStudentNameAndFileNameFormat(studentDao, workDao, studentId, workId);
        fileDao.deleteFile(workId, studentId, FileNameSpecificationUtils.getFileName(studentId, format[0], format[1]) + upload.getExtensionName());
        uploadDao.delete(upload);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "studentDoneWork", allEntries = true),
            @CacheEvict(cacheNames = "studentUndoneWork", allEntries = true),
            @CacheEvict(cacheNames = "workDetail", allEntries = true)
    })
    @Override
    public void uploadFile(MultipartFile file, String studentNumber, String workId) {
        String[] format = FileNameSpecificationUtils.safeGetStudentNameAndFileNameFormat(studentDao, workDao, studentNumber, workId);
        Upload upload = new Upload();
        upload.setStudentId(studentNumber);
        upload.setWorkId(workId);
        upload.setMime(file.getContentType());
        upload.setSize(file.getSize());
        upload.setExtensionName(FileUtils.getExtensionName(file));
        fileDao.createFile(file, workId, studentNumber, FileNameSpecificationUtils.getFileName(studentNumber, format[0], format[1]));
        uploadDao.save(upload);
    }

    @Override
    public long getUploadSum(String workId) {
        return uploadDao.findSizeByWorkId(workId).stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public void reviewWork(LoginUser loginUser, String workId, String studentId, String review) {
        Upload upload = uploadDao.findUploadByStudentIdAndWorkId(studentId, workId);
        if (upload == null) {
            throw new NullFiledException("不存在该作业", HttpStatus.NOT_FOUND);
        }
        upload.setReview(review);
        uploadDao.save(upload);
        if (!noticeDao.findById(studentId + workId).isPresent()) {
            String workName = workDao.findById(workId).map(Work::getWorkName).orElse("N/A");
            Notice notice = Notice.createReviewInstance(workId, studentId, loginUser.getName(), loginUser.getNo(), studentId, "", workName);
            noticeDao.save(notice);
        }
    }

    @Override
    public String reviewWork(String workId, String studentId) {
        return uploadDao.findReviewByStudentIdAndWorkId(studentId, workId);
    }
}
