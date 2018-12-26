package top.yunshu.shw.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.yunshu.shw.server.entity.RestModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 *
 * @author Ning
 */
@ControllerAdvice
public class ExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
     * json 格式错误消息
     *
     * @param req      HttpServletRequest
     * @param response HttpServletResponse
     * @param e        Exception
     * @return 异常消息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestModel jsonErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) {
        logger.error("jsonErrorHandler->" + e.getClass().getSimpleName() + ":" + e.getMessage(), e);
        RestModel restModel = new RestModel();
        restModel.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        restModel.setMsg(req.getRequestURL().toString() + " " + e.getMessage());
        response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        return restModel;
    }

    /**
     * BaseException 错误
     *
     * @param req      HttpServletRequest
     * @param response HttpServletResponse
     * @param e        BaseException
     * @return 异常消息
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public RestModel baseErrorHandler(HttpServletRequest req, HttpServletResponse response, BaseException e) {
        logger.error("baseErrorHandler->" + e.getClass().getSimpleName() + ":" + e.getMessage(), e);
        RestModel restModel = new RestModel();
        restModel.setCode(e.getCode().value());
        restModel.setMsg(req.getRequestURL().toString() + " " + e.getMessage());
        response.setStatus(e.getCode().value());
        return restModel;
    }

    /**
     * BaseException 错误
     *
     * @param req      HttpServletRequest
     * @param response HttpServletResponse
     * @param e        BaseException
     * @return 异常消息
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public RestModel noHandlerFoundErrorHandler(HttpServletRequest req, HttpServletResponse response, NoHandlerFoundException e) {
        logger.info("noHandlerFoundErrorHandler->" + e.getClass().getSimpleName() + ":" + e.getMessage());
        RestModel restModel = new RestModel();
        restModel.setCode(HttpStatus.NOT_FOUND.value());
        restModel.setMsg(req.getRequestURL().toString() + " " + e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return restModel;
    }
}
