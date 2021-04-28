package org.arch.framework.automate.xmind.table;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:55 AM
 */
@Data
@Accessors(chain = true)
public class Column {
    private String name;
    private String typ;
    private String comment;
    private final List<Property> properties = new ArrayList<>();

    public boolean isId() {
        return "id".equals(name);
    }

}
