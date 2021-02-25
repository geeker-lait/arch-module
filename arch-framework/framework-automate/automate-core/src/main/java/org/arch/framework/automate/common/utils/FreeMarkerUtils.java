package org.arch.framework.automate.common.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.builder.bak.TemplateProcessor;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.arch.framework.automate.generater.render.RenderingResponse;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * FreeMarker模板工具帮助类
 */
@Slf4j
@UtilityClass
public class FreeMarkerUtils {

    private static Configuration configuration;

    private static List<TemplateProcessor> templateProcessors;

    static {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setObjectWrapper(Configuration.getDefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
        configuration.setDefaultEncoding("UTF-8");
        templateProcessors = new ArrayList<>();
    }


    public static List<TemplateProcessor> addFtlProcessor(TemplateProcessor templateProcessor) {
        templateProcessors.add(templateProcessor);
        return templateProcessors;
    }

    /**
     * 获取解析后的字符串
     *
     * @param renderingRequest 模板参数
     * @return 渲染结果
     */
    public static String render(RenderingRequest renderingRequest) {
        try (Writer writer = new StringWriter()) {
            configuration.setDirectoryForTemplateLoading(new File(renderingRequest.getFtlPath()));
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setObjectWrapper(new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
            configuration.setWhitespaceStripping(true);
            Template template = configuration.getTemplate(renderingRequest.getFtlName());
            template.process(renderingRequest, writer);
            return writer.toString();
        } catch (TemplateException | IOException e) {
            throw new CodegenException(
                    String.format("render %s code source error.", renderingRequest.getEntity().getClassName()), e);
        }
    }

    /**
     * 获取解析后的值.
     *
     * @param renderingRequest 模板参数
     * @return 渲染结果
     */
    public static RenderingResponse process(RenderingRequest renderingRequest) {
        RenderingResponse response = new RenderingResponse();
        response.setClassName(renderingRequest.getClassName());
        response.setPackageName(renderingRequest.getPackageName());
        response.setFtlName(renderingRequest.getFtlName());
        // 渲染模板文件
        String code = render(renderingRequest);
        if (code != null) {
            // 处理模板文件
            templateProcessors.forEach(ftlProcessor -> {
                if (ftlProcessor.getTemplate().getTemplate().equals(renderingRequest.getFtlName())) {
                    ftlProcessor.process(code, renderingRequest);
                }
            });
        }
        response.setSuccess(true);
        return response;
    }


}
