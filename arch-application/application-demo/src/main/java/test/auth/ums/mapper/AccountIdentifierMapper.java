package test.auth.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import test.auth.ums.entity.AccountIdentifier;

/**
 * 用户-标识(AccountIdentifier) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-26 22:59:15
 * @since 1.0.0
 */
@Mapper
public interface AccountIdentifierMapper extends BaseMapper<AccountIdentifier> {

}