package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.BankCard;
import org.springframework.stereotype.Service;

/**
 * 用户银行卡信息(BankCard) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:03:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BankCardService extends CrudService<BankCard, java.lang.Long> {

}