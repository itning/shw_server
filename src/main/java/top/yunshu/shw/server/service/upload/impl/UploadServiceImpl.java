package top.yunshu.shw.server.service.upload.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.service.upload.UploadService;

/**
 * 上传历史服务实现
 *
 * @author itning
 */
@Service
public class UploadServiceImpl implements UploadService {

    private final UploadDao uploadDao;

    @Autowired
    public UploadServiceImpl(UploadDao uploadDao) {
        this.uploadDao = uploadDao;
    }

    @Override
    public Upload getUploadInfoByWorkId(String studentId, String workId) {
        return uploadDao.findUploadByStudentIdAndWorkId(studentId, workId);
    }
}
