package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class ColumnsProperties {
    private String name;
    private String typ;
    private String length;
    private String comment;
}
