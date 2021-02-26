package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.arch.framework.automate.generater.core.SchemaSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
public class ExcelProperties implements SchemaSource {
    // excel 文件
    private String file;
    // 表头
    private Map<String,String> heads = new HashMap<>();
    // 其他配置

}
