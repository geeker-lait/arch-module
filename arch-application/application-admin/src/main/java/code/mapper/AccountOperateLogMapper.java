package code.mapper;

import code.entity.AccountOperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账号操作记录(account_operate_log)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface AccountOperateLogMapper extends BaseMapper<AccountOperateLog> {

}
