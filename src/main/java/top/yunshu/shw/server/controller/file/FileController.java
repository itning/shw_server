package top.yunshu.shw.server.controller.file;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.yunshu.shw.server.service.file.FileService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 * 文件上传下载
 *
 * @author itning
 */
@Controller
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/student/down_preview/{studentNumber}/{workId}")
    public void preview(@PathVariable String studentNumber,
                        @PathVariable String workId,
                        @RequestParam(defaultValue = "UTF-8") String encoding,
                        HttpServletResponse response) {
        response.setCharacterEncoding(encoding);
        fileService.getFile(studentNumber, workId).ifPresent(file -> {
            try (ServletOutputStream outputStream = response.getOutputStream();
                 FileInputStream fileInputStream = new FileInputStream(file)) {
                String contentType = Files.probeContentType(file.toPath());
                if (contentType.equals(MediaType.TEXT_HTML_VALUE)) {
                    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                } else {
                    response.setContentType(contentType);
                }
                IOUtils.copy(fileInputStream, outputStream);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
