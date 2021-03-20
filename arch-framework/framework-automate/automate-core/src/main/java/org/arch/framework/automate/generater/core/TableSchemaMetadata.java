package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.properties.TableProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/19/2021 3:48 PM
 */
public interface TableSchemaMetadata {
    List<TableProperties> getTables();
}
