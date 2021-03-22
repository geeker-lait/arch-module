package org.arch.auth.rbac.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.dcenter.ums.security.core.api.premission.service.UpdateCacheOfRolesResourcesService;
import top.dcenter.ums.security.core.premission.listener.UpdateRolesResourcesListener;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.19 13:05
 */
@Slf4j
public class ArchUpdateRolesResourcesListener extends UpdateRolesResourcesListener {

    public ArchUpdateRolesResourcesListener(UpdateCacheOfRolesResourcesService updateCacheOfRolesResourcesService) {
        super(updateCacheOfRolesResourcesService);
    }

}
