package top.yunshu.shw.server.controller.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.util.SessionUtils;

/**
 * 公共控制器
 *
 * @author itning
 */
@RestController
public class CommonController {
    @GetMapping("/user")
    public ResponseEntity<RestModel> getUserInfo() {
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        return ResponseEntity.ok(new RestModel<>(loginUser));
    }
}
