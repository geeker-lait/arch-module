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
 * 用户-身份信息(user_idcard)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_idcard")
public class UserIdcard extends Model<UserIdcard> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 名族
     */
    private String nation;
    /**
     * 居住地
     */
    private String domicile;
    /**
     * 颁发机构
     */
    private String signOrg;
    /**
     * 时间戳
     */
    private Date st;

}