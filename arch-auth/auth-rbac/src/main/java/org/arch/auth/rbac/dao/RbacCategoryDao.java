package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacCategory;
import org.arch.auth.rbac.mapper.RbacCategoryMapper;
import org.springframework.stereotype.Repository;

/**
 * 资源类目表(rbac_category)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacCategoryDao extends ServiceImpl<RbacCategoryMapper, RbacCategory> {

}