package org.arch.framework.crud;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 */
@Data
public class CrudLog implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 用户名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date created;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;

    /**
     * 操作说明
     */
    private String operation;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 请求服务器地址
     */
    private String serverAddr;

    /**
     * 是否异常
     */
    private String isException;

    /**
     * 异常信息
     */
    private String exceptionName;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 设备名称/操作系统
     */
    private String deviceName;

    /**
     * 浏览器名称
     */
    private String browserName;

    /**
     * 执行时间
     */
    private Long executeTime;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 日志类型
     */
    private String logType;

    public static final String TYPE_ACCESS = "access";
    public static final String TYPE_UPDATE = "update";
    public static final String TYPE_SELECT = "select";
    public static final String TYPE_LOGIN_LOGOUT = "loginLogout";
}
