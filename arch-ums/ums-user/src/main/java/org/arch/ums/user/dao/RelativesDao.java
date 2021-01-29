package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.Relatives;
import org.arch.ums.user.mapper.RelativesMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户亲朋信息(Relatives) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:10:04
 * @since 1.0.0
 */
@Slf4j
@Repository
public class RelativesDao extends ServiceImpl<RelativesMapper, Relatives> implements CrudDao<Relatives> {

}