package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.file.FileInfoDto;
import org.arch.auth.sso.file.FileUploader;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.framework.beans.Response;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 图片上下传控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:15
 */
@RestController()
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/file")
public class UploadController {

    private final FileUploader fileUploader;
    private final SsoProperties ssoProperties;

    /**
     * 上传图片
     * @param files:     上传文件
     * @return 返回删除文件的路径集合
     */
    @NonNull
    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<List<String>> uploadImage(@RequestParam("files") MultipartFile[] files) {
        String uploadType = ssoProperties.getUploadType();
        List<String> imageUrlList = new ArrayList<>(files.length);
        try {
            // 待优化
            for (MultipartFile file : files) {
                FileInfoDto upload = fileUploader.upload(file, uploadType, true);
                imageUrlList.add(upload.getFullFilePath());
            }
            return Response.success(imageUrlList);
        }
        catch (Exception e) {
            log.error("上传图片失败", e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 上传头像
     * @param file:     上传文件
     * @return 返回删除文件的路径集合
     */
    @NonNull
    @PostMapping(value = "/image/avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<List<String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String uploadType = ssoProperties.getUploadType();
        try {
            FileInfoDto upload = fileUploader.upload(file, uploadType, true);
            return Response.success(Collections.singletonList(upload.getFullFilePath()));
        }
        catch (Exception e) {
            log.error("上传图片失败", e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
