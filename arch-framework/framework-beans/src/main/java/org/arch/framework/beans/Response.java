package org.arch.framework.beans;

import org.arch.framework.beans.enums.StatusCode;
import org.arch.framework.beans.exception.constant.ResponseStatusCode;

/**
 * 封装统一返回的Json数据结构
 *
 * @param <T>
 */
public class Response<T> {
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
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

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
        return new Response<T>(ResponseStatusCode.SUCCESS.getCode(), ResponseStatusCode.SUCCESS.getDescr());
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> Response<T> success(T data, String message) {
        return new Response<T>(ResponseStatusCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 请求成功的返回方法
     *
     * @param data 返回数据
     * @return 成功响应参数
     */
    public static <T> Response<T> success(T data) {
        return new Response<T>(ResponseStatusCode.SUCCESS.getCode(), ResponseStatusCode.SUCCESS.getDescr(), data);
    }


    /**
     * 失败返回结果
     *
     * @param statusCode 错误码
     */
    public static <T> Response<T> failed(StatusCode statusCode) {
        return new Response<T>(statusCode.getCode(), statusCode.getDescr(), null);
    }


    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> Response<T> failed(StatusCode errorCode, String message) {
        return new Response<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Response<T> failed(String message) {
        return new Response<T>(ResponseStatusCode.FAILED.getCode(), message, null);
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
        return new Response<T>(ResponseStatusCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Response<T> unauthorized(T data) {
        return new Response<T>(ResponseStatusCode.UNAUTHORIZED.getCode(), ResponseStatusCode.UNAUTHORIZED.getDescr(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Response<T> forbidden(T data) {
        return new Response<T>(ResponseStatusCode.FORBIDDEN.getCode(), ResponseStatusCode.FORBIDDEN.getDescr(), data);
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
        return new Response(code.getCode(), code.getDescr());
    }

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response setData(T data) {
        this.data = data;
        return this;
    }

}
