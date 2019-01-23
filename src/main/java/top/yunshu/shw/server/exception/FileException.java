package top.yunshu.shw.server.exception;

import org.springframework.http.HttpStatus;

/**
 * 文件异常
 *
 * @author itning
 */
public class FileException extends BaseException {
    public FileException(String msg, HttpStatus code) {
        super(msg, code);
    }
}
