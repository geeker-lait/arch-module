package org.arch.ums.conf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.MobileInfoRequest;
import org.arch.ums.conf.dto.MobileInfoSearchDto;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.service.MobileInfoService;
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
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_INFO_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_INFO_PARTIAL_FAILED;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static org.arch.framework.utils.SecurityUtils.isAdminForRole;
import static org.arch.ums.uitls.MobileUtils.check;
import static org.arch.ums.uitls.MobileUtils.getBooleanResponse;
import static org.arch.ums.uitls.MobileUtils.getMobileInfo;

/**
 * 手机号归属地信息(MobileInfo) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:52:06
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/mobile/info")
public class MobileInfoController implements CrudController<MobileInfoRequest, MobileInfo, java.lang.Long, MobileInfoSearchDto, MobileInfoService> {

    private final TenantContextHolder tenantContextHolder;
    private final MobileInfoService mobileInfoService;
    private final MobileSegmentService mobileSegmentService;

    @Override
    public MobileInfo resolver(TokenInfo token, MobileInfoRequest request) {
        MobileInfo mobileInfo = new MobileInfo();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, mobileInfo);
        }
        return mobileInfo;
    }

    @Override
    public MobileInfoService getCrudService() {
        return mobileInfoService;
    }

    @Override
    public MobileInfoSearchDto getSearchDto() {
        return new MobileInfoSearchDto();
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
    public Response<MobileInfoSearchDto> findOne(@RequestBody @Valid MobileInfoRequest request, TokenInfo token) {
        try {
            MobileInfo mobileInfo = resolver(token, request);
            MobileInfoSearchDto searchDto = convertSearchDto(mobileInfo);
            MobileInfo result = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<MobileInfoSearchDto>> find(@RequestBody @Valid MobileInfoRequest request, TokenInfo token) {
        MobileInfo mobileInfo = resolver(token, request);
        MobileInfoSearchDto searchDto = convertSearchDto(mobileInfo);
        try {
            List<MobileInfo> mobileInfoList = getCrudService().findAllByMapParams(searchDto.getSearchParams());
            return Response.success(mobileInfoList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<MobileInfoSearchDto>> page(@RequestBody @Valid MobileInfoRequest request,
                                                     @PathVariable(value = "pageNumber") Integer pageNumber,
                                                     @PathVariable(value = "pageSize") Integer pageSize,
                                                     TokenInfo token) {
        MobileInfo mobileInfo = resolver(token, request);
        MobileInfoSearchDto searchDto = convertSearchDto(mobileInfo);
        try {
            IPage<MobileInfo> page = getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param requestList 实体的 request 类型列表
     * @return  {@link Response(Boolean)}
     */
    @PostMapping("/savesOnDuplicateKeyUpdate")
    public Response<Boolean> insertOnDuplicateKeyUpdate(@Valid @RequestBody List<MobileInfoRequest> requestList) {
        try {
            List<MobileInfo> tList = requestList.stream()
                                                .map(request -> resolver(null, request))
                                                .collect(Collectors.toList());
            return Response.success(getCrudService().insertOnDuplicateKeyUpdateBatch(tList));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量上传手机归属地信息.  注意: 必须拥有 ROLE_ADMIN 角色才能上传.<br>
     * 格式: 1999562  甘肃-兰州 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机归属地信息
     * @param delimiter csv 行格式分隔符
     * @param token     当前用户的登录信息.
     * @return {@link Response}
     */
    @PostMapping(value = "/uploadInfos", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Boolean> addMobileInfo(@RequestParam("file") MultipartFile file,
                                           @RequestParam("delimiter") String delimiter,
                                           TokenInfo token) {

        try {
            Response<Boolean> checkFailure = check(delimiter, token);
            if (nonNull(checkFailure)) {
                return checkFailure;
            }
            // 权限校验
            if (isAdminForRole(token)) {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                // 获取手机号段信息
                List<MobileSegment> segmentList = this.mobileSegmentService.findAll();
                if (segmentList == null) {
                    return Response.failed(CommonStatusCode.QUERY_MOBILE_SEGMENT_FAILED);
                }
                if (segmentList.size() == 0) {
                    return Response.failed(CommonStatusCode.MOBILE_SEGMENT_NOT_DATA);
                }
                Map<Integer, MobileSegment> segmentMap =
                        segmentList.stream()
                                   .collect(Collectors.toMap(MobileSegment::getPrefix,
                                                             mobileSegment -> mobileSegment));
                // 存储解析错误的行信息
                List<String> errorList = new ArrayList<>();
                // 解析手机号段信息. 格式: 1999562	甘肃-兰州
                List<MobileInfoRequest> mobileInfoList = getMobileInfo(delimiter, bufferedReader, segmentMap, errorList);

                Response<Boolean> savesResponse = insertOnDuplicateKeyUpdate(mobileInfoList);
                return getBooleanResponse(errorList, savesResponse,
                                          SAVES_MOBILE_INFO_FAILED,
                                          SAVES_MOBILE_INFO_PARTIAL_FAILED);
            }
            return Response.failed(AuthStatusCode.FORBIDDEN);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed(SAVES_MOBILE_INFO_FAILED, e.getMessage());
        }
    }

}
