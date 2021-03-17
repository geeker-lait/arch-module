package org.arch.ums.conf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.service.MobileSegmentService;
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

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_SEGMENT_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_SEGMENT_PARTIAL_FAILED;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static org.arch.ums.uitls.MobileUtils.check;
import static org.arch.ums.uitls.MobileUtils.getBooleanResponse;
import static org.arch.ums.uitls.MobileUtils.getMobileSegment;
import static org.arch.ums.uitls.MobileUtils.isAdminForRole;

/**
 * 手机号段信息(MobileSegment) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conf/mobile/segment")
public class MobileSegmentController implements CrudController<MobileSegment, java.lang.Long, MobileSegmentSearchDto, MobileSegmentService> {

    private final MobileSegmentService mobileSegmentService;

    @Override
    public MobileSegment resolver(TokenInfo token, MobileSegment mobileSegment) {
        if (isNull(mobileSegment)) {
            mobileSegment = new MobileSegment();
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
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileSegmentList 实体类列表
     * @return  {@link Response(Boolean)}
     */
    @PostMapping("/savesNoResult")
    public Response<Boolean> saveAllNoResult(@Valid @RequestBody List<MobileSegment> mobileSegmentList) {
        try {
            return Response.success(getCrudService().insertOnDuplicateKeyUpdateBatch(mobileSegmentList));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
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
    public Response<MobileSegment> findOne(@RequestBody MobileSegment entity, TokenInfo token) {
        try {
            resolver(token, entity);
            MobileSegmentSearchDto searchDto = convertSearchDto(entity);
            MobileSegment t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<MobileSegment>> find(@RequestBody MobileSegment t, TokenInfo token) {
        resolver(token, t);
        MobileSegmentSearchDto searchDto = convertSearchDto(t);
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
    public Response<IPage<MobileSegment>> page(@RequestBody MobileSegment entity,
                                               @PathVariable(value = "pageNumber") Integer pageNumber,
                                               @PathVariable(value = "pageSize") Integer pageSize,
                                               TokenInfo token) {
        resolver(token, entity);
        MobileSegmentSearchDto searchDto = convertSearchDto(entity);
        try {
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
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
                List<MobileSegment> mobileSegmentList = getMobileSegment(delimiter, bufferedReader, errorList);

                Response<Boolean> savesResponse = saveAllNoResult(mobileSegmentList);
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
