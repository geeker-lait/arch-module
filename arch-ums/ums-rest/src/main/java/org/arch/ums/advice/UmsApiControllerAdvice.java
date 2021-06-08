package org.arch.ums.advice;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.ArgumentException;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.BaseException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.ValidationException;
import org.arch.framework.web.feign.exception.FeignCallException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.arch.framework.beans.exception.constant.ArgumentStatuesCode.VALIDATE_FAILED;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static org.springframework.util.StringUtils.hasText;
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
public class UmsApiControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<String> authenticationException(AuthenticationException e) {
        log.error(e.getMessage(),e);
        return Response.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(ArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> argumentException(ArgumentException e) {
        log.error(e.getMessage(),e);
        return Response.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> validationException(ValidationException e) {
        log.error(e.getMessage(),e);
        return Response.error(e.getResponseCode().getCode(), e.getMessage());
    }

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

    @ExceptionHandler(FeignCallException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> feignCallException(FeignCallException e){
        log.error(e.getMessage(),e);
        return Response.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> businessException(BusinessException e){
        log.error(e.getMessage(),e);
        return Response.error(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> baseException(BaseException e){
        log.error(e.getMessage(),e);
        return Response.error(FAILED.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Map<String, String>> bindException(Exception e){
        log.warn(e.getMessage(), e);
        List<FieldError> fieldErrorList = ((BindException) e).getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>(fieldErrorList.size());
        for (FieldError fieldError : fieldErrorList) {
            String defaultMessage = fieldError.getDefaultMessage();
            if (hasText(defaultMessage)) {
                errorMap.put(fieldError.getField(), defaultMessage);
            }
        }
        Response<Map<String, String>> response = Response.error(VALIDATE_FAILED.getCode(), null);
        return response.setData(errorMap);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> exception(Exception e){
        log.error(e.getMessage(),e);
        return Response.error(FAILED.getCode(), e.getMessage());
    }

}
