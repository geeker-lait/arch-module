package org.arch.framework.automate.generater.render;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.arch.framework.automate.common.metadata.EntityInfo;
import org.arch.framework.automate.common.metadata.FieldInfo;
import org.arch.framework.automate.common.utils.FreeMarkerUtils;
import org.arch.framework.automate.generater.config.CodeGeneratorConfig;
import org.arch.framework.automate.generater.core.GeneratorConstants;
import org.arch.framework.automate.generater.config.ModuleConfig;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 默认的模板引擎渲染实现类
 */
@Slf4j
public class DefaultRender implements Rendable {

    /**
     * 代码生成的配置
     */
    private final CodeGeneratorConfig config;

    private Map<String, RenderingResponse> lastRenderingResponseMap = new HashMap<>();

    public DefaultRender(CodeGeneratorConfig config) {
        this.config = config;
    }

    /**
     * 渲染对应的模板
     */
    @Override
    public final RenderingResponse render(EntityInfo entityInfo, String module, String savePath) {
        RenderingRequest renderingRequest = new RenderingRequest();

        renderingRequest.setLastRenderResponse(lastRenderingResponseMap);

        ModuleConfig moduleConfig = config.getModuleConfigMap().get(module);
        // 设置类名,为实体名称+模板配置的对应后缀
        renderingRequest.setClassName(entityInfo.getClassName() + moduleConfig.getClassNameSuffix());
        if (Objects.equals(GeneratorConstants.ENTITY_MODULE, module)) {
            entityInfo.setClassName(renderingRequest.getClassName());
        }
        String packageName = moduleConfig.getPackageName();
        if (packageName.contains("*")) {
            packageName = packageName.replace("*", entityInfo.getModuleName());
        }
        renderingRequest.setPackageName(packageName);
        savePath = savePath.endsWith(Rendable.SLASH) ? savePath : savePath + Rendable.SLASH;
        renderingRequest.setSavePath(savePath + packageName.replace(".", Rendable.SLASH) + Rendable.SLASH);
        renderingRequest.setFtlName(moduleConfig.getFtlName());

        renderingRequest.setFtlPath(config.getFtlPath());
        renderingRequest.setCover(config.isCover());

        renderingRequest.setEntity(entityInfo);

        renderingRequest.setAuthor(config.getAuthor());
        renderingRequest.setComments(config.getComments());
        renderingRequest.setDate(config.getDate());

        // fields ，只支持基本类型映射
        renderingRequest.setOtherParams(config.getOtherParams());

        // check for other imports
        renderingRequest.setImports(checkImports(entityInfo));

        // use freemarker to render code.
        RenderingResponse lastRenderingResponse = FreeMarkerUtils.process(renderingRequest);

        lastRenderingResponseMap.put(module, lastRenderingResponse);
        log.info("render module is {}, response is {}", module, lastRenderingResponse);
        log.info("lastRenderingResponseMap is {}", lastRenderingResponseMap);
        return lastRenderingResponse;
    }

    /**
     * 过滤不需要模板导入的包
     *
     * @param entityInfo
     * @return
     */
    private Set<String> checkImports(EntityInfo entityInfo) {
        Set<String> imports = new HashSet<>();
        List<FieldInfo> fielList = entityInfo.getFields();

        if (!CollectionUtils.isEmpty(fielList)) {
            fielList.forEach(f -> {
                String packageName = f.getPackageName();
                if (Strings.isNotBlank(packageName) && !packageName.contains("java.lang")) {
                    imports.add(packageName);
                }
            });
        }
        return imports;
    }

}
