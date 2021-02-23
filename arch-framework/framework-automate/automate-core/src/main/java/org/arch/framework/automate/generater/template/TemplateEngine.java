package org.arch.framework.automate.generater.template;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/20/2021 4:01 PM
 */
public interface TemplateEngine {
    TemplateEngine init(TemplateConfig var1);

    Template getTemplate(String var1);
}
