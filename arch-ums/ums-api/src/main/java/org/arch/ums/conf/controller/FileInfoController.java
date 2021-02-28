package org.arch.ums.conf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.conf.service.FileInfoService;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 对象存储文件信息(FileInfo) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:07
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/file/info")
public class FileInfoController implements CrudController<FileInfo, java.lang.Long, FileInfoSearchDto, FileInfoService> {

    private final TenantContextHolder tenantContextHolder;
    private final FileInfoService fileInfoService;

    @Override
    public FileInfo resolver(TokenInfo token, FileInfo fileInfo) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            fileInfo.setTenantId(token.getTenantId());
        }
        else {
            fileInfo.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return fileInfo;
    }

    @Override
    public FileInfoService getCrudService() {
        return fileInfoService;
    }

    @Override
    public FileInfoSearchDto getSearchDto() {
        return new FileInfoSearchDto();
    }

    /**
     * 根据 filePath 与 uploadType 删除 文件信息
     *
     * @param filePath   文件路径
     * @param uploadType 上传类型
     * @return 删除的文件信息
     */
    @NonNull
    @DeleteMapping(value = "/deleteByFilePathAndUploadType", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Response<FileInfo> deleteByFilePathAndUploadType(@RequestParam(value = "filePath") String filePath,
                                                     @RequestParam(value = "uploadType") String uploadType) {

        try {
            Integer tenantId = Integer.valueOf(this.tenantContextHolder.getTenantId());
            FileInfo fileInfo = this.fileInfoService.deleteByFilePathAndUploadType(tenantId, filePath, uploadType);
            if (isNull(fileInfo)) {
                return Response.success(null);
            }
            this.fileInfoService.deleteById(fileInfo.getId());
            fileInfo.setDeleted(Boolean.TRUE);
            return Response.success(fileInfo);
        }
        catch (Exception e) {
            log.error(String.format("删除文件信息失败: filePath: %s, uploadType: %s", filePath, uploadType), e);
            return Response.error(FAILED.getCode(), "删除文件信息失败");
        }
    }

}
