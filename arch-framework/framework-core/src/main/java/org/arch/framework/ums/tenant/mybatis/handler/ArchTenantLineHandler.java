package org.arch.framework.ums.tenant.mybatis.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.arch.framework.ums.properties.AppProperties;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * mybatis 行级多租户处理器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 20:42
 */
public class ArchTenantLineHandler implements TenantLineHandler {

    private final TenantContextHolder tenantContextHolder;
    private final Set<String> tenantTables;
    private final String tenantIdColumn;

    public ArchTenantLineHandler(TenantContextHolder tenantContextHolder,
                                 AppProperties appProperties) {
        this.tenantContextHolder = tenantContextHolder;
        this.tenantIdColumn = appProperties.getTenantIdColumn();
        this.tenantTables = new HashSet<>();
        tenantTables.add("account_identifier");
        tenantTables.add("account_role");
        tenantTables.add("account_category");
        tenantTables.add("account_resource");
        tenantTables.add("account_menu");
        tenantTables.add("account_permission");
        tenantTables.add("account_group");
        tenantTables.add("account_post");
        tenantTables.add("account_role_menu");
        tenantTables.add("account_role_resource");
        tenantTables.add("account_role_permission");
        tenantTables.add("account_role_group");
    }

    @Override
    public Expression getTenantId() {
        return new StringValue(tenantContextHolder.getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        return this.tenantIdColumn;
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return !this.tenantTables.contains(tableName);
    }
}
