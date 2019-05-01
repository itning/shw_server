package top.itning.server.shwwork.exception;


import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import top.itning.server.common.exception.BaseException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author itning
 * @date 2019/4/29 18:11
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = getError(request);
        if (error instanceof BaseException) {
            BaseException baseException = (BaseException) error;
            Map<String, Object> errorAttributes = new LinkedHashMap<>();
            errorAttributes.put("code", baseException.getCode().value());
            errorAttributes.put("msg", baseException.getMsg());
            errorAttributes.put("data", "");
            return errorAttributes;
        } else if (error instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) error;
            Map<String, Object> errorAttributes = new LinkedHashMap<>();
            errorAttributes.put("code", responseStatusException.getStatus().value());
            errorAttributes.put("msg", responseStatusException.getMessage());
            errorAttributes.put("data", "");
            return errorAttributes;
        } else {
            Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
            errorAttributes.put("code", errorAttributes.getOrDefault("status", 404));
            errorAttributes.put("msg", error.getMessage());
            errorAttributes.put("data", "");
            return errorAttributes;
        }
    }
}
