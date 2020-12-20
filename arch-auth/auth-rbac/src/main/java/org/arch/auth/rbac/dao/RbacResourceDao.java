package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacResource;
import org.arch.auth.rbac.mapper.RbacResourceMapper;
import org.springframework.stereotype.Repository;

/**
 * 资源表(rbac_resource)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacResourceDao extends ServiceImpl<RbacResourceMapper, RbacResource> {

}