package top.itning.server.shwfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.shwfile.service.FileService;

/**
 * @author itning
 * @date 2019/5/3 11:14
 */
@RestController
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 学生上传作业
     *
     * @param workId 作业ID
     * @param file   文件
     * @return ResponseEntity
     */
    @PostMapping("/up/{workId}")
    public ResponseEntity<Void> uploadWork(@RequestParam("no") String studentNumber,
                                           @PathVariable String workId,
                                           @RequestParam("file") MultipartFile file) {
        fileService.uploadFile(file, studentNumber, workId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
