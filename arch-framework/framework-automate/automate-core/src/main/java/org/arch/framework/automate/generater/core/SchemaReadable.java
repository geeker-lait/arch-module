package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.properties.SchemaProperties;

import java.io.IOException;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:07 AM
 */
public interface SchemaReadable<S extends ConfigProperties> {

    SchemaType getTyp();

    /**
     * 读取
     *
     * @param schemaProperties
     * @throws IOException
     */
    <T extends SchemaData> List<T> read(SchemaProperties schemaProperties);

}
