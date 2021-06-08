package org.arch.ums.user.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.user.dto.BankCardRequest;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户银行卡信息(BankCard) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:59:11
 * @since 1.0.0
 */

@RestController
@RequestMapping("/user/bank/card")
public interface BankCardRest extends CrudRest<BankCardRequest, java.lang.Long, BankCardSearchDto> {

}

