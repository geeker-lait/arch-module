package org.arch.framework.automate;

import com.alibaba.fastjson.JSONObject;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.builder.TemplateProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName MainTest
 * @Author http://github.com/geeker-lait
 * @Tel 15801818092
 * @Date 11:09 AM 11/24/2018
 * @Version 1.0.0
 * @Description //TODO
 */
@SpringBootTest
public class GeneratorTest {
    @Autowired
    private GeneratorConfig generatorConfig;

    @Autowired
    private List<TemplateProcessor> templateProcessors;

    @Test
    public void buildProject() {
        System.out.println(JSONObject.toJSON(generatorConfig));
        templateProcessors.forEach(p->{
            try {
                p.build(generatorConfig);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
