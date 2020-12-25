package com.uni.skit.user.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uni.common.constant.BizIdCode;
import com.uni.common.constant.RedisKeyCode;
import com.uni.common.exception.BizException;
import com.uni.common.service.IdService;
import com.uni.skit.user.dto.BankCardDto;
import com.uni.user.entity.UUser;
import com.uni.user.entity.UUserBankcard;
import com.uni.user.service.IUUserBankcardService;
import com.uni.common.utils.RedisUtils;
import com.uni.common.utils.StringUtils;
import com.uni.skit.user.biz.IUserBankcardBiz;
import com.uni.skit.user.biz.IUserBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * author: guoyuanhao
 * date: 2020/3/31 18:10
 * desc:
 */
@Slf4j
@Service
public class UserBankcardBizImpl implements IUserBankcardBiz {

    @Autowired
    private IUserBiz iUserBiz;
    @Autowired
    private IUUserBankcardService userBankcardService;
    @Autowired
    private IdService idService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public JSONObject sendBindCardCode(BankCardDto bankCardDto) {
        UUser currentUser = iUserBiz.getCurrentUserInfo();

        String bankCardId = idService.generateId(BizIdCode.BANGKCARD);
        UUserBankcard bankcard = new UUserBankcard();
        bankcard.setBankcardId(bankCardId);
        bankcard.setUserId(currentUser.getUserId());
        bankcard.setUserName(bankCardDto.getName());
        bankcard.setReservedPhone(bankCardDto.getPhone());
        bankcard.setIdcard(bankCardDto.getIdCard());
        bankcard.setBankcard(bankCardDto.getBankCard());
        bankcard.setBankName(bankCardDto.getBankName());
        bankcard.setVerifyStatus(UUserBankcard.VerifyStatus.IN_VERIFY);
        boolean result = userBankcardService.save(bankcard);
        if (!result) {
            throw new BizException("发送绑卡验证码异常！");
        }
        String captcha = RandomStringUtils.random(4, "0123456789");
        redisUtils.set(RedisKeyCode.SEND_BANGKCARD_CAPTCHA + bankCardId, captcha, 1, TimeUnit.MINUTES);

        JSONObject jsb = new JSONObject();
        jsb.put("msg", "发送验证码成功");
        jsb.put("type", "sendCaptcha");
        jsb.put("bankCardId", bankCardId);
        jsb.put("captcha", captcha);
        return jsb;
    }

    @Override
    public JSONObject bindCard(BankCardDto bankCardDto) {
        if (StringUtils.isEmpty(bankCardDto.getCaptcha())) {
            throw new BizException("验证码不能为空！");
        }
        String bankCardId = bankCardDto.getBankCardId();
        if (StringUtils.isEmpty(bankCardDto.getBankCardId())) {
            throw new BizException("绑卡Id不能为空！");
        }
        Wrapper<UUserBankcard> queryWrapper = new QueryWrapper<UUserBankcard>().lambda().eq(UUserBankcard::getBankcardId, bankCardId).eq(
                UUserBankcard::getDeleted, false);
        UUserBankcard bankcard = userBankcardService.getOne(queryWrapper);
        if (bankcard == null || !bankcard.getVerifyStatus().equals(UUserBankcard.VerifyStatus.IN_VERIFY)) {
            throw new BizException("绑卡状态有误！");
        }
        String captcha = String.valueOf(redisUtils.get(RedisKeyCode.SEND_BANGKCARD_CAPTCHA + bankCardId));
        if (StringUtils.isEmpty(captcha)) {
            throw new BizException("验证码失效！");
        }
        if (!bankCardDto.getCaptcha().equals(captcha)) {
            throw new BizException("验证码有误！");
        }
        bankcard.setVerifyStatus(UUserBankcard.VerifyStatus.VERIFY_SUCCEED);
        bankcard.setUtime(LocalDateTime.now());
        userBankcardService.saveOrUpdate(bankcard);

        JSONObject jsb = new JSONObject();
        jsb.put("msg", "绑卡成功");
        jsb.put("type", "bankcard");
        return jsb;
    }

}
