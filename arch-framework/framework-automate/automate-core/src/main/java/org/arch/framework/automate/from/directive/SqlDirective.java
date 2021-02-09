package org.arch.framework.automate.from.directive;

import org.arch.framework.automate.api.DirectiveRequest;
import org.arch.framework.automate.api.DirectiveResponse;

/**
 * @author lait.zhang@gmail.com
 * @description: 1.DDL（data definition language）——数据定义语言，用于定义和管理 SQL 数据库中的所有对象的语言
 * <p>
 * DDL主要的命令有CREATE、ALTER、DROP等，DDL主要是用在定义或改变表（TABLE）的结构，数据类型，表之间的链接和约束等初始化工作上，他们大多在建立表时使用。
 * <p>
 * （1）CREATE - to create objects in the database 创建
 * <p>
 * （2）ALTER - alters the structure of the database 修改
 * <p>
 * （3）DROP - delete objects from the database 删除
 * <p>
 * （4）TRUNCATE - remove all records from a table, including all spaces allocated for the records are removed TRUNCATE TABLE [Table Name]。
 * <p>
 * （5）COMMENT - add comments to the data dictionary 注释
 * <p>
 * （6）GRANT - gives user's access privileges to database 授权
 * <p>
 * （7）REVOKE - withdraw access privileges given with the GRANT command 收回已经授予的权限
 * <p>
 * 2.DML（data manipulation language）——数据操作语言，SQL中处理数据等操作统称为数据操纵语言。
 * <p>
 * <p>
 * <p>
 * 它们是SELECT、UPDATE、INSERT、DELETE，就象它的名字一样，这4条命令是用来对数据库里的数据进行操作的语言。
 * <p>
 * （1）SELECT - retrieve data from the a database 查询
 * <p>
 * （2）INSERT - insert data into a table 添加
 * <p>
 * （3）UPDATE - updates existing data within a table 更新
 * <p>
 * （4）DELETE - deletes all records from a table, the space for the records remain 删除
 * <p>
 * （5）CALL - call a PL/SQL or Java subprogram
 * <p>
 * （6）EXPLAIN PLAN - explain access path to data
 * <p>
 * （7）LOCK TABLE - control concurrency 锁，用于控制并发
 * <p>
 * 3.DCL（Data Control Language）——数据控制语言，用来授予或回收访问数据库的某种特权，并控制数据库操纵事务发生的时间及效果，对数据库实行监视等。
 * <p>
 * <p>
 * <p>
 * 数据库控制功能，用来设置或更改数据库用户或角色权限的语句，包括grant,deny,revoke等语句。在默认状态下，只有sysadmin,dbcreator,db_owner或db_securityadmin等人员才有权力执行DCL 详细解释。
 * <p>
 * （1）COMMIT - save work done 提交
 * <p>
 * （2）SAVEPOINT - identify a point in a transaction to which you can later roll back 保存点
 * <p>
 * （3）ROLLBACK - restore database to original since the last COMMIT 回滚
 * <p>
 * （4）SET TRANSACTION - Change transaction options like what rollback segment to use 设置当前事务的特性，它对后面的事务没有影响
 * @weixin PN15855012581
 * @date 2/5/2021 4:54 PM
 */
public interface SqlDirective<T extends DirectiveRequest> {

    <R extends DirectiveResponse> R exec(T directiveRequest);

    SqlDirectiveCode getSqlDirectiveCode();
}
