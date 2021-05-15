package org.arch.ums.feign.user.client;


import org.arch.framework.feign.BaseFeignService;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.arch.ums.user.dto.JobRequest;
import org.arch.ums.user.dto.JobSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * arch-ums-api {@code JobService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-06 15:11:44
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-job", path = "/ums/user/job",
        configuration = DeFaultFeignConfig.class)
public interface UmsUserJobFeignService extends BaseFeignService<JobSearchDto, JobRequest, Long> {

}
