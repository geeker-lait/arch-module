package org.arch.ums.conf.api;

import org.arch.framework.beans.Response;
import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.conf.dto.MobileSegmentRequest;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * arch-conf-api {@code MobileSegmentService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:49:21
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-confMobileSegment", path = "/ums/conf/mobile/segment",
        configuration = DeFaultFeignConfig.class)
public interface ConfMobileSegmentApi extends FeignApi<MobileSegmentSearchDto, MobileSegmentRequest, Long> {
    /**
     * 批量上传手机号段信息, 批量保存, 如果主键或唯一索引重复则更新. <br>
     * 主要用户批量更新与添加, 如果初始化时请直接调用 ums-rest 的 /ums/conf/mobile/Segment/uploadSegment.
     * <br>
     * 格式: 1. 格式: 1345   CMCC	0 <br>
     * 分隔符可以自定义.
     *
     * @param mobileSegmentList 实体类列表
     * @return {@link Response (Boolean)}
     */
    @PostMapping("/savesOnDuplicateKeyUpdate")
    Response<Boolean> insertOnDuplicateKeyUpdate(@Valid @RequestBody List<MobileSegmentRequest> mobileSegmentList);
}