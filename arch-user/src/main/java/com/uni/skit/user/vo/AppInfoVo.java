package com.uni.skit.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: app的常用信息
 *
 * @author kenzhao
 * @date 2020/4/21 16:00
 */
@Data
public class AppInfoVo implements Serializable {
    private static final long serialVersionUID = -4991848789483289045L;
    private String kfPhone;
}