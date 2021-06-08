package org.arch.ums.conf.biz;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.conf.dao.MobileSegmentDao;
import org.arch.ums.conf.dto.MobileSegmentRequest;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.arch.ums.conf.entity.MobileSegment;
import org.arch.ums.conf.rest.MobileSegmentRest;
import org.arch.ums.conf.service.MobileSegmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.utils.SecurityUtils.isAdminForRole;
import static org.arch.ums.uitls.MobileUtils.check;
import static org.arch.ums.uitls.MobileUtils.getMobileSegment;

/**
 * 手机号段信息(MobileSegment) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:32
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MobileSegmentBiz implements CrudBiz<MobileSegmentRequest, MobileSegment, java.lang.Long, MobileSegmentSearchDto, MobileSegmentSearchDto, MobileSegmentService>, MobileSegmentRest {

    private final MobileSegmentService mobileSegmentService;
    private final MobileSegmentDao mobileSegmentDao;

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
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public MobileSegmentSearchDto findOne(MobileSegmentRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MobileSegment mobileSegment = resolver(token, request);
        MobileSegmentSearchDto searchDto = convertSearchDto(mobileSegment);
        MobileSegment result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<MobileSegmentSearchDto> find(MobileSegmentRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MobileSegment mobileSegment = resolver(token, request);
        MobileSegmentSearchDto searchDto = convertSearchDto(mobileSegment);
        List<MobileSegment> mobileSegmentList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return mobileSegmentList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<MobileSegmentSearchDto> page(MobileSegmentRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        MobileSegment mobileSegment = resolver(token, request);
        MobileSegmentSearchDto searchDto = convertSearchDto(mobileSegment);
        IPage<MobileSegment> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteById(@NonNull Long id) {
        MobileSegment entity = new MobileSegment();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MobileSegment> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                   .set(MobileSegment::getDeleted, 1);
        return mobileSegmentService.updateBySpec(updateWrapper);
    }

    /**
     * 批量逻辑删除
     *
     * @param ids 主键集合
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Boolean deleteAllById(@NonNull List<Long> ids) {

        LambdaUpdateWrapper<MobileSegment> updateWrapper = Wrappers.<MobileSegment>lambdaUpdate()
                                                                   .eq(MobileSegment::getDeleted, 0)
                                                                   .in(MobileSegment::getId, ids)
                                                                   .set(MobileSegment::getDeleted, 1);

        // 逻辑删除
        return mobileSegmentService.updateBySpec(updateWrapper);
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileSegmentList 实体类列表
     * @return  true 表示成功, false 表示失败.
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileSegment> mobileSegmentList) {
        return mobileSegmentDao.insertOnDuplicateKeyUpdateBatch(mobileSegmentList);
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param requestList  实体的 request 类型列表
     * @return  {@link Boolean}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean insertOnDuplicateKeyUpdate(List<MobileSegmentRequest> requestList) {
        List<MobileSegment> tList = requestList.stream()
                                               .map(request -> resolver(null, request))
                                               .collect(Collectors.toList());
        return mobileSegmentDao.insertOnDuplicateKeyUpdateBatch(tList);
    }

    /**
     * 批量上传手机号段信息. <br>
     * 格式: 1. 格式: 1345   CMCC	0 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机号段信息
     * @param delimiter csv 行格式分隔符
     * @return {@link Boolean}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean addMobileSegment(MultipartFile file, String delimiter) throws IOException {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Boolean checkFailure = check(delimiter, token);
        if (!checkFailure) {
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

            return insertOnDuplicateKeyUpdate(mobileSegmentList);
        }
        return false;
    }

}
