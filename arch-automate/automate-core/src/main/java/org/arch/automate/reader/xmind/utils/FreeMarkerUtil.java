package org.arch.automate.reader.xmind.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lait.zhang@gmail.com
 * @description: FreeMarker 工具类
 * @weixin PN15855012581
 * @date 5/3/2021 4:17 PM
 */
public class FreeMarkerUtil {

    private final static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    /**
     * 默认模板在/src/main/resources/ 目录下
     * 默认生成的新文件在/target/static 目录下
     * fltFile flt文件名 , templatePath flt文件路径 ,fileNameOrSuffix 后缀名(前缀随机)/全名称 , datas 数据集合
     *
     * @param fltFile
     * @param templatePath
     * @param fileNameOrSuffix
     * @param datas
     * @return 生成文件路径
     */
    public static String geneFile(String fltFile, String templatePath, String fileNameOrSuffix, Object datas) {

        Configuration configuration = new Configuration();
        String result = null;
        try {
            String resourcePath = FreeMarkerUtil.class.getResource("/").toURI().getPath();
            resourcePath = resourcePath.substring(1, resourcePath.indexOf("/target"));
            StringBuffer fltPathBuffer = new StringBuffer(resourcePath);

            if (!templatePath.contains("/src/main/resources/"))//默认路径在 /src/main/resources/ 下,以该路径为跟路径
                fltPathBuffer.append("/src/main/resources/").append(templatePath);
            else
                fltPathBuffer.append("/").append(templatePath);

            logger.info("TEMPLATE HOME PATH : {}", fltPathBuffer);
            configuration.setDirectoryForTemplateLoading(new File(fltPathBuffer.toString()));
            Template temp = configuration.getTemplate(fltFile + ".ftl");

            StringBuffer pathBuffer = new StringBuffer(resourcePath);

            //create file home
            File fileIsExists = new File(pathBuffer.append("/target").append("/static/").toString());
            if (!fileIsExists.exists()) {
                fileIsExists.mkdir();
            }

            //检查元素
            int count = 0;
            Pattern element = Pattern.compile("\\.");
            Matcher subject = element.matcher(fileNameOrSuffix);
            while (subject.find()) {
                count++;
            }

            Random rand = new Random();
            if (count < 1)//断言名称类型
                pathBuffer.append(rand.nextInt(10)).append(".").append(fileNameOrSuffix);//避免文件过多
            else
                pathBuffer.append(fileNameOrSuffix);

            String path = pathBuffer.toString();
            result = path;
            logger.info("CREATE NEW FILE PATH : {}", path);

            Writer file = new FileWriter(new File(result));

            temp.process(datas, file);
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
