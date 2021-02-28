package org.arch.framework.crud.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.mapping.SqlCommandType;
import org.arch.framework.beans.utils.WebUtils;
import org.arch.framework.crud.AuditLogService;
import org.arch.framework.crud.CrudLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 */
@Component
public class LogUtils {
    /**
     * 日志服务
     */
    private static Logger log = LoggerFactory.getLogger(LogUtils.class);

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 异常信息
     *
     * @param exceptionName
     * @param exceptionMessage
     * @param elements
     * @return
     */
    private String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
    }

    /**
     * 保存日志
     *
     * @param title
     * @param operation
     */
    public void saveLog(String title, String logType, String operation) {
        saveLog(title, logType, operation, null, null, null, null, 0);
    }

    /**
     * 保存日志
     *
     * @param title
     * @param operation
     * @param requestParams
     * @param throwable
     * @param executeTime
     */
    public void saveLog(String title, String operation, String requestParams, Throwable throwable, long executeTime) {
        saveLog(title, null, operation, requestParams, null, null, throwable, executeTime);
    }

    /**
     * 保存日志
     *
     * @param title
     * @param operation
     * @param requestParams
     * @param className
     * @param methodName
     * @param throwable
     * @param executeTime
     */
    public void saveLog(String title, String logType, String operation, String requestParams, String className, String methodName,
                        Throwable throwable, long executeTime) {
        if (auditLogService == null) {
            log.warn("AuditLogAspect - AuditLogService is null");
            return;
        }

        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = WebUtils.getRequest();

        CrudLog audit = new CrudLog();

        if (StrUtil.isEmpty(logType)) {
            String sqlCommandTypes = ObjectUtil.toString(request.getAttribute(SqlCommandType.class.getName()));
            if (StrUtil.containsAny("," + sqlCommandTypes + ",", ",INSERT,", ",UPDATE,", ",DELETE,")) {
                audit.setLogType(CrudLog.TYPE_UPDATE);
            } else if (StrUtil.contains("," + sqlCommandTypes + ",", ",SELECT,")) {
                audit.setLogType(CrudLog.TYPE_SELECT);
            } else {
                audit.setLogType(CrudLog.TYPE_ACCESS);
            }
        }
        // 审计标题
        audit.setTitle(title);
        audit.setRequestParams(requestParams);
        audit.setOperation(operation);

        audit.setClassName(className);
        // 获取请求的方法名
        audit.setMethodName(methodName);
        audit.setExecuteTime(executeTime);
        //audit.setCreateBy((String) token.getUserId());
        //audit.setCreateByName((String) session.getUsername());
        audit.setIsException(throwable != null ? "1" : "0");
        if (throwable != null) {
            audit.setExceptionName(throwable.getClass().getName());
            audit.setExceptionInfo(stackTraceToString(throwable.getClass().getName(), throwable.getMessage(),
                    throwable.getStackTrace()));
        }
        audit.setRemoteAddr(request.getRemoteAddr());
        audit.setRequestMethod(request.getMethod());
        audit.setRequestUri(request.getRequestURI());
        audit.setServerAddr(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());

        audit.setUserAgent(request.getHeader("User-Agent"));
        UserAgent userAgent = UserAgentUtil.parse(audit.getUserAgent());
        audit.setBrowserName(userAgent.getBrowser().getName());
        audit.setDeviceName(userAgent.getOs().getName());

        // 保存日志
        taskExecutor.execute(() -> {
            auditLogService.saveAuditLog(audit);
        });

    }
}
