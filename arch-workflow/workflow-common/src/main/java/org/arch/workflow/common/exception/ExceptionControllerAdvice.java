package org.arch.workflow.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.arch.workflow.common.model.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常全局捕获
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月8日
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private ExceptionFactory exceptionFactory;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo handleIllegal(IllegalArgumentException e) {
        log.error("参数非法异常", e);
        return new ErrorInfo(e.getRet(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthErrorException.class)
    @ResponseBody
    public ErrorInfo handleAuthError(AuthErrorException e) {
        log.error("授权验证异常", e);
        return new ErrorInfo(e.getRet(), e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    public ErrorInfo handleForbidden(ForbiddenException e) {
        log.error("禁止异常", e);
        return new ErrorInfo(e.getRet(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleNotFound(ObjectNotFoundException e) {
        log.error("对象没找到异常", e);
        return new ErrorInfo(e.getRet(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    public ErrorInfo handleConflict(ConflictException e) {
        log.error("冲突异常", e);
        return new ErrorInfo(e.getRet(), e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorInfo handleResponse(BaseException e) {
        log.error("全局捕获自定义异常", e);
        return new ErrorInfo(e.getRet(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleOtherException(Exception e) {
        log.error("全局捕获未知异常", e);
        return exceptionFactory.createInternalError();
    }

}