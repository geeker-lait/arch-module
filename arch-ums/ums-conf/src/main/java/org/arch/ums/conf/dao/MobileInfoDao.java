package org.arch.ums.conf.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.ums.conf.mapper.MobileInfoMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 手机号归属地信息(MobileInfo) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:35
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class MobileInfoDao extends CrudServiceImpl<MobileInfoMapper, MobileInfo> implements CrudDao<MobileInfo> {
    private final MobileInfoMapper mobileInfoMapper;
}
