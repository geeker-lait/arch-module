package org.arch.ums.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.BankCard;

/**
 * 用户银行卡信息(BankCard) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:03:47
 * @since 1.0.0
 */
@Mapper
public interface BankCardMapper extends BaseMapper<BankCard> {

}