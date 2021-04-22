package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.ConfigProperties;

/**
 * @author lait.zhang@gmail.com
 * @description:
 * columns:
 *   - name:
 *     typ:
 *     length:
 * @weixin PN15855012581
 * @date 2/18/2021 9:02 AM
 */
@Data
@NoArgsConstructor
@ToString
public class ColumnsProperties implements ConfigProperties {
    // 名称
    private String name;
    // 类型
    private String typ;
    // 长度
    private String length;
    // 注释
    private String comment;
    // 是否是主键
    private Boolean pk;
    // 是否唯一
    private Boolean unique;
    // 是否为空
    private Boolean notnull;
}
