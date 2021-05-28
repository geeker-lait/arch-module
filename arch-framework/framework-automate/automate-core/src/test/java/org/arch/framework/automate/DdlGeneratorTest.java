package org.arch.framework.automate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveException;
import org.arch.framework.automate.common.database.Database;
import org.arch.framework.automate.common.module.Module;
import org.arch.framework.automate.generater.reader.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.reader.xmind.parser.XmindParser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/3/2021 4:18 PM
 */
@SpringBootTest
@Slf4j
public class DdlGeneratorTest {

    @Test
     public void genDdl() throws DocumentException, ArchiveException, IOException {
        String fileName = "minds"+ File.separator +"ofs-alarm-center-1.xmind";
        Resource resource = new ClassPathResource(fileName);
        String absolutePath = resource.getFile().getAbsolutePath();

        String res = XmindParser.parseJson(absolutePath);
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(absolutePath, JsonRootBean.class);

        Set<Module> moduleSet = new HashSet<>();
        //generate(root, moduleSet);

        Database database = moduleSet.stream().findFirst().orElse(new Module())
                                     .getDatabases().stream().findFirst().get();
//        FreeMarkerUtil.geneFile("ddl.ftl","templates",)

     }
}
