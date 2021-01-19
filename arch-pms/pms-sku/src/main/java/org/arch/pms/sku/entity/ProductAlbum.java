package org.arch.pms.sku.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 产品-相册(product_album)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_album")
public class ProductAlbum extends Model<ProductAlbum> implements Serializable {
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
     * 相册名称
     */
    private String albumName;
    /**
     * 相册描述
     */
    private String descr;
    /**
     * 相册封面
     */
    private Integer albumCover;
    /**
     * 排序
     */
    private Long sorted;

}
