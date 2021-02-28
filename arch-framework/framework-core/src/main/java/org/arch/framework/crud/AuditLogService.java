package org.arch.framework.crud;

public interface AuditLogService {

    /**
     * 保存日志内容（日志）
     * @param log
     */
    void saveAuditLog(CrudLog log);
}
