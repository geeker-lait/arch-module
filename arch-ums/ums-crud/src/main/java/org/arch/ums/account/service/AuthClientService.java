package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.AuthClientDao;
import org.arch.ums.account.entity.AuthClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 授权客户端(AuthClient) 表服务层<br>
 * @author YongWu zheng
 * @date 2021-01-29 20:48:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthClientService extends CrudService<AuthClient, java.lang.Long> {

    protected final AuthClientDao authClientDao;

    /***
     * 逻辑删除实体
     *
     * @param id 主键id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        AuthClient authClient = new AuthClient();
        authClient.setId(id);
        authClient.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.lambdaUpdate(authClient).set(AuthClient::getDeleted, 1);
        // 逻辑删除
        return authClientDao.update(updateWrapper);
    }

    /***
     * 逻辑删除实体
     *
     * @param t 需要删除的实体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(AuthClient t) {
        t.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.lambdaUpdate(t).set(AuthClient::getDeleted, 1);
        // 逻辑删除
        return authClientDao.update(updateWrapper);
    }

    /***
     * 根据主键集合逻辑删除实体
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteAllById(List<Long> ids) {
        LambdaUpdateWrapper<AuthClient> updateWrapper = Wrappers.<AuthClient>lambdaUpdate()
                                                                .eq(AuthClient::getDeleted, 0)
                                                                .in(AuthClient::getId, ids)
                                                                .set(AuthClient::getDeleted, 1);

        // 逻辑删除
        return authClientDao.update(updateWrapper);
    }

}