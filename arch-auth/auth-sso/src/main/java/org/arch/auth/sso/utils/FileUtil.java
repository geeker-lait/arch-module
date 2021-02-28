package org.arch.auth.sso.utils;

import org.arch.auth.sso.exception.GlobalFileException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:53
 */
public class FileUtil extends org.arch.framework.beans.utils.FileUtil {

    private static final String[] PICTURE_SUFFIXES = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".svg"};

    /**
     * 获取文件名称
     * @param fileName  文件名称/文件全路径/url
     * @return 返回文件名称
     */
    @NonNull
    public static String getPrefix(@NonNull String fileName) {
        int idx = fileName.lastIndexOf(".");
        int xie = fileName.lastIndexOf("/");
        idx = idx == -1 ? fileName.length() : idx;
        xie = xie == -1 ? 0 : xie + 1;
        return fileName.substring(xie, idx);
    }

    /**
     * 根据 fileName 获取文件后缀
     * @param fileName  文件名
     * @return  文件后缀
     */
    @NonNull
    public static String getSuffix(@NonNull String fileName) {
        int index = fileName.lastIndexOf(".");
        index = -1 == index ? fileName.length() : index;
        return fileName.substring(index);
    }

    /**
     * 根据 url 获取图片文件后缀
     * @param imgUrl    图片 url
     * @return  图片后缀
     */
    @NonNull
    public static String getSuffixByUrl(@NonNull String imgUrl) {
        String defaultSuffix = ".png";
        if (!StringUtils.hasText(imgUrl)) {
            return defaultSuffix;
        }
        String fileName = imgUrl;
        //noinspection AlibabaUndefineMagicConstant
        if(imgUrl.contains("/")) {
            fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
        }
        String fileSuffix = getSuffix(fileName);
        return !StringUtils.hasText(fileSuffix) ? defaultSuffix : fileSuffix;
    }

    /**
     * 是否为照片
     * @param suffix    文件后缀
     * @return  true 表示为照片
     */
    public static boolean isPicture(@NonNull String suffix) {
        return StringUtils.hasText(suffix) && Arrays.asList(PICTURE_SUFFIXES).contains(suffix.toLowerCase());
    }

    /**
     * 创建目录
     * @param filePath  目录路径
     */
    public static void mkdirs(@NonNull String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean mkdirs;
            if (file.isDirectory()) {
                mkdirs = file.mkdirs();
            } else {
                mkdirs = file.getParentFile().mkdirs();
            }
            if (!mkdirs) {
                throw new GlobalFileException("创建目录失败！");
            }
        }
    }

    /**
     * 检查文件路径是否有效, 无效则创建目录
     * @param realFilePath  目录路径
     */
    public static void checkFilePath(@NonNull String realFilePath) {
        if (!StringUtils.hasText(realFilePath)) {
            return;
        }
        File parentDir = new File(realFilePath).getParentFile();
        if (!parentDir.exists()) {
            boolean mkdirs = parentDir.mkdirs();
            if (!mkdirs) {
                throw new GlobalFileException("创建目录失败！");
            }
        }
    }

    /**
     * 将 InputStream 转换为字符串
     *
     * @param is InputStream
     * @return  InputStream 字符串
     */
    @NonNull
    public static String toString(@NonNull InputStream is) {
        return toString(is, "UTF-8");
    }

    /**
     * 将 InputStream 转换为字符串
     *
     * @param is InputStream
     * @return  InputStream 字符串
     */
    @NonNull
    public static String toString(@NonNull InputStream is, @Nullable String encoding) {
        encoding = encoding == null ? "UTF-8" : encoding;
        StringBuilder fileContent = new StringBuilder();
        try (
            InputStream inputStream = is;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
                fileContent.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

    /**
     * 克隆 InputStream
     *
     * @param is InputStream
     * @return  ByteArrayInputStream, 支持 reset() 方法, 可重复读.
     */
    @NonNull
    public static InputStream clone(@NonNull InputStream is) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            byte[] buf = baos.toByteArray();
            return new ByteArrayInputStream(buf, 0, buf.length);
        } catch (IOException e) {
            throw new GlobalFileException("无法复制当前文件流！", e);
        }
    }
}
