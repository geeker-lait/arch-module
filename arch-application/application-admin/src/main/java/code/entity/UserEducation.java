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
 * 用户-学历信息(user_education)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_education")
public class UserEducation extends Model<UserEducation> implements Serializable {
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
     * 证书编码
     */
    private String certificateNo;
    /**
     * 证书名称
     */
    private String certificateName;
    /**
     * 证书登记机构
     */
    private String certificateOrg;
    /**
     * 学历(如: 大专, 本科, 硕士, 博士)
     */
    private String certificateLevel;
    /**
     * 顺序
     */
    private Integer sorted;
    /**
     * 颁发时间
     */
    private Date awardtime;
    /**
     * 时间戳
     */
    private Date st;

}