package com.uni.skit.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.uni.skit.user.dto.BankCardDto;

/**
 * author: guoyuanhao
 * date: 2020/3/31 17:53
 * desc:
 */
public interface IUserBankcardBiz {

    JSONObject sendBindCardCode(BankCardDto bankCardDto);

    JSONObject bindCard(BankCardDto bankCardDto);

}
