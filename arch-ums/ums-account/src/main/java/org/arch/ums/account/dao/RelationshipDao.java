package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.mapper.RelationshipMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-关系(Relationship) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:20:31
 * @since 1.0.0
 */
@Slf4j
@Repository
public class RelationshipDao extends ServiceImpl<RelationshipMapper, Relationship> implements CrudDao<Relationship> {

}