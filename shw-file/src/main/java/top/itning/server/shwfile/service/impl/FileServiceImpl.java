package top.itning.server.shwfile.service.impl;


import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.shwfile.persistence.FilePersistence;
import top.itning.server.shwfile.pojo.FileUploadMetaData;
import top.itning.server.shwfile.service.FileService;
import top.itning.server.shwfile.stream.UploadMessage;
import top.itning.server.shwfile.util.FileUtils;

/**
 * @author itning
 * @date 2019/5/3 11:16
 */
@Service
public class FileServiceImpl implements FileService {
    private final FilePersistence filePersistence;
    private final UploadMessage uploadMessage;

    public FileServiceImpl(FilePersistence filePersistence, UploadMessage uploadMessage) {
        this.filePersistence = filePersistence;
        this.uploadMessage = uploadMessage;
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
            uploadMessage.uploadOutput().send(MessageBuilder.withPayload(fileUploadMetaData).build());
        } else {
            throw new RuntimeException("未知存储失败");
        }
    }
}
