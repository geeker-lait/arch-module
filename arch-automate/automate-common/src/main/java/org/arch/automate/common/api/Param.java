package org.arch.automate.common.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * column/field/param
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
    private final Set<Annot> annotations = new HashSet<>();
}
