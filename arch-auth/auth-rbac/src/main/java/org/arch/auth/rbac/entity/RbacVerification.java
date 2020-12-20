package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.time.LocalDate;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 4:12 PM
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_verification")
public class RbacVerification extends CrudEntity<RbacVerification>  {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、qq号码、微信号、微博号；
     */
    private String identifier;
    /**
     * 授权凭证【credential】：站内账号是密码、第三方登录是token；
     */
    private String credential;
    /**
     * 登录类型【identitytype】：登录类别，如：系统用户、邮箱、手机，或者第三方的qq、微信、微博；
     */
    private String chanelType;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * isActive
     */
    private boolean deleted;
    /**
     * createdBy
     */
    private String createdBy;
    /**
     * createdDate
     */
    private LocalDate createdDate;
}
