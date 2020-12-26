package org.arch.ums.account.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/31 15:35
 */
@Data
public class UserAttachmentDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 应用CODE
     */
    private String appCode;

    /**
     * 应用ID
     */
    private String appId;


    /**
     * 类型(房产证照片/驾驶照照片/保单照片/学历照片/身份证)
     */
    private String attachmentName;

    /**
     * 名称(正面/反面/其他...)
     */
    private String attchmentTyp;

    /**
     * 顺序
     */
    private Integer indx;

    /**
     * 附件链接
     */
    private String url;

    /**
     * 账号id（分布式ID生成）
     */
    private String userId;

}