package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
public class ExcelProperties {
    // excel 文件
    private String file;
    // 表头
    private Map<String,Object> heads;
    // 其他配置
}
