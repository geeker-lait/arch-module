package org.arch.pms.sku.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 产品-售后及服务(product_aftersale)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_aftersale")
public class ProductAftersale extends Model<ProductAftersale> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 店铺ID
     */
    private String storeId;
    /**
     * 售后或服务ID
     */
    private String serviceId;
    /**
     * 售后或服务名称
     */
    private String serviceName;
    /**
     * 费用
     */
    private BigDecimal fee;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}
