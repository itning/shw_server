package top.itning.server.shwfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.common.exception.RoleException;
import top.itning.server.common.model.LoginUser;
import top.itning.server.shwfile.service.FileService;

import javax.servlet.http.HttpServletResponse;

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
                                           @RequestParam("userType") String userType,
                                           @PathVariable String workId,
                                           @RequestParam("file") MultipartFile file) {
        if (!userType.equals(LoginUser.STUDENT_USER)) {
            throw new RoleException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
        fileService.uploadFile(file, studentNumber, workId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 学生作业下载
     *
     * @param range         请求头
     * @param studentNumber 学号
     * @param workId        作业ID
     * @param response      {@link HttpServletResponse}
     */
    @GetMapping("/down/{studentNumber}/{workId}")
    public void downloadFile(@RequestHeader(required = false) String range,
                             @PathVariable String studentNumber,
                             @PathVariable String workId,
                             HttpServletResponse response) {
        fileService.downFile(studentNumber, workId, range, response);
    }


    /**
     * 直接下载所有
     *
     * @param workId   作业ID
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/down_now/{workId}")
    public void downloadAllFileNow(@PathVariable String workId,
                                   HttpServletResponse response) {
        fileService.downFiles(workId, response);
    }
}
