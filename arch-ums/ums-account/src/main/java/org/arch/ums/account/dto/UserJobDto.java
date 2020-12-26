package org.arch.ums.account.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserJobDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //公司名称
    private String companyName;
    //行业
    private String industry;
    //职业
    private String profession;
    //职位
    private String position;
    //职业类型
    private String professionTyp;
    // 职业身份
    private String careerTyp;
    //薪资
    private String salary;
    //公司电话
    private String telephone;
    //分号机
    private String telExtNo;
    //银行代发薪水
    private String income;
    //非银行代发薪水
    private String notIncome;
    //月流水
    private String monthRunning;
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
     * 账号id（分布式ID生成）
     */
    private String userId;
    /**
     * 账户名,手机号
     */
    private String accountName;

    private UUserAddressDto userAddress;

}
