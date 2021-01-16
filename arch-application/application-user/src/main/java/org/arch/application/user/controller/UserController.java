package org.arch.application.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 用户相关功能
 */
@Api(tags = "用户相关")
@RestController
public class UserController {

//    @Autowired
//    private IUserBiz iUserBiz;
//
//    @ApiOperation(value = "查询用户基本信息", authorizations = @Authorization(value = "token"))
//    @PostMapping("/user/getBaseInfo")
//    public ApiBaseResult getBaseInfo() {
//        return iUserBiz.getBaseInfo();
//    }
//
//    @ApiOperation(value = "保存用户基本信息", authorizations = @Authorization(value = "token"))
//    @PostMapping("/user/saveBaseInfo")
//    public ApiBaseResult saveBaseInfo(@Validated @RequestBody UserDto userDto) {
//        return iUserBiz.saveBaseInfo(userDto);
//    }
//
//    @ApiOperation(value = "获取当前用户信息", authorizations = @Authorization(value = "token"))
//    @GetMapping("/user/getCurrentUser")
//    public ApiBaseResult getCurrentUser() {
//        return ApiBaseResult.success(iUserBiz.getCurrentUserInfo());
//    }
//
//    @ApiOperation(value = "查询用户工作信息", authorizations = @Authorization(value = "token"))
//    @PostMapping("/user/getUserJob")
//    public ApiBaseResult getUserJob() {
//        return iUserBiz.getUserJob();
//    }
//
//    @ApiOperation(value = "编辑工作信息", authorizations = @Authorization(value = "token"))
//    @PostMapping("/user/addUserJob")
//    public ApiBaseResult getAccountCode(@Validated @RequestBody UserJobDto userJobDto, HttpServletRequest request) {
//        return iUserBiz.addUserJob(userJobDto);
//    }
//
//    @ApiOperation(value = "查询联系人信息", authorizations = @Authorization(value = "token"))
//    @PostMapping("/user/getUserRelationship")
//    public ApiBaseResult getUserRelationship() {
//        return iUserBiz.getUserRelationship();
//    }
//
//    @ApiOperation(value = "编辑联系人信息", authorizations = @Authorization(value = "token"))
//    @PostMapping("/user/addUserRelationship")
//    public ApiBaseResult getAccountCode(@Validated @RequestBody List<UUserRelationshipDto> userRelationshipDtos, HttpServletRequest request) {
//        return iUserBiz.addUUserRelationship(userRelationshipDtos);
//    }
}
