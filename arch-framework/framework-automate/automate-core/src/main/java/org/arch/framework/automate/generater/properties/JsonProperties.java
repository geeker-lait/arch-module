package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.ToString;
import org.arch.framework.automate.generater.core.SchemaSource;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@ToString
public class JsonProperties  implements SchemaSource {
    private String content;
}
