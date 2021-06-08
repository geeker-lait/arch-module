package org.arch.admin.conf.contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.api.ConfMobileInfoApi;
import org.arch.ums.conf.api.ConfMobileSegmentApi;
import org.arch.ums.conf.dto.MobileInfoRequest;
import org.arch.ums.conf.dto.MobileSegmentRequest;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.arch.ums.conf.entity.MobileSegment;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_INFO_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_INFO_PARTIAL_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_SEGMENT_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.SAVES_MOBILE_SEGMENT_PARTIAL_FAILED;
import static org.arch.framework.utils.SecurityUtils.isAdminForRole;
import static org.arch.ums.uitls.MobileUtils.check;
import static org.arch.ums.uitls.MobileUtils.getBooleanResponse;
import static org.arch.ums.uitls.MobileUtils.getMobileInfo;
import static org.arch.ums.uitls.MobileUtils.getMobileSegment;

/**
 * 更新与增量添加手机号段信息, 手机归属地信息.<br>
 * 注意: 如果初始化, 这请调用 ums-api 的 /ums/conf/mobile/info/uploadInfos 与  /ums/conf/mobile/Segment/uploadSegments 接口.
 * 因为如果上传的数据过大, Feign 客户端会因超时报错(但逻辑上是执行完成).
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.15 21:28
 */
@RestController("adminMobileInfoController")
@RequestMapping("/conf")
@Slf4j
@RequiredArgsConstructor
public class MobileInfoController {

    private final ConfMobileInfoApi confMobileInfoApi;
    private final ConfMobileSegmentApi confMobileSegmentApi;

    /**
     * 批量上传手机归属地信息, 批量保存, 如果主键或唯一索引重复则更新. 注意: 必须拥有 ROLE_ADMIN 角色才能上传.<br>
     * 主要用户批量更新与添加, 如果初始化时请直接调用 ums-api 的 /ums/conf/mobile/info/uploadInfos.<br>
     * 格式: 1999562  甘肃-兰州 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机归属地信息
     * @param delimiter csv 行格式分隔符
     * @param token     当前用户的登录信息.
     * @return {@link Response}
     */
    @PostMapping(value = "/add/mobileInfo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Boolean> addMobileInfo(@RequestParam("file") MultipartFile file,
                                           @RequestParam("delimiter") String delimiter,
                                           TokenInfo token) {
        try {
            Boolean checkFailure = check(delimiter, token);
            if (!checkFailure) {
                return Response.success(checkFailure);
            }
            // 权限校验
            if (isAdminForRole(token)) {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                // 获取手机号段信息
                Response<List<MobileSegmentSearchDto>> response = this.confMobileSegmentApi.list();
                List<MobileSegmentSearchDto> segmentSearchDtoList = response.getSuccessData();
                if (segmentSearchDtoList == null) {
                    return Response.failed(CommonStatusCode.QUERY_MOBILE_SEGMENT_FAILED);
                }
                if (segmentSearchDtoList.size() == 0) {
                    return Response.failed(CommonStatusCode.MOBILE_SEGMENT_NOT_DATA);
                }
                Map<Integer, MobileSegment> segmentMap =
                        segmentSearchDtoList.stream()
                                .map(dto -> {
                                    MobileSegment mobileSegment = new MobileSegment();
                                    BeanUtils.copyProperties(dto, mobileSegment);
                                    return mobileSegment;
                                })
                                .collect(Collectors.toMap(MobileSegment::getPrefix,
                                        mobileSegment -> mobileSegment));
                // 存储解析错误的行信息
                List<String> errorList = new ArrayList<>();
                // 解析手机号段信息. 格式: 1999562	甘肃-兰州
                List<MobileInfoRequest> mobileInfoList = getMobileInfo(delimiter, bufferedReader, segmentMap, errorList);

                Response<Boolean> savesResponse = this.confMobileInfoApi.insertOnDuplicateKeyUpdate(mobileInfoList);
                return getBooleanResponse(errorList, savesResponse,
                        SAVES_MOBILE_INFO_FAILED,
                        SAVES_MOBILE_INFO_PARTIAL_FAILED);
            }
            return Response.failed(AuthStatusCode.FORBIDDEN);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed(SAVES_MOBILE_INFO_FAILED, e.getMessage());
        }
    }

    /**
     * 批量上传手机号段信息, 批量保存, 如果主键或唯一索引重复则更新.  注意: 必须拥有 ROLE_ADMIN 角色才能上传.<br>
     * 主要用户批量更新与添加, 如果初始化时请直接调用 ums-api 的 /ums/conf/mobile/Segment/uploadSegment.
     * <br>
     * 格式: 1. 格式: 1345   CMCC	0 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机号段信息
     * @param delimiter csv 行格式分隔符
     * @param token     当前用户的登录信息.
     * @return {@link Response}
     */
    @PostMapping(value = "/add/mobileSegment", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Boolean> addMobileSegment(@RequestParam("file") MultipartFile file,
                                              @RequestParam("delimiter") String delimiter,
                                              TokenInfo token) {
        try {
            Boolean checkFailure = check(delimiter, token);
            if (!checkFailure) {
                return Response.success(checkFailure);
            }
            // 权限校验
            if (isAdminForRole(token)) {
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                // 存储解析错误的行信息
                List<String> errorList = new ArrayList<>();
                // 解析手机归属地信息. 格式: 1345   CMCC	0
                List<MobileSegmentRequest> mobileSegmentList = getMobileSegment(delimiter, bufferedReader, errorList);

                Response<Boolean> savesResponse =
                        this.confMobileSegmentApi.insertOnDuplicateKeyUpdate(mobileSegmentList);
                return getBooleanResponse(errorList, savesResponse,
                        SAVES_MOBILE_SEGMENT_FAILED,
                        SAVES_MOBILE_SEGMENT_PARTIAL_FAILED);
            }
            return Response.failed(AuthStatusCode.FORBIDDEN);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed(SAVES_MOBILE_SEGMENT_FAILED, e.getMessage());
        }
    }

}

