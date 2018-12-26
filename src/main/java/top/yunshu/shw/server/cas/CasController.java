package top.yunshu.shw.server.cas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.yunshu.shw.server.util.SessionUtils;

/**
 * CAS Controller
 *
 * @author itning
 */
@Controller
public class CasController {
    private static final Logger logger = LoggerFactory.getLogger(CasController.class);

    private final GlobalConstants globalConstants;

    @Autowired
    public CasController(GlobalConstants globalConstants) {
        this.globalConstants = globalConstants;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> casLogin() {
        logger.debug("302 Found Location: " + globalConstants.getLoginSuccessUrl());
        return ResponseEntity.status(HttpStatus.FOUND).location(globalConstants.getLoginSuccessUrl()).build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> casLogout() {
        logger.debug("cas Logout");
        logger.debug("302 Found Location: " + globalConstants.getLogoutUrl());
        SessionUtils.invalidateSession();
        return ResponseEntity.status(HttpStatus.FOUND).location(globalConstants.getLogoutUrl()).build();
    }
}
