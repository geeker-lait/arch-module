package org.arch.framework.beans.schema.database;

import lombok.Data;
import lombok.experimental.Accessors;
import org.arch.framework.beans.Metadata;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 11:17 AM
 */
@Data
@Accessors(chain = true)
public class Database implements Metadata {
    private String name;
    private String comment;
    private final Set<Table> tables = new HashSet<>();
}
