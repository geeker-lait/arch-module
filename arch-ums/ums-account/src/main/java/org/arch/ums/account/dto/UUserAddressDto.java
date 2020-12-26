package org.arch.ums.account.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
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
public class UUserAddressDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 用户地址
     */
    @NotNull(message = "地址不能为空")
    private String address;

    /**
     * 地址类型，0:户籍地址，1：居住地址，2：工作地址
     */
    private Integer addressTyp;

    /**
     * 市
     */
    @NotNull(message = "市不能为空")
    private String city;

    /**
     * 区
     */
    @NotNull(message = "区不能为空")
    private String district;

    /**
     * 地址顺序
     */
    private Integer indx;

    /**
     * 省
     */
    @NotNull(message = "省不能为空")
    private String province;

    /**
     * 账号id（分布式ID生成）
     */
    private String userId;

}
