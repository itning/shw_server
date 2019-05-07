package top.itning.server.shwgateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 错误处理器
 *
 * @author itning
 * @date 2019/5/2 18:49
 */
@RestController
public class ErrorHandler implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, true);
        String msg = errorAttributes.getOrDefault("error", "not found").toString();
        String code = errorAttributes.getOrDefault("status", 404).toString();
        return "{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":\"\"}";
    }
}
