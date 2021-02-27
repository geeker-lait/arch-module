package org.arch.framework.automate.generater.reader;

import org.arch.framework.automate.from.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/26/2021 2:27 PM
 */

public abstract class AbstractSchemaReader {
    @Autowired
    protected DatabaseService databaseService;
}
