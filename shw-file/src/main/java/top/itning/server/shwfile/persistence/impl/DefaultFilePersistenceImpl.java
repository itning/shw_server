package top.itning.server.shwfile.persistence.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.shwfile.persistence.FilePersistence;

import java.io.File;

/**
 * @author itning
 * @date 2019/5/3 11:56
 */
@Component
public class DefaultFilePersistenceImpl implements FilePersistence {
    @Override
    public boolean file2disk(MultipartFile file, String saveFileName) {
        String dir = "C:\\Users\\wangn\\Desktop";
        File saveFile = new File(dir + File.separator + saveFileName);
        try {
            file.transferTo(saveFile);
            return saveFile.exists() && saveFile.length() == file.getSize();
        } catch (Exception e) {
            return false;
        }
    }
}
