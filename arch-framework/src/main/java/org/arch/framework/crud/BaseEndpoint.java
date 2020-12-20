package org.arch.framework.crud;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import java.beans.PropertyEditorSupport;

/**
 * 基础控制器 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 */
public abstract class BaseEndpoint implements WebBindingInitializer {

    private static final Logger log = LoggerFactory.getLogger(BaseEndpoint.class);

    /**
     * 返回的集合或分页为空时的包装，将数据包装成空集合的形式，json格式<br>
     * {"_embedded":{"resources":[]}}
     */
    private final EmbeddedWrappers wrappers = new EmbeddedWrappers(false);

    /**
     * web数据绑定，主要是过滤XSS攻击<br>
     * 该方法只对控制器方法参数为String生效<br>
     * 其他诸如POST、PUT方法的请求体参数无效<br>
     */
    @InitBinder
    @Override
    public void initBinder(WebDataBinder binder) {
        log.debug("进入BaseEndpoint的initBinder方法");
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                log.debug("传进来的String类型的字符串为={}", text);
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });

    }
}
