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
 * 产品-店铺(product_store)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_store")
public class ProductStore extends Model<ProductStore> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 店铺ID,分布式ID生成
     */
    private String storeId;
    /**
     * 商店名称
     */
    private String storeName;
    /**
     * 店铺头像
     */
    private String avatar;
    /**
     * 账号ID
     */
    private String accountId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 经营范围
     */
    private String bizScope;
    /**
     * 是否认证通过
     */
    private Integer approve;
    /**
     * 店铺等级
     */
    private Integer grade;
    /**
     * 是否删除
     */
    private Integer deleted;
    /**
     * 时间戳
     */
    private Date st;

}
