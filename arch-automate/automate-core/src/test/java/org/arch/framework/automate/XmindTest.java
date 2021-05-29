package org.arch.framework.automate;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveException;
import org.arch.framework.beans.schema.database.Database;
import org.arch.automate.Project;
import org.arch.automate.reader.xmind.meta.JsonRootBean;
import org.arch.automate.reader.xmind.parser.XmindParser;
import org.arch.automate.reader.xmind.utils.FreeMarkerUtil;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generate;

/**
 * @ClassName XmindTest
 * @Author http://github.com/geeker-lait
 * @Tel 15801818092
 * @Date 11:09 AM 11/24/2018
 * @Version 1.0.0
 * @Description //TODO
 */
@SpringBootTest
@Slf4j
public class XmindTest {

    @Test
    public void parse() throws DocumentException, ArchiveException, IOException {
        //        String fileName = "minds\\ofs-alarm-er.xmind";
//        String fileName = "minds"+ File.separator +"ofs-alarm-center3.xmind";
        String fileName = "minds"+ File.separator +"ofs-alarm-center.xmind";
//        String fileName = "minds"+ File.separator +"xmind-example.xmind";
        Resource resource = new ClassPathResource(fileName);
        String absolutePath = resource.getFile().getAbsolutePath();

        String res = XmindParser.parseJson(absolutePath);
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(absolutePath, JsonRootBean.class);

        Project project = new Project();

        generate(root, project);

        Database database = project.getModules().stream().findFirst().get().getDatabases().stream().findFirst().get();
        FreeMarkerUtil.geneFile("ddl","templates","ddl.sql", database);
        System.out.println(JSON.toJSONString(project));
    }

}
