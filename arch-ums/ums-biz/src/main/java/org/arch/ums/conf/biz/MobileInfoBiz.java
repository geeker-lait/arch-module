package org.arch.ums.conf.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.conf.dao.MobileInfoDao;
import org.arch.ums.conf.dto.MobileInfoRequest;
import org.arch.ums.conf.dto.MobileInfoSearchDto;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.rest.MobileInfoRest;
import org.arch.ums.conf.service.MobileInfoService;
import org.arch.ums.conf.service.MobileSegmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.utils.SecurityUtils.isAdminForRole;
import static org.arch.ums.uitls.MobileUtils.check;
import static org.arch.ums.uitls.MobileUtils.getMobileInfo;

/**
 * 手机号归属地信息(MobileInfo) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:32
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MobileInfoBiz implements CrudBiz<MobileInfoRequest, MobileInfo, java.lang.Long, MobileInfoSearchDto, MobileInfoSearchDto, MobileInfoService>, MobileInfoRest {

    private final TenantContextHolder tenantContextHolder;
    private final MobileInfoService mobileInfoService;
    private final MobileSegmentService mobileSegmentService;
    private final MobileInfoDao mobileInfoDao;

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public MobileInfoSearchDto findOne(MobileInfoRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MobileInfo mobileInfo = resolver(token, request);
        MobileInfoSearchDto searchDto = convertSearchDto(mobileInfo);
        MobileInfo result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<MobileInfoSearchDto> find(MobileInfoRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MobileInfo mobileInfo = resolver(token, request);
        MobileInfoSearchDto searchDto = convertSearchDto(mobileInfo);
        List<MobileInfo> mobileInfoList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return mobileInfoList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<MobileInfoSearchDto> page(MobileInfoRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MobileInfo mobileInfo = resolver(token, request);
        MobileInfoSearchDto searchDto = convertSearchDto(mobileInfo);
        IPage<MobileInfo> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param requestList 实体的 request 类型列表
     * @return  {@link Boolean}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean insertOnDuplicateKeyUpdate(List<MobileInfoRequest> requestList) throws SQLException {
        List<MobileInfo> tList = requestList.stream()
                                            .map(request -> resolver(null, request))
                                            .collect(Collectors.toList());
        return insertOnDuplicateKeyUpdateBatch(tList);
    }

    /**
     * 批量上传手机归属地信息.  注意: 必须拥有 ROLE_ADMIN 角色才能上传.<br>
     * 格式: 1999562  甘肃-兰州 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机归属地信息
     * @param delimiter csv 行格式分隔符
     * @return {@link Boolean}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Object addMobileInfo(MultipartFile file, String delimiter) throws SQLException, IOException {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Boolean checkFailure = check(delimiter, token);
        if (!checkFailure) {
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

            return insertOnDuplicateKeyUpdate(mobileInfoList);
        }
        return false;
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileInfoList 实体类列表
     * @return  true 表示成功, false 表示失败.
     * @throws SQLException 批量保持失败
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileInfo> mobileInfoList) throws SQLException {
        return mobileInfoDao.insertOnDuplicateKeyUpdateBatch(mobileInfoList);
    }

}
