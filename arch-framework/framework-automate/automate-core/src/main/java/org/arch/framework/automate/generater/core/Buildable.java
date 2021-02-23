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

    TemplateName getTemplateName();

    Map<String,Object> buildData(Path filePath, PackageProperties packageProperties, TableProperties tableProperties);
}
