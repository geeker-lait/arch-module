package org.arch.ums.biz.conf.service;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.conf.dao.MobileInfoDao;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.ums.conf.service.MobileInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * 手机号归属地信息(MobileInfo) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:35
 * @since 1.0.0
 */
@Slf4j
@Service
public class BizMobileInfoService extends MobileInfoService {

    public BizMobileInfoService(MobileInfoDao mobileInfoDao) {
        super(mobileInfoDao);
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileInfoList 实体类列表
     * @return  true 表示成功, false 表示失败.
     * @throws SQLException 批量保持失败
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileInfo> mobileInfoList) throws SQLException {
        return mobileInfoDao.insertOnDuplicateKeyUpdateBatch(mobileInfoList);
    }

}
