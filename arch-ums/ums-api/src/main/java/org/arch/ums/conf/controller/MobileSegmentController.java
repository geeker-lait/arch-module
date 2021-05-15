package org.arch.ums.conf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.MobileSegmentRequest;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.service.MobileSegmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_SEGMENT_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_SEGMENT_PARTIAL_FAILED;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static org.arch.framework.utils.SecurityUtils.isAdminForRole;
import static org.arch.ums.uitls.MobileUtils.check;
import static org.arch.ums.uitls.MobileUtils.getBooleanResponse;
import static org.arch.ums.uitls.MobileUtils.getMobileSegment;

/**
 * 手机号段信息(MobileSegment) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 23:01:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/mobile/segment")
public class MobileSegmentController implements CrudController<MobileSegmentRequest, MobileSegment, java.lang.Long, MobileSegmentSearchDto, MobileSegmentService> {

    private final TenantContextHolder tenantContextHolder;
    private final MobileSegmentService mobileSegmentService;

    @Override
    public MobileSegment resolver(TokenInfo token, MobileSegmentRequest request) {
        MobileSegment mobileSegment = new MobileSegment();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, mobileSegment);
        }
        return mobileSegment;
    }

    @Override
    public MobileSegmentService getCrudService() {
        return mobileSegmentService;
    }

    @Override
    public MobileSegmentSearchDto getSearchDto() {
        return new MobileSegmentSearchDto();
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
    public Response<MobileSegmentSearchDto> findOne(@RequestBody @Valid MobileSegmentRequest request, TokenInfo token) {
        try {
            MobileSegment mobileSegment = resolver(token, request);
            MobileSegmentSearchDto searchDto = convertSearchDto(mobileSegment);
            MobileSegment result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<MobileSegmentSearchDto>> find(@RequestBody @Valid MobileSegmentRequest request, TokenInfo token) {
        MobileSegment mobileSegment = resolver(token, request);
        MobileSegmentSearchDto searchDto = convertSearchDto(mobileSegment);
        try {
            List<MobileSegment> mobileSegmentList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(mobileSegmentList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<MobileSegmentSearchDto>> page(@RequestBody @Valid MobileSegmentRequest request,
                                                        @PathVariable(value = "pageNumber") Integer pageNumber,
                                                        @PathVariable(value = "pageSize") Integer pageSize,
                                                        TokenInfo token) {
        MobileSegment mobileSegment = resolver(token, request);
        MobileSegmentSearchDto searchDto = convertSearchDto(mobileSegment);
        try {
            IPage<MobileSegment> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param requestList  实体的 request 类型列表
     * @return  {@link Response(Boolean)}
     */
    @PostMapping("/savesOnDuplicateKeyUpdate")
    public Response<Boolean> insertOnDuplicateKeyUpdate(@Valid @RequestBody List<MobileSegmentRequest> requestList) {
        try {
            List<MobileSegment> tList = requestList.stream()
                                                   .map(request -> resolver(null, request))
                                                   .collect(Collectors.toList());
            return Response.success(mobileSegmentService.insertOnDuplicateKeyUpdateBatch(tList));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量上传手机号段信息. <br>
     * 格式: 1. 格式: 1345   CMCC	0 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机号段信息
     * @param delimiter csv 行格式分隔符
     * @param token     当前用户的登录信息.
     * @return {@link Response}
     */
    @PostMapping(value = "/uploadSegments", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Boolean> addMobileSegment(@RequestParam("file") MultipartFile file,
                                              @RequestParam("delimiter") String delimiter,
                                              TokenInfo token){
        try {
            Response<Boolean> checkFailure = check(delimiter, token);
            if (nonNull(checkFailure)) {
                return checkFailure;
            }
            // 权限校验
            if (isAdminForRole(token)) {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                // 存储解析错误的行信息
                List<String> errorList = new ArrayList<>();
                // 解析手机归属地信息. 格式: 1345   CMCC	0
                List<MobileSegmentRequest> mobileSegmentList = getMobileSegment(delimiter, bufferedReader, errorList);

                Response<Boolean> savesResponse = insertOnDuplicateKeyUpdate(mobileSegmentList);
                return getBooleanResponse(errorList, savesResponse,
                                          SAVES_MOBILE_SEGMENT_FAILED,
                                          SAVES_MOBILE_SEGMENT_PARTIAL_FAILED);
            }
            return Response.failed(AuthStatusCode.FORBIDDEN);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.failed(SAVES_MOBILE_SEGMENT_FAILED, e.getMessage());
        }
    }

}
