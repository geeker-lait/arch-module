package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 统计-推广链接(statictics_promotion_link)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("statictics_promotion_link")
public class StaticticsPromotionLink extends Model<StaticticsPromotionLink> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 排序
     */
    private Integer indx;
    /**
     * 资源ID
     */
    private Long resourceId;
    /**
     * 资源URI链接
     */
    private String resourceUri;
    /**
     * 源（推广渠道标识源）推广渠道/渠道来源
     */
    private String source;

}