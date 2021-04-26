package org.arch.framework.automate.xmind;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:55 AM
 */
@Data
public class Column {
    private String name;
    private String typ;
    private String comment;
    private String length;
    private List<Propertie> properties;

}
