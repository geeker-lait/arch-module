package org.arch.ums.conf.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
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

import java.util.List;
import java.util.stream.Collectors;

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
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public FileInfoSearchDto findOne(FileInfoRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        FileInfo fileInfo = resolver(token, request);
        FileInfoSearchDto searchDto = convertSearchDto(fileInfo);
        FileInfo result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<FileInfoSearchDto> find(FileInfoRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        FileInfo fileInfo = resolver(token, request);
        FileInfoSearchDto searchDto = convertSearchDto(fileInfo);
        List<FileInfo> fileInfoList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return fileInfoList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<FileInfoSearchDto> page(FileInfoRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        FileInfo fileInfo = resolver(token, request);
        FileInfoSearchDto searchDto = convertSearchDto(fileInfo);
        IPage<FileInfo> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
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
