package org.arch.ums.user.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
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
@RequiredArgsConstructor
@Repository
public class BankCardDao extends CrudServiceImpl<BankCardMapper, BankCard> implements CrudDao<BankCard> {
    private final BankCardMapper bankCardMapper;
}