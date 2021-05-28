package org.arch.auth.sso.utils;

import org.arch.auth.sso.exception.GlobalFileException;
import org.arch.auth.sso.file.FileInfoDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:59
 */
public class ImageUtil {
    /**
     * 获取图片信息
     *
     * @param file 图片文件
     * @throws GlobalFileException 获取图片信息失败
     */
    @NonNull
    public static FileInfoDto getInfo(@Nullable File file) {
        if (null == file) {
            return new FileInfoDto();
        }
        try {
            return getInfo(new FileInputStream(file))
                    .setSize(file.length())
                    .setOriginalFileName(file.getName())
                    .setSuffix(FileUtil.getSuffix(file.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalFileException("获取图片信息发生异常！", e);
        }
    }

    /**
     * 获取图片信息
     *
     * @param multipartFile multipart file
     * @throws GlobalFileException 获取图片信息失败
     */
    @NonNull
    public static FileInfoDto getInfo(@Nullable MultipartFile multipartFile) {
        if (null == multipartFile) {
            return new FileInfoDto();
        }
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            long size = multipartFile.getSize();
            if (!hasText(originalFilename)) {
                throw new GlobalFileException("获取图片信息失败！");
            }
            return getInfo(multipartFile.getInputStream())
                    .setSize(size)
                    .setOriginalFileName(originalFilename)
                    .setSuffix(FileUtil.getSuffix(originalFilename));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalFileException("获取图片信息发生异常！", e);
        }
    }

    /**
     * 获取图片信息
     *
     * @param inputStream 图片流
     * @throws GlobalFileException 获取图片信息失败
     */
    @NonNull
    public static FileInfoDto getInfo(@NonNull InputStream inputStream) {
        try (BufferedInputStream in = new BufferedInputStream(inputStream)) {
            int size = inputStream.available();
            //字节流转图片对象
            Image bi = ImageIO.read(in);
            if (null == bi) {
                return new FileInfoDto();
            }
            //获取默认图像的高度，宽度
            return new FileInfoDto()
                    .setWidth(bi.getWidth(null))
                    .setHeight(bi.getHeight(null))
                    .setSize(size);
        } catch (Exception e) {
            throw new GlobalFileException("获取图片信息发生异常！", e);
        }
    }
}
