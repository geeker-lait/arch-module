package org.arch.framework.beans.utils;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.Locale;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/2/2021 7:51 PM
 */
public class ReloadUtils {

    public static void main(String[] args) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        ReloadableResourceBundleMessageSource auto = new ReloadableResourceBundleMessageSource();
        String[] basenames = {"filename"};
        auto.setBasenames(basenames);
        auto.setResourceLoader(resourceLoader);
        auto.setCacheSeconds(5);
        auto.setUseCodeAsDefaultMessage(true);
        while (true) {
            System.out.println(auto.getMessage("key", null, Locale.CHINESE));
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

