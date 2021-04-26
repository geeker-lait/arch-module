package org.arch.framework.automate;

import org.apache.commons.compress.archivers.ArchiveException;
import org.arch.framework.automate.generater.service.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.service.xmind.parser.XmindParser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @ClassName XmindTest
 * @Author http://github.com/geeker-lait
 * @Tel 15801818092
 * @Date 11:09 AM 11/24/2018
 * @Version 1.0.0
 * @Description //TODO
 */
@SpringBootTest
public class XmindTest {

    @Test
    public void parse() throws DocumentException, ArchiveException, IOException {
        String fileName = "minds\\ofs-alarm-er.xmind";
        //String fileName = "minds\\Xmind8.xmind";
        Resource resource = new ClassPathResource(fileName);

        String res = XmindParser.parseJson(resource.getFile().getAbsolutePath());
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(resource.getFile().getAbsolutePath(), JsonRootBean.class);
//        System.out.println(root);


    }


}
