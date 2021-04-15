package org.arch.ums.feign.conf.client;


import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.conf.entity.MobileInfo;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * arch-conf-api {@code ConfMobileInfoService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:51:27
 * @since 1.0.0
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-confMobileInfo", path = "/ums/conf/mobile/info",
        configuration = DeFaultFeignConfig.class)
public interface ConfMobileInfoFeignService extends BaseFeignService<MobileInfo, java.lang.Long> {

    /**
     * 批量上传手机归属地信息, 批量保存, 如果主键或唯一索引重复则更新.<br>
     * 主要用户批量更新与添加, 如果初始化时请直接调用 ums-api 的 /ums/conf/mobile/info/uploadInfos.<br>
     *     格式: 1999562  甘肃-兰州 <br>
     *     分隔符可以自定义.
     * @param mobileInfoList 实体类列表
     * @return  {@link Response(Boolean)}
     */
    @PostMapping("/savesOnDuplicateKeyUpdate")
    Response<Boolean> insertOnDuplicateKeyUpdate(@Valid @RequestBody List<MobileInfo> mobileInfoList);
}
