package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.ConfigProperties;

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
@ToString
public class ExcelProperties implements ConfigProperties {
    // schema类型名称 mvc/api
    private String schemaReader;
    // excel 文件
    private String file;
    // 表头
    private Map<String,String> heads = new HashMap<>();
    //private Set<ReaderProperties> reads;

}
