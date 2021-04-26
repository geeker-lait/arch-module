package org.arch.framework.automate.xmind;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 11:17 AM
 */
@Data
public class Database {
    private String name;
    private List<Table> tables;
}
