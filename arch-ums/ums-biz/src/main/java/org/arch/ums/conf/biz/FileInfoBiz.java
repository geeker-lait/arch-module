package org.arch.ums.conf.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.FileInfoRequest;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.conf.rest.FileInfoRest;
import org.arch.ums.conf.service.FileInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 对象存储文件信息(FileInfo) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileInfoBiz implements CrudBiz<FileInfoRequest, FileInfo, java.lang.Long, FileInfoSearchDto, FileInfoSearchDto, FileInfoService>, FileInfoRest {

    private final TenantContextHolder tenantContextHolder;
    private final FileInfoService fileInfoService;

    @Override
    public FileInfo resolver(TokenInfo token, FileInfoRequest request) {
        FileInfo fileInfo = new FileInfo();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, fileInfo);
        }
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
     * @param filePath      文件路径
     * @param uploadType    上传类型
     * @return  删除的文件信息
     */
    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public FileInfoSearchDto deleteByFilePathAndUploadType(String filePath, String uploadType) {
        String tenantId = tenantContextHolder.getTenantId();
        LambdaQueryWrapper<FileInfo> queryWrapper =
                Wrappers.<FileInfo>lambdaQuery()
                        .eq(FileInfo::getTenantId, Integer.valueOf(tenantId))
                        .eq(FileInfo::getUploadType, uploadType)
                        .eq(FileInfo::getFilePath, filePath);
        FileInfo fileInfo = fileInfoService.findOneBySpec(queryWrapper);
        if (isNull(fileInfo)) {
            throw new BusinessException("删除失败");
        }
        deleteById(fileInfo.getId());
        fileInfo.setDeleted(Boolean.TRUE);
        return convertReturnDto(fileInfo);
    }

}
