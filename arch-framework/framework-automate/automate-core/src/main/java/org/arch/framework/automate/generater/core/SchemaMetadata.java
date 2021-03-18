package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.properties.MethodProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: api/mvc/或其他数据结构  标记用
 * @weixin PN15855012581
 * @date :
 */
public interface SchemaMetadata {

    String getName();

    List<TableProperties> getTables();

    List<MethodProperties> getApis();
}
