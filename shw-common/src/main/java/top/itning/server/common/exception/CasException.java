package top.itning.server.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Cas Exception
 *
 * @author itning
 */
public class CasException extends BaseException {
    public CasException(String msg, HttpStatus code) {
        super(msg, code);
    }
}
