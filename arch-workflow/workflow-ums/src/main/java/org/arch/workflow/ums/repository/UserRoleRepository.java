package org.arch.workflow.ums.repository;

import org.arch.workflow.common.repository.BaseRepository;
import org.arch.workflow.ums.domain.UserRole;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色数据类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public interface UserRoleRepository extends BaseRepository<UserRole, Integer> {
    /**
     * 根据用户ID删除
     *
     * @param userId
     * @return
     */
    @Transactional
    int deleteByUserId(int userId);


    /**
     * 根据角色ID和用户ID删除
     *
     * @param roleId
     * @param userId
     * @return
     */
    @Transactional
    int deleteByRoleIdAndUserId(int roleId, int userId);

}