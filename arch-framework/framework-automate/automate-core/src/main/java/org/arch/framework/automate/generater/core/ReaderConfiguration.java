package org.arch.framework.automate.generater.core;

import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaConfiguration;
import org.arch.framework.automate.generater.core.SchemaPattern;

@Data
public class ReaderConfiguration<T extends SchemaConfiguration> {
    private String resource;
    private T configuration;
    private SchemaPattern pattern;
}
