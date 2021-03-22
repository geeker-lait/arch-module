package org.arch.auth.rbac.stream.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.premission.dto.UpdateRoleResourcesDto;

/**
 * rbac 权限更新事件
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.19 9:53
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonIgnoreProperties("source")
public class RbacRemotePermissionUpdatedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 7848453729161338456L;

    /**
     * 更新权限资源 DTO
     */
    @SuppressWarnings("FieldMayBeFinal")
    private UpdateRoleResourcesDto<Object> updateRoleResourcesDto;

    public RbacRemotePermissionUpdatedEvent() {
        // 序列化
        //noinspection ConstantConditions
        this(new Object(), null);
    }

    public RbacRemotePermissionUpdatedEvent(@NonNull Object source,
                                            @NonNull UpdateRoleResourcesDto<Object> updateRoleResourcesDto) {
        super(source);
        this.updateRoleResourcesDto = updateRoleResourcesDto;
    }

    public UpdateRoleResourcesDto<Object> getUpdateRoleResourcesDto() {
        return updateRoleResourcesDto;
    }
}
