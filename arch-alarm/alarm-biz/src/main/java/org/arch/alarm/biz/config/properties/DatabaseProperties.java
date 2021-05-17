package org.arch.alarm.biz.config.properties;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 11:00 AM
 */
@Data
public class DatabaseProperties {
    private String name;
    private List<TableProperties> tables;
}
