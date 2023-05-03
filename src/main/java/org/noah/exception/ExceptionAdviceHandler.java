package org.noah.exception;

import org.apache.shiro.ShiroException;
import org.noah.entity.CommonException;
import org.noah.entity.ResponseEntity;
import org.noah.enums.CommonError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionAdviceHandler {
    private final Logger log = LoggerFactory.getLogger(ExceptionAdviceHandler.class);
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseEntity handle401(ShiroException e) {
        return ResponseEntity.fail(CommonError.NOT_AUTH, "抱歉，您没有操作权限！");
    }

    /**
     * 缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handlerException(MissingServletRequestParameterException e) {
        return ResponseEntity.fail(CommonError.PARAMS_ERROR, "缺少参数:" + e.getParameterName());
    }

    /**
     *
     * @RequestBody类型参数数据类型转换异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handlerException(HttpMessageNotReadableException e) {
        log.error("参数数据异常", e);
        return ResponseEntity.fail(CommonError.PARAMS_ERROR);
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handlerException(NullPointerException e) {
        log.error("空指针异常", e);
        return ResponseEntity.fail(CommonError.REQUEST_ERROR);
    }

    /**
     * 请求方式不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handlerException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方式不支持", e);
        return ResponseEntity.fail(CommonError.REQUEST_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity handlerCommonException(CommonException commonException) {
        log.error("CommonException. ", commonException);
        return ResponseEntity.fail(commonException);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity handlerException(HttpServletRequest request, Exception exception) {
        ResponseEntity entity;
        if (exception instanceof CommonException) {
            CommonException commonException = (CommonException) exception;
            log.error(commonException.getErrMsg(), commonException.getCause());
            entity = ResponseEntity.fail(commonException);
        } else {
            log.error("请求处理失败", exception);
            entity = ResponseEntity.fail(CommonError.REQUEST_ERROR);
        }
        return entity;
    }
}
