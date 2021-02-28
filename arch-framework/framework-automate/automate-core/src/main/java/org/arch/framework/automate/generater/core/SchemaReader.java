package org.arch.framework.automate.generater.core;

import lombok.Getter;
import org.arch.framework.automate.generater.reader.ExcelApiSchemaReader;
import org.arch.framework.automate.generater.reader.ExcelMvcSchemaReader;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public enum SchemaReader {

    API(ExcelApiSchemaReader.class.getSimpleName()),
    MVC(ExcelMvcSchemaReader.class.getSimpleName()),
    ;
    @Getter
    private String beanName;
    SchemaReader(String readerName) {
        this.beanName = readerName;
    }
}
