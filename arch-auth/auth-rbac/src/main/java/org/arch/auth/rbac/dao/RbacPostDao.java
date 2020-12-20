package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacPost;
import org.arch.auth.rbac.mapper.RbacPostMapper;
import org.springframework.stereotype.Repository;

/**
 * 岗位表(rbac_post)数据DAO
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@Repository
public class RbacPostDao extends ServiceImpl<RbacPostMapper, RbacPost>  {

}