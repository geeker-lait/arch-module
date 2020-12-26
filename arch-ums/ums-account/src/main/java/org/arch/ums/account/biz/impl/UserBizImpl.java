package org.arch.ums.account.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.uni.account.entity.mybatis.AAccount;
import com.uni.account.service.IAAccountService;
import com.uni.common.constant.BizIdCode;
import com.uni.common.entity.TokenInfo;
import com.uni.common.exception.BizException;
import com.uni.common.service.IdService;
import com.uni.common.utils.GsonUtils;
import com.uni.common.utils.RedisUtils;
import com.uni.common.utils.SecurityUtils;
import com.uni.common.utils.StringUtils;
import org.arch.ums.account.biz.IUserBiz;
import com.uni.skit.user.config.security.TokenProvider;
import org.arch.ums.account.dto.UUserAddressDto;
import org.arch.ums.account.dto.UUserRelationshipDto;
import org.arch.ums.account.dto.UserDto;
import org.arch.ums.account.dto.UserJobDto;
import org.arch.ums.account.vo.ApiBaseResult;
import org.arch.ums.account.vo.JwtUserVo;
import com.uni.user.entity.*;
import com.uni.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 用户相关
 *
 * @author kenzhao
 * @date 2020/3/30 19:05
 */
@Service
@Slf4j
public class UserBizImpl implements IUserBiz {
    @Autowired
    private IUUserService userService;
    @Autowired
    private IUUserJobService userJobService;
    @Autowired
    private IAAccountService iaAccountService;
    @Autowired
    private IUUserRelationshipService userRelationshipService;
    @Autowired
    private IdService idService;
    @Autowired
    private IUUserAddressService userAddressService;
    @Autowired
    private IUUserEducationService userEducationServicel;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Environment env;

    /**
     * 保存或编辑用户基本信息
     * @param userDto
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ApiBaseResult saveBaseInfo(UserDto userDto) {

        UUserAddressDto uUserAddressDto = userDto.getUserAddress();
        TokenInfo currentAccount = SecurityUtils.getCurrentUser();

        Wrapper<UUser> queryWrapper = new QueryWrapper<UUser>().lambda().eq(UUser::getAppId, userDto.getAppId()).eq(UUser::getIdcard, userDto.getIdcard()).eq(
                UUser::getDeleted, false);
        UUser user = userService.getOne(queryWrapper);

        //传入的类型是新增
        if (user != null  && userDto.getId() != null) {
            throw new BizException("该身份证号已注册！");
        }
        if (userDto.getId() == null || user == null) {
            String userId = idService.generateId(BizIdCode.USER);
            user = new UUser();
            user.setUserId(userId);
        }
        user.setIdcard(userDto.getIdcard());
        user.setIdcard(userDto.getIdcard());
        user.setUserName(userDto.getUserName());
        user.setAppId(currentAccount.getAppId());
        user.setAppCode(currentAccount.getAppCode());
        boolean isOk = userService.saveOrUpdate(user);
        if (!isOk) {
            throw new BizException("保存用户基本信息失败");
        }
        UUserAddress address = GsonUtils.fromJson(GsonUtils.toJson(uUserAddressDto),UUserAddress.class);
        address.setAddressTyp(1);
        address.setUserId(user.getUserId());
        address.setAppId(currentAccount.getAppId());
        address.setAppCode(currentAccount.getAppCode());
        userAddressService.saveOrUpdate(address);
        UUserEducation education = new UUserEducation(userDto.getDegree(), user.getUserId(), currentAccount.getAppId(), currentAccount.getAppCode());
        if (userDto.getDegreeId() != null) {
            education.setId(education.getId());
        }
        userEducationServicel.saveOrUpdate(education);

        Wrapper<AAccount> queryAccount = new QueryWrapper<AAccount>().lambda().eq(AAccount::getAccountId, currentAccount.getAccountId())
                .eq(AAccount::getAppId, currentAccount.getAppId()).eq(AAccount::getDeleted, false);
        AAccount aAccountSel = iaAccountService.getOne(queryAccount);
        if (aAccountSel == null) {
            throw new BizException("该账户有误！");
        }
        aAccountSel.setUserId(user.getUserId());
        aAccountSel.setUtime(LocalDateTime.now());
        iaAccountService.saveOrUpdate(aAccountSel);

        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        tokenInfo.setUserId(user.getUserId());
        //更新token
        Authentication authentication2 = new UsernamePasswordAuthenticationToken(GsonUtils.toJson(tokenInfo), null, Arrays.asList(new SimpleGrantedAuthority("TEST")));
        SecurityContextHolder.getContext().setAuthentication(authentication2);
        // 生成令牌
        String token = tokenProvider.createToken(authentication2);
        redisUtils.set(env.getProperty("jwt.online-key") + "::" + token, JSONObject.toJSONString(tokenInfo), new Integer(env.getProperty("jwt.token-validity-in-seconds")) / 1000);
        JwtUserVo jwtUserVo = new JwtUserVo();
        jwtUserVo.setAccountName(tokenInfo.getAccountName());
        jwtUserVo.setToken(env.getProperty("jwt.token-start-with") + " " + token);
        return ApiBaseResult.success(jwtUserVo);
    }
    /**
     * 获取当前登陆用户信息
     * @return
     */
    @Override
    public UUser getCurrentUserInfo() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String userId = tokenInfo.getUserId();
        if (StringUtils.isEmpty(userId)) {
            Wrapper<AAccount> queryAccount = new QueryWrapper<AAccount>().lambda().eq(AAccount::getAccountId, tokenInfo.getAccountId())
                    .eq(AAccount::getAppId, tokenInfo.getAppId()).eq(AAccount::getDeleted, false);
            AAccount aAccountSel = iaAccountService.getOne(queryAccount);
            if (aAccountSel == null || StringUtils.isEmpty(aAccountSel.getUserId())) {
                throw new BizException("当前账户暂无用户信息！");
            }
            userId = aAccountSel.getUserId();
        }
        Wrapper<UUser> queryWrapper = new QueryWrapper<UUser>().lambda().eq(UUser::getUserId, userId).eq(
                UUser::getDeleted, false);
        UUser user = userService.getOne(queryWrapper);
        if (user == null) {
            throw new BizException("当前账户暂无用户信息！");
        }
        return user;
    }

    /**
     * 新增或更新用户工作信息
     *
     * @param userJobDto
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ApiBaseResult addUserJob(UserJobDto userJobDto) {
        //事务，更新工作信息
        UUserAddressDto uUserAddressDto = userJobDto.getUserAddress();
        String userJobDtoStr = GsonUtils.toJson(userJobDto);
        UUserJob uUserJob = GsonUtils.fromJson(userJobDtoStr, UUserJob.class);
        String userId = userJobDto.getUserId();
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        if (userId == null) {
            userId = tokenInfo.getUserId();
            uUserJob.setUserId(userId);
        }
        if (uUserAddressDto != null) {
            UUserAddress uUserAddress = GsonUtils.fromJson(GsonUtils.toJson(uUserAddressDto),UUserAddress.class);
            uUserAddress.setAppCode(uUserAddress.getAppCode());
            uUserAddress.setAppId(tokenInfo.getAppId());
            uUserAddress.setUserId(userId);
            uUserAddress.setAddressTyp(2);
            userAddressService.saveOrUpdate(uUserAddress);
        }

        boolean isOk = userJobService.saveOrUpdate(uUserJob);
        if (isOk) {
            return ApiBaseResult.success();
        }
        return ApiBaseResult.error("用户工作信息处理失败");
    }

    /**
     * 新增或更新用户联系人信息
     *
     * @param userRelationshipDtos
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ApiBaseResult addUUserRelationship(List<UUserRelationshipDto> userRelationshipDtos) {
        if (userRelationshipDtos == null || userRelationshipDtos.size() < 1) {
            throw new BizException("请提供用户联系人信息！");
        }
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();

        //先删除原用户信息
        userRelationshipService.remove(new UpdateWrapper<UUserRelationship>().lambda().eq(UUserRelationship::getAppId,tokenInfo.getAppId()).eq(UUserRelationship::getUserId,tokenInfo.getUserId()));
        for (UUserRelationshipDto userRelationshipDto : userRelationshipDtos
        ) {
            String userRelationshipDtoStr = GsonUtils.toJson(userRelationshipDto);
            UUserRelationship uUserRelationship = GsonUtils.fromJson(userRelationshipDtoStr, UUserRelationship.class);
            String userId = uUserRelationship.getUserId();
            if (userId == null) {
                userId = tokenInfo.getUserId();
                uUserRelationship.setUserId(userId);
            }
            boolean isOk = userRelationshipService.saveOrUpdate(uUserRelationship);
            if (!isOk) {
                return ApiBaseResult.error("用户联系人信息处理失败");
            }

        }
        return ApiBaseResult.success();
    }

    /**
     * 获取用户基本信息
     *
     * @param
     * @return
     */
    @Override
    public ApiBaseResult getBaseInfo() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String userId = tokenInfo.getUserId();
        Wrapper<UUser> queryWrapper = new QueryWrapper<UUser>().lambda().eq(UUser::getUserId, userId).eq(
                UUser::getDeleted, false);
        UUser user = userService.getOne(queryWrapper);
        if (user == null) {
            return ApiBaseResult.success();
        }
        String userStr = GsonUtils.toJson(user);
        UserDto userDto = GsonUtils.fromJson(userStr, UserDto.class);


        //  地址信息和教育信息
        UUserAddress address = new UUserAddress();
        address.setAddressTyp(1);
        address.setUserId(user.getUserId());
        address.setAppId(user.getAppId());
        address.setAppCode(user.getAppCode());
        address = userAddressService.getOne(new QueryWrapper<>(address));
        if (address != null) {
            UUserAddressDto uUserAddress = GsonUtils.fromJson(GsonUtils.toJson(address),UUserAddressDto.class);
            userDto.setUserAddress(uUserAddress);
        }
        UUserEducation education = new UUserEducation();
        education.setUserId(user.getUserId());
        education.setAppId(user.getAppId());
        education.setAppCode(user.getAppCode());
        education = userEducationServicel.getOne(new QueryWrapper<>(education));
        if (education != null) {
            userDto.setDegree(education.getDegreeName());
            userDto.setDegreeId(education.getId());
        }
        return ApiBaseResult.success(userDto);
    }

    /**
     * 查看用户工作信息
     *
     * @return
     */
    @Override
    public ApiBaseResult getUserJob() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String userId = tokenInfo.getUserId();
        Wrapper<UUserJob> queryWrapper = new QueryWrapper<UUserJob>().lambda().eq(UUserJob::getAppId, tokenInfo.getAppId()).eq(UUserJob::getUserId, userId).eq(
                UUserJob::getDeleted, false);
        UUserJob userJob = userJobService.getOne(queryWrapper);
        if (userJob == null) {
            return ApiBaseResult.success();
        }
        String userJobStr = GsonUtils.toJson(userJob);
        UserJobDto userJobDto = GsonUtils.fromJson(userJobStr, UserJobDto.class);

        Wrapper<UUserAddress> userAddressWrapper = new QueryWrapper<UUserAddress>().lambda().eq(UUserAddress::getAppId, tokenInfo.getAppId()).eq(UUserAddress::getUserId, userId).eq(UUserAddress::getAddressTyp, 2).eq(
                UUserAddress::getDeleted, false);
        UUserAddress userAddress = userAddressService.getOne(userAddressWrapper);
        String userAddressStr = GsonUtils.toJson(userAddress);
        UUserAddressDto userAddressDto = GsonUtils.fromJson(userAddressStr, UUserAddressDto.class);
        userJobDto.setUserAddress(userAddressDto);
        return ApiBaseResult.success(userJobDto);
    }

    /**
     * 查看用户联系人信息
     *
     * @return
     */
    @Override
    public ApiBaseResult getUserRelationship() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String userId = tokenInfo.getUserId();
        Wrapper<UUserRelationship> queryWrapper = new QueryWrapper<UUserRelationship>().lambda().eq(UUserRelationship::getAppId, tokenInfo.getAppId()).eq(UUserRelationship::getUserId, userId).eq(
                UUserRelationship::getDeleted, false);
        List<UUserRelationship> uUserRelationships = userRelationshipService.list(queryWrapper);
        if (uUserRelationships == null || uUserRelationships.size() < 1) {
            return ApiBaseResult.success();
        }
//        String uUserRelationshipStr = GsonUtils.toJson(uUserRelationship);
//        UUserRelationshipDto userRelationshipDto = GsonUtils.fromJson(uUserRelationshipStr, UUserRelationshipDto.class);
        return ApiBaseResult.success(uUserRelationships);
    }
}