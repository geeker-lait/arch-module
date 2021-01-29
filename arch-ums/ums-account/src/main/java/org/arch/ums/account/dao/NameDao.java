package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.mapper.NameMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号名(Name) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:53:40
 * @since 1.0.0
 */
@Slf4j
@Repository
public class NameDao extends ServiceImpl<NameMapper, Name> implements CrudDao<Name> {

}