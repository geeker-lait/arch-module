package org.arch.framework.automate.from.ddl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.dto.DefinitionTableDto;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.arch.framework.automate.generater.properties.TableProperties;
import org.arch.framework.beans.enums.StatusCode;
import org.arch.framework.beans.exception.BaseException;
import org.springframework.beans.factory.InitializingBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * DDl 操作 数据库
 *  暂时不提供修改Alter 表结构或者操作可通过  先drop table 然后 重新 creat
 * @author junboXiang
 * @version V1.0
 * 2021-02-27
 */
@Slf4j
public abstract class DDLOperate implements InitializingBean {

    protected static final List<String> JDBC_DDL_OPERATE_TYPES = Lists.newArrayList();

    protected static final Map<String, DDLOperate> JDBC_DDL_OPERATE_MAP = new HashMap<>(16);

    /**
     * 统一获取 jdbc Connection 并将 Collection 交给子类自己的业务执行自己的业务
     * @param properties
     * @param function
     * @param <R>
     * @return
     */
    protected <R> R execute(DatabaseProperties properties, Function<Connection, R> function) {
        try (Connection connection = getConnection(properties);) {
            return function.apply(connection);
        } catch (SQLException  | ClassNotFoundException e) {
            log.warn("The database connection failed or the operation failed, business config exception please check jdbcProperties, databaseProperties:{}", properties, e);
            // 错误信息可以调整,暂时先这么写
            throw new BaseException(new StatusCode() {
                @Override
                public int getCode() {
                    return -1;
                }
                @Override
                public String getDescr() {
                    return "连接数据失败请检查 jdbc 信息";
                }
            });
        } catch (Exception e) {
            log.warn("The database connection failed or the operation failed, Exception databaseProperties:{}", properties, e);
        }
        return null;
    }

    /**
     * 根据 driver 选择 对应的 jdbc ddl实现类
     *      次方法必须在spring容器环境中调用，对象实例依赖spring容器创建
     * @param properties
     * @return
     */
    public static DDLOperate selectDDLOperate(DatabaseProperties properties) {
        if (properties == null || StringUtils.isBlank(properties.getDriver()) || StringUtils.isBlank(properties.getHost()) ||
                StringUtils.isBlank(properties.getHost()) || StringUtils.isBlank(properties.getUsername()) || StringUtils.isBlank(properties.getPassword())) {
            log.warn("datasource params is null, user system default datasource, properties:{}", properties);
            return JDBC_DDL_OPERATE_MAP.get(Constants.DDL_DEFAULT);
        }
        String lowerDriverStr = properties.getDriver().toLowerCase();
        for (String typeName : JDBC_DDL_OPERATE_TYPES) {
            if (lowerDriverStr.indexOf(typeName) > -1) {
                return JDBC_DDL_OPERATE_MAP.get(typeName);
            }
        }
        log.warn("match ddlOperate error, return null properties:{}", properties);
        throw new BaseException(new StatusCode() {
            @Override
            public int getCode() {
                return -1;
            }

            @Override
            public String getDescr() {
                return "暂时不支持当前指定的数据库类型";
            }
        });
    }

    /**
     * 获取连接
     * @param properties
     * @return
     */
     private Connection getConnection(DatabaseProperties properties) throws ClassNotFoundException, SQLException {
        if (properties == null || StringUtils.isBlank(properties.getDriver()) || StringUtils.isBlank(properties.getHost()) ||
                StringUtils.isBlank(properties.getHost()) || StringUtils.isBlank(properties.getUsername()) || StringUtils.isBlank(properties.getPassword())) {
            return null;
        }
        String username = properties.getUsername();
        String password = properties.getPassword();
        Class.forName(getDriver(properties));
        return DriverManager.getConnection(getDbUrl(properties), username, password);
    }

    /**
     * 获取jdbc连接数据库的 url
     * @param properties
     * @return
     */
    protected abstract String getDbUrl(DatabaseProperties properties);

    /**
     * 获取驱动，不同版本数据库驱动可能存在差异
     *      例如 mysql 6.x+ 是 com.mysql.cj.jdbc.Driver 5.x 是 com.mysql.jdbc.Driver 高版本可以兼容低版本
     *      项目使用的是 mysql 8.0，如果用户传入的是低版本驱动会创建连接失败，可以直接使用高版本（每个数据不同，子类自行处理）
     * @param properties
     * @return
     */
    protected abstract String getDriver(DatabaseProperties properties);

    /**
     * 创建数据库
     * @param properties
     * @param database
     * @return
     */
    public abstract int createDatabase(DatabaseProperties properties, String database);

    /**
     * 删除指定数据库
     * @param properties
     * @param database
     * @return
     */
    public abstract int dropDatabase(DatabaseProperties properties, String database);

    /**
     * 删除表
     * @param properties
     * @param  database
     * @param tableName
     * @return
     */
    public abstract int dropTable(DatabaseProperties properties, String database, String tableName);

    /**
     * 创建 table
     *      先判断对应库下表是否存在，不存在才创建
     * @param properties
     * @param record
     * @return
     */
    public abstract int createTable(DatabaseProperties properties, DefinitionTableDto record);
    

    /**
     * 库及库下的表信息
     * @param properties    数据库配置
     * @param database 单个库查询
     * @return
     */
    public abstract List<TableProperties> getDatabaseProperties(DatabaseProperties properties, String database);


}
