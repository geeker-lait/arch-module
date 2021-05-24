package org.arch.workflow.ums.repository;

import org.arch.workflow.common.repository.BaseRepository;
import org.arch.workflow.ums.constant.TableConstant;
import org.arch.workflow.ums.domain.Group;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户群组数据类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public interface GroupRepository extends BaseRepository<Group, Integer> {
    /**
     * 根据uid查询群组
     *
     * @param userId
     * @return
     */
    @Query("select a from Group a, UserGroup b where a.id = b.groupId and a.status=" + TableConstant.ROLE_STATUS_NORMAL + " and b.userId = ?1 order by a.order asc")
    List<Group> findByUserId(int userId);

    /**
     * 根据父ID查询群组
     *
     * @param parentId
     * @return
     */
    List<Group> findByParentId(int parentId);


    /**
     * 根据状态查询群组
     *
     * @param status
     * @return
     */
    List<Group> findByStatusOrderByOrderAsc(byte status);
}