package org.arch.ums.conf.dao;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.mapper.MobileSegmentMapper;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.baomidou.mybatisplus.extension.toolkit.SqlHelper.sqlSessionFactory;
import static org.arch.framework.crud.utils.MybatisBatchUtils.getConnection;

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
    /**
     * insert on duplicate key update batch sql
     */
    private static final String INSERT_ON_DUPLICATE_KEY_UPDATE_BATCH_SQL =
            "insert into conf_mobile_segment(`prefix`,`mno`,`virtual`,`deleted`) " +
                    "VALUES(?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE `mno`=VALUES(`mno`),`virtual`=VALUES(`virtual`)," +
                    "`deleted`=VALUES(`deleted`);";

    private final MobileSegmentMapper mobileSegmentMapper;

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileSegmentList 实体类列表
     * @return  true 表示成功, false 表示失败.
     * @throws SQLException 批量保持失败
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileSegment> mobileSegmentList) throws SQLException {

        SqlSessionFactory sqlSessionFactory = sqlSessionFactory(entityClass);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        Connection connection = getConnection(sqlSessionFactory, log);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ON_DUPLICATE_KEY_UPDATE_BATCH_SQL)) {
            connection.setAutoCommit(!transaction);
            int count = 0;
            for (MobileSegment mobileSegment : mobileSegmentList) {
                preparedStatement.setInt(1, mobileSegment.getPrefix());
                preparedStatement.setString(2, mobileSegment.getMno());
                preparedStatement.setBoolean(3, mobileSegment.getVirtual());
                preparedStatement.setBoolean(4, mobileSegment.getDeleted());
                preparedStatement.addBatch();
                count++;
                if (count == DEFAULT_BATCH_SIZE) {
                    count = 0;
                    preparedStatement.executeBatch();
                    preparedStatement.clearParameters();
                }
            }
            if (count % DEFAULT_BATCH_SIZE != 0) {
                preparedStatement.executeBatch();
            }
            if (transaction) {
                connection.commit();
            }
            return true;
        } catch (Throwable t) {
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
