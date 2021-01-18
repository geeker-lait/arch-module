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
 * 订单-收发货方信息(order_swapper)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_swapper")
public class OrderSwapper extends Model<OrderSwapper> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 收/发货人姓名
     */
    private String userName;
    /**
     * 收/发货人手机
     */
    private String userPhone;
    /**
     * 类型：1:发货人，2:收货人
     */
    private Integer userTyp;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区/县
     */
    private String discint;
    /**
     * 街道/办事处
     */
    private String street;
    /**
     * 小区/村庄
     */
    private String village;
    /**
     * 收/发货人地址
     */
    private String adress;
    /**
     * 应用id
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