package org.arch.framework.beans.schema.database;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Accessors(chain = true)
public class Property {

    private String name;
    private String value;

}
