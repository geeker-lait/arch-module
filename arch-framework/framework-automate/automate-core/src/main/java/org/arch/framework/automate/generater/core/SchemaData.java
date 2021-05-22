package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.common.api.Api;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.database.Database;

public interface SchemaData {
    Database getDatabase();
    Api getApi();
    SchemaPattern getSchemaPattern();
}
