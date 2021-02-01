package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.BankCard;
import org.arch.ums.user.mapper.BankCardMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户银行卡信息(BankCard) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:03:40
 * @since 1.0.0
 */
@Slf4j
@Repository
public class BankCardDao extends ServiceImpl<BankCardMapper, BankCard> implements CrudDao<BankCard> {

}