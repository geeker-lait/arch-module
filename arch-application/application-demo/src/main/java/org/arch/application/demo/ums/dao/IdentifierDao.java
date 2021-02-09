package org.arch.application.demo.ums.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.application.demo.ums.entity.Identifier;
import org.arch.application.demo.ums.mapper.IdentifierMapper;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 用户-标识(Identifier) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:52:27
 * @since 1.0.0
 */
@Slf4j
@Repository
public class IdentifierDao extends ServiceImpl<IdentifierMapper, Identifier> implements CrudDao<Identifier> {

}
