package org.arch.ums.conf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.FileInfoRequest;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.conf.service.FileInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.DUPLICATE_KEY;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 对象存储文件信息(FileInfo) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:42:59
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/file/info")
public class FileInfoController implements CrudController<FileInfoRequest, FileInfo, java.lang.Long, FileInfoSearchDto, FileInfoService> {

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
     * 保存, 处理了唯一索引异常
     * @param request   实体的 request 类型
     * @param token     token info
     * @return  {@link Response}
     */
    @Override
    @PostMapping
    public Response<FileInfoSearchDto> save(@Valid @RequestBody FileInfoRequest request, TokenInfo token) {
        FileInfo fileInfo = resolver(token, request);
        try {
            boolean isSave = getCrudService().save(fileInfo);
            if (isSave) {
                return Response.success(convertSearchDto(fileInfo));
            }
            else {
                return Response.failed(FAILED, "保持失败");
            }
        }
        catch (DuplicateKeyException e) {
            log.error(e.getMessage(),e);
            return Response.failed(DUPLICATE_KEY, request.getFilePath() + " 重名或主键冲突");
        }
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
    Response<FileInfoSearchDto> deleteByFilePathAndUploadType(@RequestParam(value = "filePath") String filePath,
                                                              @RequestParam(value = "uploadType") String uploadType) {

        try {
            Integer tenantId = Integer.valueOf(this.tenantContextHolder.getTenantId());
            FileInfo fileInfo = this.fileInfoService.deleteByFilePathAndUploadType(tenantId, filePath, uploadType);
            if (isNull(fileInfo)) {
                return Response.success(null);
            }
            return Response.success(convertSearchDto(fileInfo));
        }
        catch (Exception e) {
            log.error(String.format("删除文件信息失败: filePath: %s, uploadType: %s", filePath, uploadType), e);
            return Response.error(FAILED.getCode(), "删除文件信息失败");
        }
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<FileInfoSearchDto> findOne(@RequestBody @Valid FileInfoRequest request, TokenInfo token) {
        try {
            FileInfo fileInfo = resolver(token, request);
            FileInfoSearchDto searchDto = convertSearchDto(fileInfo);
            FileInfo result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertSearchDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<FileInfoSearchDto>> find(@RequestBody @Valid FileInfoRequest request, TokenInfo token) {
        FileInfo fileInfo = resolver(token, request);
        FileInfoSearchDto searchDto = convertSearchDto(fileInfo);
        try {
            List<FileInfo> fileInfoList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(fileInfoList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<FileInfoSearchDto>> page(@RequestBody @Valid FileInfoRequest request,
                                                   @PathVariable(value = "pageNumber") Integer pageNumber,
                                                   @PathVariable(value = "pageSize") Integer pageSize,
                                                   TokenInfo token) {
        FileInfo fileInfo = resolver(token, request);
        FileInfoSearchDto searchDto = convertSearchDto(fileInfo);
        try {
            IPage<FileInfo> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
