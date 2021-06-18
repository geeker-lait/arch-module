package org.arch.automate.core;

import lombok.Data;
import org.arch.automate.configuration.SchemaConfiguration;
import org.arch.automate.enums.SchemaPattern;
import org.arch.automate.Project;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class ReaderConfiguration<T extends SchemaConfiguration> {
    private String resource;
    private T configuration;
    private SchemaPattern pattern;
    /**
     * 用于缓存解析 resource 后的 Project, Map(resource, Project)
     */
    private ConcurrentHashMap<String, Project> resourceProjectMap;
}
