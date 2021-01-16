package org.arch.ums.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.framework.ums.tenant.context.handler.ArchTenantContextHolder;
import org.arch.framework.ums.tenant.mybatis.handler.ArchTenantLineHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 14:45
 */
@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class MybatisPlusConfig {

    private final ArchTenantContextHolder archTenantContextHolder;
    private final AppProperties appProperties;

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(
                new ArchTenantLineHandler(archTenantContextHolder, appProperties))
        );
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
