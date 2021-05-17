package org.arch.alarm.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 12:42 AM
 */
@Data
public class AlarmParamsDto implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空")
    private Long regId;

    /**
     * 冗余规则名称，展示用
     */
    private String regName;

    /**
     * 字典id冗余处理，查找用
     */
    @NotNull(message = "字典id不能为空")
    private Long dicId;

    /**
     * 库表,值如db.tb 确定唯一
     */
    @NotNull(message = "库表不能为空")
    private String dbtb;

    /**
     * 字段名，展示用
     */
    private String filedName;

    /**
     * 字段码
     */
    @NotNull(message = "字段code为不能为空")
    private String filedCode;

    /**
     * 字段类型
     */
    private String filedTyp;

    /**
     * 创建时间
     */
    private Date ctime;

}
