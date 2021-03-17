package org.arch.framework.crud.utils;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.17 19:23
 */
public class MybatisBatchUtils {

    /**
     * 获取 {@link Connection}
     * @param sqlSessionFactory {@link SqlSessionFactory}
     * @param log               {@link Logger}
     * @return  {@link Connection}
     */
    @NonNull
    public static Connection getConnection(@NonNull SqlSessionFactory sqlSessionFactory, @NonNull Logger log) {
        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        if (sqlSessionHolder != null) {
            SqlSession sqlSession = sqlSessionHolder.getSqlSession();
            //原生无法支持执行器切换，当存在批量操作时，会嵌套两个session的，优先commit上一个session
            //按道理来说，这里的值应该一直为false。
            sqlSession.commit(!transaction);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            log.warn("SqlSession [" + sqlSession + "] was not registered for synchronization because DataSource is not transactional");
        }
        return sqlSession.getConnection();
    }
}
