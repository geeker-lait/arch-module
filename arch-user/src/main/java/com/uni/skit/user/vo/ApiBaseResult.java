package com.uni.skit.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description: api接口返回父类
 *
 * @author kenzhao
 * @date 2020/3/30 14:03
 */
@Data
public class ApiBaseResult {

    private Integer status = 200;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String message = "success";
    private Object body = "success";

    private ApiBaseResult() {
        timestamp = LocalDateTime.now();
    }

    public static ApiBaseResult success(){
        ApiBaseResult ApiBaseResult = new ApiBaseResult();
        return ApiBaseResult;
    }
    public static ApiBaseResult success(String message){
        ApiBaseResult ApiBaseResult = new ApiBaseResult();
        ApiBaseResult.setMessage(message);
        return ApiBaseResult;
    }
    public static ApiBaseResult success(Object body){
        ApiBaseResult ApiBaseResult = new ApiBaseResult();
        ApiBaseResult.setBody(body);
        return ApiBaseResult;
    }

    public static ApiBaseResult error(String message){
        ApiBaseResult ApiBaseResult = new ApiBaseResult();
        ApiBaseResult.setStatus(9870);
        ApiBaseResult.setMessage(message);
        return ApiBaseResult;
    }
    public static ApiBaseResult error(Integer status, String message){
        ApiBaseResult ApiBaseResult = new ApiBaseResult();
        ApiBaseResult.setStatus(status);
        ApiBaseResult.setMessage(message);
        return ApiBaseResult;
    }
}