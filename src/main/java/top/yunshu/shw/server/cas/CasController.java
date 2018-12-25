package top.yunshu.shw.server.cas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

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
    @ResponseStatus(value = HttpStatus.FOUND)
    public void casLogin(HttpServletResponse response) {
        logger.debug("302 Found Location: " + globalConstants.getLoginSuccessUrl());
        response.setHeader("Location", globalConstants.getLoginSuccessUrl());
    }
}
