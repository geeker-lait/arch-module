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
 * 产品-相册图片(product_album_pic)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product_album_pic")
public class ProductAlbumPic extends Model<ProductAlbumPic> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 相册id
     */
    private String albumId;
    /**
     * 图片url
     */
    private String picUri;
    /**
     * 图片名称
     */
    private String picName;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}
