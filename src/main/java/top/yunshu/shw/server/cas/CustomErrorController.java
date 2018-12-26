package top.yunshu.shw.server.cas;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.yunshu.shw.server.entity.RestModel;

/**
 * 期望:当CAS抛出异常时在此截获
 *
 * @author itning
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<RestModel<Void>> error() {
        RestModel<Void> restModel = new RestModel<>();
        restModel.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        restModel.setMsg("用户信息获取失败");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.APPLICATION_JSON_UTF8).body(restModel);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}