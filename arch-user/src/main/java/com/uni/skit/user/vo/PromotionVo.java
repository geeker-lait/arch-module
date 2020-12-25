package com.uni.skit.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: 金融生活页面
 *
 * @author kenzhao
 * @date 2020/4/15 17:30
 */
@Data
public class PromotionVo implements Serializable {
    private static final long serialVersionUID = -1211591558045309014L;
    private String appUrl;
    private String appVersion;
    private String temp;
}