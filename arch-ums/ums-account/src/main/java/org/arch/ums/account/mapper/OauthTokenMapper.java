package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.OauthToken;

/**
 * 第三方账号授权(OauthToken) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:55:50
 * @since 1.0.0
 */
@Mapper
public interface OauthTokenMapper extends BaseMapper<OauthToken> {

}