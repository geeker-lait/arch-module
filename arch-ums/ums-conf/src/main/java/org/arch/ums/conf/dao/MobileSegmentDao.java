package org.arch.ums.conf.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.mapper.MobileSegmentMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 手机号段信息(MobileSegment) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:34
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class MobileSegmentDao extends CrudServiceImpl<MobileSegmentMapper, MobileSegment> implements CrudDao<MobileSegment> {
    private final MobileSegmentMapper mobileSegmentMapper;
}
