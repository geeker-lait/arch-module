package com.uni.skit.user.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uni.account.entity.mybatis.AAccount;
import com.uni.account.service.IAAccountService;
import com.uni.common.constant.BizIdCode;
import com.uni.common.constant.RedisKeyCode;
import com.uni.common.exception.BizException;
import com.uni.common.service.IdService;
import com.uni.common.sms.ChuangLanSmsService;
import com.uni.common.utils.GsonUtils;
import com.uni.common.utils.MD5Util;
import com.uni.common.utils.RedisUtils;
import com.uni.common.utils.StringUtils;
import com.uni.skit.user.biz.IAccountBiz;
import com.uni.skit.user.config.security.TokenProvider;
import com.uni.skit.user.dto.AuthAccountDto;
import com.uni.skit.user.dto.AuthRegDto;
import com.uni.skit.user.vo.JwtUserVo;
import com.ytec.yuap.uacs.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/28 16:06
 */
@Service
@Slf4j
public class AccountBizImpl implements IAccountBiz {

    private final static String SKIT_ADMIN_SALT = "SKIT_ADMIN_SALT";

    @Value("${smsCode.expiration}")
    private Long expiration;
    /**
     * 1小时的最大次数
     */
    @Value("${smsCode.maxCount}")
    private Integer maxCount;

    @Value("${rsa.private_key}")
    private String privateKey;

    @Value("${single.login:false}")
    private Boolean singleLogin;


    @Value("${jwt.token-start-with}")
    private String tokenStartWith;
    @Value("${jwt.online-key}")
    private String onlineKey;
    @Value("${jwt.token-validity-in-seconds}")
    private Integer tokenValidityInseconds;


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IAAccountService iaAccountService;
    @Autowired
    private IdService idService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ChuangLanSmsService chuangLanSmsService;

    /**
     * @param authRegDto
     * @Description: 获取验证码
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    @Override
    public JwtUserVo getAccountCode(AuthRegDto authRegDto, HttpServletRequest request) {
        String ipStr = IpUtil.getRemoteIP(request);
        String redisKey = RedisKeyCode.GETSMSCODE + authRegDto.getAppId() + authRegDto.getAccountName();
        String maxRedisKey = RedisKeyCode.GETSMSCODE_MAXCOUNT + ipStr;
        //最大限制次数
        Object useCount = redisUtils.get(maxRedisKey);
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
//            chuangLanSmsService.sendSms(accountCode, authRegDto.getAccountName());
//        } catch (Exception e) {
//            throw new BizException("短信发送失败，请重试");
//        }

        //发送成功后缓存设置有效期
        redisUtils.set(maxRedisKey, count.toString(), 1, TimeUnit.HOURS);
        redisUtils.set(redisKey, accountCode, expiration, TimeUnit.MINUTES);
        JwtUserVo jwtUserVo = new JwtUserVo();
        jwtUserVo.setAccountName(authRegDto.getAccountName());
        jwtUserVo.setToken(accountCode);
        return jwtUserVo;
    }

    /**
     * @param authRegDto
     * @Description: 注册
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    @Override
    public JwtUserVo reg(AuthRegDto authRegDto) throws Exception {
        //验证码非空验证
        String verifyCode = authRegDto.getVerifyCode();
        if (StringUtils.isEmpty(verifyCode)) {
            throw new BizException("验证码不可为空！");
        }
        String redisKey = RedisKeyCode.GETSMSCODE + authRegDto.getAppId() + authRegDto.getAccountName();
        String redisCode = (String) redisUtils.get(redisKey);
        //一致性验证
        if (!verifyCode.equals(redisCode)) {
            throw new BizException("验证码不正确！");
        }

        Wrapper<AAccount> queryWrapper = new QueryWrapper<AAccount>().lambda().eq(AAccount::getAccountName, authRegDto.getAccountName())
                .eq(AAccount::getAppId, authRegDto.getAppId());
        AAccount aAccountSel = iaAccountService.getOne(queryWrapper);
        if (aAccountSel == null) {
            //生成account表
            AAccount aAccount = new AAccount();
            aAccount.setAccountId(idService.generateId(BizIdCode.ACCOUNT));
            aAccount.setAccountName(authRegDto.getAccountName());
            aAccount.setAppCode(authRegDto.getAppCode());
            aAccount.setAppId(authRegDto.getAppId());
            aAccount.setSource(authRegDto.getSource());

            String inPwd = authRegDto.getPwd();
            if (inPwd != null && inPwd.length() > 6) {
                String pwdStr = MD5Util.MD5(inPwd, SKIT_ADMIN_SALT);
                aAccount.setPwd(pwdStr);
            }
            iaAccountService.save(aAccount);
        }
        //登陆
        AuthAccountDto authAccountDto = new AuthAccountDto();
        authAccountDto.setAccountName(authRegDto.getAccountName());
        authAccountDto.setPassword(authRegDto.getPwd());
        authAccountDto.setAppId(authRegDto.getAppId());
        return login(authAccountDto);
    }

    /**
     * @param authAccountDto
     * @Description: 登陆
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    @Override
    public JwtUserVo login(AuthAccountDto authAccountDto) throws Exception {
        //accountName+appId查询账户
        AAccount aAccountSel = new AAccount();
        Wrapper<AAccount> queryWrapper = new QueryWrapper<AAccount>().lambda().eq(AAccount::getAccountName, authAccountDto.getAccountName())
                .eq(AAccount::getAppId, authAccountDto.getAppId());
        AAccount aAccount = iaAccountService.getOne(queryWrapper);
        //存在，则比对密码

        String inPwd = aAccount.getPwd();
        if (!StringUtils.isEmpty(inPwd) && !StringUtils.isEmpty(authAccountDto.getPassword())) {
            // 密码解密
//        RSA rsa = new RSA(privateKey, null);
//        String pwdStr = new String(rsa.encrypt(authAccountDto.getPassword(), KeyType.PrivateKey));
            String pwdStr = MD5Util.MD5(authAccountDto.getPassword(), SKIT_ADMIN_SALT);
            if (!pwdStr.equals(aAccount.getPwd())) {
                throw new BizException("密码不正确！");
            }
        }
        Authentication authentication2 = new UsernamePasswordAuthenticationToken(GsonUtils.toJson(aAccount), null, Arrays.asList(new SimpleGrantedAuthority("TEST")));
        SecurityContextHolder.getContext().setAuthentication(authentication2);
        // 生成令牌
        String token = tokenProvider.createToken(authentication2);

        redisUtils.set(onlineKey + "::" + token, JSONObject.toJSONString(aAccount), tokenValidityInseconds / 1000);

        JwtUserVo jwtUserVo = new JwtUserVo();
        jwtUserVo.setAccountName(authAccountDto.getAccountName());
        jwtUserVo.setToken(tokenStartWith + " " + token);
        return jwtUserVo;
    }

    /**
     * @param jwtUserVo
     * @Description: 登出
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    @Override
    public JwtUserVo logout(JwtUserVo jwtUserVo) {
        String token = tokenStartWith + " " + jwtUserVo.getToken();
        redisTemplate.delete(token);
        return null;
    }

    /**
     * @param authRegDto
     * @Description: 验证码登陆
     * @author kenzhao
     * @date 2020/3/28 16:34
     * @version V1.0
     */
    @Override
    public JwtUserVo verifiLogin(AuthRegDto authRegDto) throws Exception {
        return reg(authRegDto);
    }
}