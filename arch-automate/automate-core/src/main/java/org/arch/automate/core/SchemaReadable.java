package org.arch.automate.core;

import org.arch.automate.enums.SchemaType;
import org.arch.automate.properties.SchemaProperties;

import java.io.IOException;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 11:07 AM
 */
public interface SchemaReadable {

    /**
     * 获取Schema类型
     *
     * @return
     */
    SchemaType getTyp();

    /**
     * 读取
     *
     * @param schemaProperties
     * @throws IOException
     */
    List<SchemaData> read(SchemaProperties schemaProperties);

}