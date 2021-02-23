package org.arch.framework.automate.generater.builder;

import org.arch.framework.automate.generater.core.AbstractGenerator;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/23/2021 9:17 PM
 */
public abstract class AbstractBuilder {
    /**
     * 获取包命名
     * @param filePath
     * @return
     */
    public String buildPkg(Path filePath) {
        String p = filePath.toString();
        int l = p.indexOf(AbstractGenerator.MAIN_JAVA);
        int ll = p.lastIndexOf(File.separator);
        String pkg = p.substring(l + AbstractGenerator.MAIN_JAVA.length(), ll).replaceAll(Matcher.quoteReplacement(File.separator), "\\.");
        return pkg;
    }
}
