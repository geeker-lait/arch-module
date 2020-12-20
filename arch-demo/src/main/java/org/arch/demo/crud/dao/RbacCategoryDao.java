package org.arch.demo.crud.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.demo.crud.entity.RbacCategory;
import org.arch.demo.crud.mapper.RbacCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 资源类目表(rbac_category)数据DAO
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Slf4j
@Repository
public class RbacCategoryDao extends ServiceImpl<RbacCategoryMapper, RbacCategory> implements BaseRepository<RbacCategory, Long> {

}