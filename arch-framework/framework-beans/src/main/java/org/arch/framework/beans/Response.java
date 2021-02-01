package org.arch.framework.beans;

import org.arch.framework.beans.enums.StatusCode;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;

import java.io.Serializable;

/**
 * 封装统一返回的Json数据结构
 *
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
//    @JsonInclude(JsonInclude.Include.NON_NULL)
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
     * 获取 code 等于 {@link ResponseStatusCode#SUCCESS} 时的 data 数据.
     * @return  返回 code 等于 {@link ResponseStatusCode#SUCCESS} 时的 data 数据, 否则返回 null.
     */
    public T getSuccessData() {
        if (ResponseStatusCode.SUCCESS.getCode() == code) {
            return data;
        }
        return null;
    }

}
