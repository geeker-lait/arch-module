package org.arch.framework.automate.generater.render;

import lombok.Data;
import org.arch.framework.automate.generater.metadata.EntityInfo;
import org.arch.framework.automate.generater.metadata.ModuleInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 渲染请求
 */
@Data
//@Builder
public class RenderingRequest {
    private String moduleName;
    private String ftlName;
    private String ftlPath;
    private String savePath;
    private String packageName;
    private boolean cover;
    private String className;
    private String author;
    private String date;
    private String comments;
    private List<ModuleInfo> moduleInfos;
    private EntityInfo entity;
    private Set<String> imports;
    private Map<String, RenderingResponse> lastRenderResponse;
    private Map<String, String> otherParams;
}
