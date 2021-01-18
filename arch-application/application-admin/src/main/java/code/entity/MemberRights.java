package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 会员权益(member_rights)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("member_rights")
public class MemberRights extends Model<MemberRights> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 会员级别id
     */
    private Long memberLevelId;
    /**
     * 权益类型
     */
    private Integer rightsTyp;
    /**
     * 权益名称
     */
    private String rightsName;
    /**
     * 权益值
     */
    private String rightsValue;
    /**
     * 权益码
     */
    private String rightsCode;

}