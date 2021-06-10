package org.arch.ums.conf.dao;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.framework.crud.utils.MybatisBatchUtils;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.ums.conf.mapper.MobileInfoMapper;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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

    /**
     * insert on duplicate key update batch sql
     */
    private static final String INSERT_ON_DUPLICATE_KEY_UPDATE_BATCH_SQL =
            "insert into conf_mobile_info(`prefix`,`province`,`city`,`mno`,`virtual`,`deleted`) " +
            "VALUES(?, ?, ?, ?, ?,?) " +
            "ON DUPLICATE KEY UPDATE `province`=VALUES(`province`),`city`=VALUES(`city`)," +
            "`mno`=VALUES(`mno`),`virtual`=VALUES(`virtual`),`deleted`=VALUES(`deleted`);";

    private final MobileInfoMapper mobileInfoMapper;

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileInfoList 实体类列表
     * @return  true 表示成功, false 表示失败.
     * @throws SQLException 批量保持失败
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileInfo> mobileInfoList) throws SQLException {

        SqlSessionFactory sqlSessionFactory = SqlHelper.sqlSessionFactory(entityClass);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        Connection connection = MybatisBatchUtils.getConnection(sqlSessionFactory, log);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ON_DUPLICATE_KEY_UPDATE_BATCH_SQL)) {
            connection.setAutoCommit(!transaction);
            int count = 0;
            for (MobileInfo mobileInfo : mobileInfoList) {
                preparedStatement.setInt(1, mobileInfo.getPrefix());
                preparedStatement.setString(2, mobileInfo.getProvince());
                preparedStatement.setString(3, mobileInfo.getCity());
                preparedStatement.setString(4, mobileInfo.getMno());
                preparedStatement.setBoolean(5, mobileInfo.getVirtual());
                preparedStatement.setBoolean(6, mobileInfo.getDeleted());
                preparedStatement.addBatch();
                count++;
                if (count == IService.DEFAULT_BATCH_SIZE) {
                    count = 0;
                    preparedStatement.executeBatch();
                    preparedStatement.clearParameters();
                }
            }
            if (count % IService.DEFAULT_BATCH_SIZE != 0) {
                preparedStatement.executeBatch();
            }
            if (transaction) {
                connection.commit();
            }
            return true;
        }
        catch (Throwable t) {
            connection.rollback();
            Throwable unwrapped = ExceptionUtil.unwrapThrowable(t);
            if (unwrapped instanceof RuntimeException) {
                MyBatisExceptionTranslator myBatisExceptionTranslator
                        = new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
                throw Objects.requireNonNull(myBatisExceptionTranslator.translateExceptionIfPossible((RuntimeException) unwrapped));
            }
            throw ExceptionUtils.mpe(unwrapped);
        }
    }
}
