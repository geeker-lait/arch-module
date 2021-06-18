package org.arch.project.rule;

import com.ql.util.express.IExpressContext;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: 自定义实现ExpressContext 结合spring
 * @weixin PN15855012581
 * @date 6/18/2021 1:58 PM
 */
public class QLExpresionContext extends HashMap<String, Object> implements IExpressContext<String, Object> {

    private ApplicationContext context;

    public QLExpresionContext(ApplicationContext aContext) {
        this.context = aContext;
    }

    public QLExpresionContext(Map<String, Object> aProperties, ApplicationContext aContext) {
        super(aProperties);
        this.context = aContext;
    }


    /**
     * 根据名称从属性列表中提取属性值
     *
     * @param name
     * @return
     */
    public Object get(Object name) {
        Object result = null;
        result = super.get(name);
        try {
            if (result == null && this.context != null && this.context.containsBean((String) name)) {
                //如果在Spring容器中包含bean，则返回String的Bean
                result = this.context.getBean((String) name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Object put(String name, Object object) {
        if (name.equalsIgnoreCase("myData")) {
            throw new RuntimeException("没有实现");
        }
        return super.put(name, object);
    }


}
