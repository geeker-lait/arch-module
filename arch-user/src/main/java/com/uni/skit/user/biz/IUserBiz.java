package com.uni.skit.user.biz;

import com.uni.skit.user.dto.UUserRelationshipDto;
import com.uni.skit.user.dto.UserDto;
import com.uni.skit.user.dto.UserJobDto;
import com.uni.skit.user.vo.ApiBaseResult;
import com.uni.user.entity.UUser;

import java.util.List;

/**
 * Description: 用户相关
 *
 * @author kenzhao
 * @date 2020/3/30 19:05
 */
public interface IUserBiz {

    /**
     * 新增或更新用户工作信息
     *
     * @param userJobDto
     * @return
     */
    ApiBaseResult addUserJob(UserJobDto userJobDto);


    /**
     * 查看用户工作信息
     *
     * @return
     */
    ApiBaseResult getUserJob();

    /**
     * 新增或更新用户联系人信息
     *
     * @param userRelationshipDtos
     * @return
     */
    ApiBaseResult addUUserRelationship(List<UUserRelationshipDto> userRelationshipDtos);

    /**
     * 查看用户联系人信息
     *
     * @return
     */
    ApiBaseResult getUserRelationship();
    /**
     * 获取用户基本信息
     * @return
     */
    ApiBaseResult getBaseInfo();

    /**
     * 保存或编辑用户基本信息
     * @param userDto
     * @return
     */
    ApiBaseResult saveBaseInfo(UserDto userDto);

    /**
     * 获取当前登陆用户信息
     * @return
     */
    UUser getCurrentUserInfo();

}