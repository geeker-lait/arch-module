package org.arch.framework.automate.generater.service.xmind.database;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 11:17 AM
 */
@Data
@Accessors(chain = true)
public class Database {
    private String name;
    private String comment;
    private final List<Table> tables = new ArrayList<>();
}
