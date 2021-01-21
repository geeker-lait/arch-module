package org.arch.application.demo.crud.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.application.demo.crud.entity.RbacGroup;
import org.arch.application.demo.crud.mapper.RbacGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 组织机构表(rbac_group)数据DAO
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Slf4j
@Repository
public class RbacGroupDao extends ServiceImpl<RbacGroupMapper, RbacGroup> implements CrudDao<RbacGroup> {

}
