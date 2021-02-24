package org.arch.framework.automate.generater.config.properties;

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
    // 表头
    private Map<String,Object> heads;
    // 其他配置
}