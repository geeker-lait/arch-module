package org.arch.ums.account.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/28 16:17
 */
@Data
public class AuthUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String code;
    private String username;
}