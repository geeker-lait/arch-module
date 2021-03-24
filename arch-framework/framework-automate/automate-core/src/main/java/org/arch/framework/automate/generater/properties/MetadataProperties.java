package org.arch.framework.automate.generater.properties;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/24/2021 10:19 AM
 */
@Data
public class MetadataProperties  {
    // api方法列表
    @NestedConfigurationProperty
    private List<MethodProperties> apis = new ArrayList<>();
    // entity实体列表
    @NestedConfigurationProperty
    private List<TableProperties> tables = new ArrayList<>();
}
