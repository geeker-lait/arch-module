package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单-评价(order_topic)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_topic")
public class OrderTopic extends Model<OrderTopic> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 单号
     */
    private Long orderNo;
    /**
     * 卖方账号ID
     */
    private Long sellerAccountId;
    /**
     * 卖方账号名称
     */
    private String sellerAccountName;
    /**
     * 买方账号ID
     */
    private Long buyerAccountId;
    /**
     * 买方账号名称
     */
    private String buyerAccountName;
    /**
     * 点赞数
     */
    private Long point;
    /**
     * 评论分数
     */
    private Integer score;
    /**
     * 图片列表
     */
    private String pics;
    /**
     * 内容
     */
    private String content;
    /**
     * 评价时间
     */
    private Date commentTime;
    /**
     * 应用ID
     */
    private Long appId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 时间戳
     */
    private Date st;

}