package org.arch.pms.sku.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 产品-佐料(product_relish)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_relish")
public class ProductRelish extends Model<ProductRelish> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 产品id
     */
    private String spuId;
    /**
     * 赠送的成长值
     */
    private Integer giftGrowth;
    /**
     * 赠送的积分
     */
    private Integer giftPoint;
    /**
     * 限制使用的积分数
     */
    private Integer pointLimit;
    /**
     * 时间戳
     */
    private Date st;

}
