package org.arch.auth.sso.file.image;

import org.arch.auth.sso.exception.LocalUploadFileException;
import org.arch.auth.sso.file.FileInfoDto;
import org.arch.auth.sso.utils.FileUtil;
import org.arch.auth.sso.utils.ImageUtil;
import org.arch.framework.ums.enums.StorageType;
import org.springframework.lang.NonNull;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.hasText;

/**
 * 本地图片操作客户端
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 12:24
 */
public class LocalImageClient extends BaseImageClient {

    private static final String DEFAULT_PREFIX = "image/";

    private String url;
    private String rootPath;
    private String pathPrefix;
    private Integer imageMaxSize;

    public LocalImageClient() {
        super(StorageType.LOCAL.ordinal());
    }

    public LocalImageClient init(@NonNull String url, @NonNull String rootPath,
                                 @NonNull String uploadType, @NonNull Integer imageMaxSize) {
        this.url = url;
        if (!hasText(url)) {
            this.url = "";
        }
        this.rootPath = rootPath;
        this.pathPrefix = !hasText(uploadType) ? DEFAULT_PREFIX : uploadType.endsWith("/") ? uploadType : uploadType + "/";
        this.imageMaxSize = imageMaxSize;
        return this;
    }

    @Override
    @NonNull
    public FileInfoDto uploadImg(@NonNull InputStream is, @NonNull String imageUrl) {
        this.check();

        String oriFileName = FileUtil.getName(imageUrl);
        this.createNewFileName(oriFileName, this.pathPrefix);
        LocalDateTime startTime = LocalDateTime.now();

        String realFilePath = this.url + this.rootPath + this.newFileName;
        FileUtil.checkFilePath(realFilePath);
        try (ByteArrayInputStream uploadIs = (ByteArrayInputStream) FileUtil.clone(is);
             FileOutputStream fos = new FileOutputStream(realFilePath)) {

            if (uploadIs.available() > this.imageMaxSize) {
                throw new LocalUploadFileException("图片上传最大值不能超过：" + (this.imageMaxSize / 1024 / 1024) + " Mb");
            }

            String fileHash = DigestUtils.md5DigestAsHex(uploadIs);
            uploadIs.reset();

            FileCopyUtils.copy(uploadIs, fos);
            uploadIs.reset();

            FileInfoDto imageInfo = ImageUtil.getInfo(uploadIs);
            return new FileInfoDto()
                    .setOriginalFileName(oriFileName)
                    .setSuffix(this.suffix)
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(LocalDateTime.now())
                    .setFilePath(this.newFileName)
                    .setFileHash(fileHash)
                    .setFullFilePath(this.url + this.rootPath + this.newFileName)
                    .setSize(imageInfo.getSize())
                    .setWidth(imageInfo.getWidth())
                    .setHeight(imageInfo.getHeight());
        } catch (Exception e) {
            throw new LocalUploadFileException("存储类型[" + this.storageType + "]文件上传失败：" + e.getMessage() + imageUrl);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean removeFile(@NonNull String pathOrUrl) {
        this.check();
        if (!hasText(pathOrUrl)) {
            throw new LocalUploadFileException("存储类型[" + this.storageType + "]删除文件失败：文件key为空");
        }
        File file = new File(this.url + this.rootPath + pathOrUrl);
        if (!file.exists()) {
            throw new LocalUploadFileException("存储类型[" + this.storageType + "]删除文件失败：文件不存在[" + this.rootPath + pathOrUrl + "]");
        }
        try {
            return file.delete();
        } catch (Exception e) {
            throw new LocalUploadFileException("存储类型[" + this.storageType + "]删除文件失败：" + e.getMessage());
        }
    }

    @Override
    public void check() {
        if (!hasText(url) || !hasText(rootPath)) {
            throw new LocalUploadFileException("存储类型[" + this.storageType + "]尚未配置文件服务器，文件上传功能暂时不可用！");
        }
    }
}
