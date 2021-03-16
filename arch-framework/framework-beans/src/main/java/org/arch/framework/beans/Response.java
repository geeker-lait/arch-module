package org.arch.framework.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.arch.framework.beans.enums.StatusCode;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 封装统一返回的Json数据结构
 * @author lait.zhang@gmail.com
 * @param <T>
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作码，成功默认为200，其他为失败
     */
    private int code;
    /**
     * 操作信息
     */
    private String msg;

    /**
     * 响应数据，为空时json序列化时会忽略
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private Response() { }

    private Response(T data) {
        this.data = data;
    }

    private Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    protected Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> Response<T> success() {
        return new Response<>(ResponseStatusCode.SUCCESS.getCode(), ResponseStatusCode.SUCCESS.getDescr());
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> Response<T> success(T data, String message) {
        return new Response<>(ResponseStatusCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 请求成功的返回方法
     *
     * @param data 返回数据
     * @return 成功响应参数
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(ResponseStatusCode.SUCCESS.getCode(), ResponseStatusCode.SUCCESS.getDescr(), data);
    }


    /**
     * 失败返回结果
     *
     * @param statusCode 错误码
     */
    public static <T> Response<T> failed(StatusCode statusCode) {
        return new Response<>(statusCode.getCode(), statusCode.getDescr(), null);
    }


    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> Response<T> failed(StatusCode errorCode, String message) {
        return new Response<>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Response<T> failed(String message) {
        return new Response<>(ResponseStatusCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> Response<T> failed() {
        return failed(ResponseStatusCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> Response<T> validateFailed() {
        return failed(ResponseStatusCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Response<T> validateFailed(String message) {
        return new Response<>(ResponseStatusCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Response<T> unauthorized(T data) {
        return new Response<>(ResponseStatusCode.UNAUTHORIZED.getCode(), ResponseStatusCode.UNAUTHORIZED.getDescr(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Response<T> forbidden(T data) {
        return new Response<>(ResponseStatusCode.FORBIDDEN.getCode(), ResponseStatusCode.FORBIDDEN.getDescr(), data);
    }

    /**
     * 请求失败的返回方法
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return 失败的响应参数
     */
    public static <T> Response<T> error(int code, String msg) {
        return new Response<>(code, msg);
    }

    /**
     * 请求失败的返回方法
     *
     * @param code 错误码
     * @return 失败的响应参数
     */
    public static <T> Response<T> error(StatusCode code) {
        return new Response<>(code.getCode(), code.getDescr());
    }

    public int getCode() {
        return code;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 判断响应的结果是否成功.
     * @return  当 code 等于 {@link ResponseStatusCode#SUCCESS} 时, 且响应的 data 为非 {@link Boolean} 时
     * 或响应的 data 为 {@link Boolean#TRUE}时 返回 true. 否则返回 false.
     */
    @JsonIgnore
    public boolean isSuccess() {
        if (ResponseStatusCode.SUCCESS.getCode() == code) {
            if (data instanceof Boolean) {
                return ((Boolean) data);
            }
            return true;
        }
        return false;
    }

    /**
     * 获取 data 数据.<br>
     *     前提: 调用 {@link #isSuccess()} 方法返回值为 true 可以调用此方法, 此方法目的就是为了不必在去判读返回值是否为 null.
     * @param isSuccess 一般情况请填 true
     * @return  返回 data
     */
    @NonNull
    @JsonIgnore
    public T getSuccessData(boolean isSuccess) {
        if (isSuccess) {
            return data;
        }
        throw new BusinessException(CommonStatusCode.RESPONSE_FAILED);
    }

    /**
     * 获取 code 等于 {@link ResponseStatusCode#SUCCESS} 时的 data 数据.
     * @return  返回 code 等于 {@link ResponseStatusCode#SUCCESS} 时的 data 数据, 否则返回 null.
     */
    @Nullable
    @JsonIgnore
    public T getSuccessData() {
        if (ResponseStatusCode.SUCCESS.getCode() == code) {
            return data;
        }
        return null;
    }

}
