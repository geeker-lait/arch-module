package org.arch.framework.automate.generater.properties;

import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaMetadata;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 6:08 PM
 */
@Data
public class XmindProperties implements SchemaMetadata {
    private String pkg;
    private String apiClass;
    private List<MethodProperties> methods;
}
