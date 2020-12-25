package com.uni.skit.user.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uni.app.entity.mybatis.Version;
import com.uni.app.service.IVersionService;
import com.uni.common.constant.RedisKeyCode;
import com.uni.common.exception.BizException;
import com.uni.common.sms.ChuangLanSmsService;
import com.uni.common.utils.RedisUtils;
import com.uni.skit.user.biz.IPromotionBiz;
import com.uni.skit.user.dto.PromotionDto;
import com.uni.skit.user.vo.ApiBaseResult;
import com.uni.skit.user.vo.AppInfoVo;
import com.uni.skit.user.vo.PromotionVo;
import com.ytec.yuap.uacs.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/4/17 15:03
 */
@Service
@Slf4j
public class PromotionBizImpl implements IPromotionBiz {
    @Autowired
    private IVersionService versionService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Environment env;
    @Autowired
    private ChuangLanSmsService chuangLanSmsService;

    /**
     * 发送短信 提供最新版本的地址
     *
     * @param promotionDto
     * @return
     */
    @Override
    public ApiBaseResult sendSms(PromotionDto promotionDto, HttpServletRequest request) {
        String ipStr = IpUtil.getRemoteIP(request);
        String redisKey = RedisKeyCode.GETSMSCODE + promotionDto.getAppId() + promotionDto.getSource() + promotionDto.getAccountName();
        String maxRedisKey = RedisKeyCode.GETSMSCODE_MAXCOUNT + promotionDto.getSource() + ipStr;
        //最大限制次数
        Object useCount = redisUtils.get(maxRedisKey);
        Integer maxCount = env.getProperty("smsCode.maxCount",Integer.class);
        if (useCount == null || maxCount == null) {
            useCount = 0;
        }
        Integer count = Integer.parseInt(useCount.toString());
        if (count > maxCount) {
            throw new BizException("休息会再注册吧！");
        }
        count++;
        //生成验证码
        String accountCode = RandomStringUtils.random(4, "0123456789");
        // 保存

        //调用短信，发送
        // 发送短信功能 kenzhao by 2020/3/28 17:09
//        try {
//            chuangLanSmsService.sendSms(accountCode, promotionDto.getAccountName());
//        } catch (Exception e) {
//            throw new BizException("短信发送失败，请重试");
//        }

        //发送成功后缓存设置有效期
        redisUtils.set(maxRedisKey, count.toString(), 1, TimeUnit.HOURS);
        Integer expiration = env.getProperty("smsCode.expiration", Integer.class);
        redisUtils.set(redisKey, accountCode, expiration, TimeUnit.MINUTES);
        PromotionVo promotionVo = new PromotionVo();
        List<Version> versions = versionService.list(new QueryWrapper<Version>().lambda().eq(Version::getAppCode, promotionDto.getAppId()).eq(Version::getAppType, promotionDto.getAppType()).orderByDesc(Version::getCtime));
        if (versions != null && versions.size() > 0) {
            Version version = versions.get(0);
            promotionVo.setAppUrl(version.getAppUrl());
            promotionVo.setAppVersion(version.getAppVersion());
        }
        promotionVo.setTemp(accountCode);
        return ApiBaseResult.success(promotionVo);
    }

    /**
     * 提供最新版本的地址
     *
     * @param promotionDto
     * @return
     */
    @Override
    public ApiBaseResult getAppVersion(PromotionDto promotionDto, HttpServletRequest request) {
        List<Version> versions = versionService.list(new QueryWrapper<Version>().lambda().eq(Version::getAppCode, promotionDto.getAppId()).eq(Version::getAppType, promotionDto.getAppType()).orderByDesc(Version::getCtime));

        PromotionVo promotionVo = new PromotionVo();
        if (versions != null && versions.size() > 0) {
            Version version = versions.get(0);
            promotionVo.setAppUrl(version.getAppUrl());
            promotionVo.setAppVersion(version.getAppVersion());
            return ApiBaseResult.success(promotionVo);
        }
        return ApiBaseResult.success();
    }

    /**
     * 获取app内的常用信息
     *
     * @param promotionDto
     * @param request
     * @return
     */
    @Override
    public ApiBaseResult getAppInfo(PromotionDto promotionDto, HttpServletRequest request) {
        String kfPhone = RedisKeyCode.APP_INFO + promotionDto.getAppId() + "KFDH";
        //最大限制次数
        String kfPhoneStr = redisUtils.getStr(kfPhone);
        AppInfoVo info = new AppInfoVo();
        info.setKfPhone(kfPhoneStr);
        return ApiBaseResult.success(info);
    }
}