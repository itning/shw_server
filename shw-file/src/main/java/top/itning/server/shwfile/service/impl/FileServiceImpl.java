package top.itning.server.shwfile.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.shwfile.client.UploadClient;
import top.itning.server.shwfile.persistence.FilePersistence;
import top.itning.server.shwfile.pojo.FileUploadMetaData;
import top.itning.server.shwfile.service.FileService;
import top.itning.server.shwfile.util.FileUtils;

/**
 * @author itning
 * @date 2019/5/3 11:16
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FilePersistence filePersistence;
    private final UploadClient uploadClient;

    public FileServiceImpl(FilePersistence filePersistence, UploadClient uploadClient) {
        this.filePersistence = filePersistence;
        this.uploadClient = uploadClient;
    }

    @Override
    public void uploadFile(MultipartFile file, String studentNumber, String workId) {
        boolean saved = filePersistence.file2disk(file, studentNumber + workId);
        if (saved) {
            FileUploadMetaData fileUploadMetaData = new FileUploadMetaData();
            fileUploadMetaData.setStudentId(studentNumber);
            fileUploadMetaData.setWorkId(workId);
            fileUploadMetaData.setMime(file.getContentType());
            fileUploadMetaData.setExtensionName(FileUtils.getExtensionName(file));
            fileUploadMetaData.setSize(file.getSize());
            uploadClient.saveOne(fileUploadMetaData);
        } else {
            throw new RuntimeException("未知存储失败");
        }
    }

    @Override
    public void delFile(String studentNumber, String workId) {
        boolean deleted = filePersistence.fileDel(studentNumber + workId);
        if (!deleted) {
            logger.warn("student number {} del work id {} failure", studentNumber, workId);
        }
    }
}
