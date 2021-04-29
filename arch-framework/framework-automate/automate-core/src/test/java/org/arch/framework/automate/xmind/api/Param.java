package org.arch.framework.automate.xmind.api;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.automate.xmind.table.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Accessors(chain = true)
public class Param {
    private String typ;
    private String name;
    private String descr;
    private final List<Annot> annotations = new ArrayList<>();
    private final List<Property> properties = new ArrayList<>();
}
