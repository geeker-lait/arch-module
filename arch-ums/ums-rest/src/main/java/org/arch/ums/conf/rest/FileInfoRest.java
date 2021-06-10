package org.arch.ums.conf.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.conf.dto.FileInfoRequest;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对象存储文件信息(FileInfo) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:56:22
 * @since 1.0.0
 */

@RestController
@RequestMapping("/conf/file/info")
public interface FileInfoRest extends CrudRest<FileInfoRequest, java.lang.Long, FileInfoSearchDto> {
    /**
     * 根据 filePath 与 uploadType 删除 文件信息
     *
     * @param filePath   文件路径
     * @param uploadType 上传类型
     * @return 删除的文件信息
     */
    @NonNull
    @DeleteMapping(value = "/deleteByFilePathAndUploadType", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    FileInfoSearchDto deleteByFilePathAndUploadType(@RequestParam(value = "filePath") String filePath,
                                                    @RequestParam(value = "uploadType") String uploadType);
}

