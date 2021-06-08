package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.AuthClient;

/**
 * 授权客户端(AuthClient) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:12
 * @since 1.0.0
 */
@Mapper
public interface AuthClientMapper extends CrudMapper<AuthClient> {

}