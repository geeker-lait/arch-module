package com.uni.skit.user.dto;

import com.uni.user.entity.UUserAddress;
import com.uni.user.entity.UUserEducation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * author: guoyuanhao
 * date: 2020/3/30 18:58
 * desc:
 */
@Getter
@Setter
public class UserDto {
    private Long id;
    @NotNull(message = "姓名不能为空")
    private String userName;
    private String appId;
    @NotNull(message = "身份证号不能为空")
    private String idcard;
    @NotNull(message = "学历不能为空")
    private String degree;
    private Long degreeId;
    private UUserAddressDto userAddress;
}
