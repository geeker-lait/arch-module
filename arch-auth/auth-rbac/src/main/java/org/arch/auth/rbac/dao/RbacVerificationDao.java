package org.arch.auth.rbac.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.rbac.entity.RbacVerification;
import org.arch.auth.rbac.mapper.RbacVerificationMapper;
import org.arch.framework.crud.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 4:12 PM
 */
@Slf4j
@Repository
public class RbacVerificationDao extends ServiceImpl<RbacVerificationMapper, RbacVerification> implements BaseRepository<RbacVerification,Long> {


}
