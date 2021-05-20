package org.arch.workflow.ums.repository;

import org.arch.workflow.common.repository.BaseRepository;
import org.arch.workflow.ums.domain.UserGroup;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户群组数据类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public interface UserGroupRepository extends BaseRepository<UserGroup, Integer> {

    /**
     * 根据用户ID删除
     *
     * @param userId
     * @return
     */
    @Transactional
    int deleteByUserId(int userId);

    /**
     * 根据群组ID和用户ID删除
     *
     * @param groupId
     * @param userId
     * @return
     */
    @Transactional
    int deleteByGroupIdAndUserId(int groupId, int userId);
}