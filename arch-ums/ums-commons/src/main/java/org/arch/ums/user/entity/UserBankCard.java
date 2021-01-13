package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户银行卡信息(user_bank_card)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_banK_card")
public class UserBankCard extends Model<UserBankCard> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户银行卡信息表ID
     */
    @TableId
	private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 卡bin 如: 6221, 6222
     */
    private String cardBin;
    /**
     * 卡code 如: ICBC, ABC
     */
    private String cardCode;
    /**
     * 卡cvv
     */
    private String cvv;
    /**
     * 卡类型:0: 储蓄卡, 1: 借记卡
     */
    private Integer cardtyp;
    /**
     * 基于user_id的顺序
     */
    private Integer sorted;
    /**
     * 有效期
     */
    private Date validity;
    /**
     * 时间戳
     */
    private Date st;

}