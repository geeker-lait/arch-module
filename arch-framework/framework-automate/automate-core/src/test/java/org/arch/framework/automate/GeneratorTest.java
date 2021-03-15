package org.arch.framework.automate;

import com.alibaba.fastjson.JSONObject;
import org.arch.framework.automate.generater.core.Generable;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private List<Generable> generables;

    @Test
    public void buildProject() {
        System.out.println(JSONObject.toJSON(generatorConfig));
        generables.forEach(p->{
            try {
                if(generatorConfig.getBuildTool().equalsIgnoreCase(p.getBuildTools().name())) {
                    p.generate(generatorConfig);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
