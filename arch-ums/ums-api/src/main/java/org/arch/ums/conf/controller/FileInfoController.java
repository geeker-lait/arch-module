package org.arch.ums.conf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.conf.service.FileInfoService;
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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.DUPLICATE_KEY;
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
        if (isNull(fileInfo)) {
            fileInfo =  new FileInfo();
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
     * @param fileInfo     实体类
     * @param token token info
     * @return  {@link Response}
     */
    @Override
    @PostMapping
    public Response<FileInfo> save(@Valid @RequestBody FileInfo fileInfo, TokenInfo token) {
        resolver(token, fileInfo);
        try {
            getCrudService().save(fileInfo);
        }
        catch (DuplicateKeyException e) {
            log.error(e.getMessage(),e);
            return Response.failed(DUPLICATE_KEY, fileInfo.getFilePath() + " 重名或主键冲突");
        }
        return Response.success(fileInfo);
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
            return Response.success(fileInfo);
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
     * @param entity 实体类
     * @param token  token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<FileInfo> findOne(@RequestBody FileInfo entity, TokenInfo token) {
        try {
            resolver(token, entity);
            FileInfoSearchDto searchDto = convertSearchDto(entity);
            FileInfo t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
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
     * @param t     实体类
     * @param token token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<FileInfo>> find(@RequestBody FileInfo t, TokenInfo token) {
        resolver(token, t);
        FileInfoSearchDto searchDto = convertSearchDto(t);
        try {
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
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
     * @param entity     实体类
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<FileInfo>> page(@RequestBody FileInfo entity,
                                          @PathVariable(value = "pageNumber") Integer pageNumber,
                                          @PathVariable(value = "pageSize") Integer pageSize,
                                          TokenInfo token) {
        resolver(token, entity);
        FileInfoSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
