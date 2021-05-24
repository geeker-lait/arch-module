package org.arch.alarm.biz.config.properties;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 11:02 AM
 */
@Data
public class ColumnProperties {
    private String name;
    private String type;
    private String descr;
    private Boolean ignore = false;
}
