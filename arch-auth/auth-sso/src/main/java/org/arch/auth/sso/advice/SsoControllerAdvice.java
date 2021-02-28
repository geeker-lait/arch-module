package org.arch.auth.sso.advice;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.arch.framework.beans.Response;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static top.dcenter.ums.security.common.consts.SecurityConstants.CONTROLLER_ADVICE_ORDER_DEFAULT_VALUE;

/**
 * sso 统一异常处理
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.28 22:21
 */
@Order(CONTROLLER_ADVICE_ORDER_DEFAULT_VALUE + 6)
@ControllerAdvice
@Slf4j
public class SsoControllerAdvice {

    @ExceptionHandler(TooManyResultsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> tooManyResultsException(TooManyResultsException e) {
        log.error(e.getMessage(),e);
        return Response.error(FAILED);
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String>  persistenceException(PersistenceException e){
        log.error(e.getMessage(),e);
        return Response.error(FAILED);
    }

    @ExceptionHandler(MybatisPlusException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> mybatisPlusException(MybatisPlusException e){
        log.error(e.getMessage(),e);
        return Response.error(FAILED);
    }

}
