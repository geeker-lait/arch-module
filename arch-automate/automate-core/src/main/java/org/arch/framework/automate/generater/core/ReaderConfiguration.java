package org.arch.framework.automate.generater.core;

import lombok.Data;
import org.arch.automate.common.configuration.SchemaConfiguration;
import org.arch.automate.common.enums.SchemaPattern;
import org.arch.automate.common.Project;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class ReaderConfiguration<T extends SchemaConfiguration> {
    private String resource;
    private T configuration;
    private SchemaPattern pattern;
    /**
     * 用于缓存解析 resource 后的 Project
     */
    private ConcurrentHashMap<String, Project> resourceProjectMap;
}
