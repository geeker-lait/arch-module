package org.arch.automate.test;

import com.alibaba.fastjson.JSONObject;
import org.arch.automate.config.GeneratorConfig;
import org.arch.automate.core.Generable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName MainTest
 * @Author http://github.com/geeker-lait
 * @Tel 15801818092
 * @Date 11:09 AM 11/24/2018
 * @Version 1.0.0
 * @Description //TODO
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
//@ContextConfiguration
@SpringBootTest
public class GeneratorTest {
    @Autowired
    private GeneratorConfig generatorConfig;
    @Autowired
    private List<Generable> generables;

    @Test
    public void buildProject() {
        System.out.println(JSONObject.toJSON(generatorConfig));
        generables.forEach(p -> {
            try {
                if (generatorConfig.getBuildTool().equalsIgnoreCase(p.getBuildTools().name())) {
                    p.generate(generatorConfig);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
