package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.properties.PackageProperties;
import org.arch.framework.automate.generater.properties.TableProperties;

import java.nio.file.Path;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO 40599365 胡
 * @weixin PN15855012581
 * @date 2/22/2021 6:17 PM
 */
public interface Buildable {
    /**
     * 获取模板名称
     * @return
     */
    TemplateName getTemplateName();

    /**
     * 构架模板文件
      * @param fileName
     * @param filePath
     */
    /*void buildFile(String fileName, Path filePath);*/

    /**
     * 构建模板文件数据
     * @param fileName
     * @param filePath
     * @param packageProperties
     * @param tableProperties
     * @return
     */
    Map<String,Object> buildData(String fileName, Path filePath, PackageProperties packageProperties, TableProperties tableProperties);
}
