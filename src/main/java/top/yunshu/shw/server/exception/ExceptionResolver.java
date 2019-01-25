package top.yunshu.shw.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.yunshu.shw.server.entity.RestModel;

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
     * @param response HttpServletResponse
     * @param e        Exception
     * @return 异常消息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestModel jsonErrorHandler(HttpServletResponse response, Exception e) {
        logger.error("jsonErrorHandler->" + e.getClass().getSimpleName() + ":" + e.getMessage(), e);
        RestModel restModel = new RestModel();
        restModel.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        restModel.setMsg(e.getMessage());
        response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        return restModel;
    }

    /**
     * BaseException 错误
     *
     * @param response HttpServletResponse
     * @param e        BaseException
     * @return 异常消息
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public RestModel baseErrorHandler(HttpServletResponse response, BaseException e) {
        logger.info("baseErrorHandler->" + e.getClass().getSimpleName() + ":" + e.getMessage());
        RestModel restModel = new RestModel();
        restModel.setCode(e.getCode().value());
        restModel.setMsg(e.getMessage());
        response.setStatus(e.getCode().value());
        return restModel;
    }

    /**
     * MissingServletRequestParameterException 错误
     *
     * @param response HttpServletResponse
     * @param e        MissingServletRequestParameterException
     * @return 异常消息
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public RestModel missingServletRequestParameterExceptionHandler(HttpServletResponse response, MissingServletRequestParameterException e) {
        logger.info("missingServletRequestParameterExceptionHandler->" + e.getMessage());
        RestModel restModel = new RestModel();
        restModel.setCode(HttpServletResponse.SC_BAD_REQUEST);
        restModel.setMsg(e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return restModel;
    }

    /**
     * BaseException 错误
     *
     * @param response HttpServletResponse
     * @param e        BaseException
     * @return 异常消息
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public RestModel noHandlerFoundErrorHandler(HttpServletResponse response, NoHandlerFoundException e) {
        logger.info("noHandlerFoundErrorHandler->" + e.getClass().getSimpleName() + ":" + e.getMessage());
        RestModel restModel = new RestModel();
        restModel.setCode(HttpStatus.NOT_FOUND.value());
        restModel.setMsg(e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return restModel;
    }
}
