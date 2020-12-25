package com.uni.skit.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yueshang
 * @since 2020-03-28
 */
@Data
public class UUserRelationshipDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 账户名,手机号
     */
    private String accountName;
    /**
     * 应用CODE
     */
    private String appCode;

    /**
     * 应用ID
     */
    private String appId;



    private Boolean deleted;



    /**
     * 顺序
     */
    private Integer indexs;

    /**
     * 关系人手机号
     */
    private String mobile;

    /**
     * 联系人名称
     */
    private String name;

    /**
     * 关系：母亲/父亲/朋友/同学/同事
     */
    private String relationship;

    /**
     * 联系人类型(0:紧急联系人1:间接联系人,2:重要联系人...)
     */
    private String relationshipType;

    /**
     * 账号id（分布式ID生成）
     */
    private String userId;


}
