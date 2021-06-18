package org.arch.auth.sso.file.image;

import org.arch.auth.sso.file.FileInfoDto;
import org.arch.auth.sso.properties.FileProperties;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

import static java.util.Objects.isNull;

/**
 * {@link ImageClient} 适配器.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 14:03
 */
public class ImageClientAdapter implements ImageClient {

    private final ImageClient imageClient;

    public ImageClientAdapter(FileProperties fileProperties) {
        this(fileProperties, null);
    }

    public ImageClientAdapter(FileProperties fileProperties, @Nullable ImageClient imageClient) {
        if (isNull(imageClient)) {
            LocalImageClient localFileClient = new LocalImageClient();
            this.imageClient = localFileClient.init(fileProperties.getUrl(), fileProperties.getRootPath(),
                    fileProperties.getUploadType(), fileProperties.getImageMaxSize());
            return;
        }
        this.imageClient = imageClient;
    }


    @Override
    @NonNull
    public FileInfoDto uploadImg(@NonNull MultipartFile file) {
        return this.imageClient.uploadImg(file);
    }

    @Override
    @NonNull
    public FileInfoDto uploadImg(@NonNull File file) {
        return this.imageClient.uploadImg(file);
    }

    @Override
    @NonNull
    public FileInfoDto uploadImg(@NonNull InputStream is, @NonNull String pathOrUrl) {
        return this.imageClient.uploadImg(is, pathOrUrl);
    }

    @Override
    public boolean removeFile(@NonNull String pathOrUrl) {
        return this.imageClient.removeFile(pathOrUrl);
    }

    @Override
    public Integer getStorageType() {
        return this.imageClient.getStorageType();
    }
}
