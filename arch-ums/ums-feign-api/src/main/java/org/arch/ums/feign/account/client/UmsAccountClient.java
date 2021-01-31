package org.arch.ums.feign.account.client;

import org.arch.framework.beans.Response;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.feign.account.config.UmsAccountDeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 用户登录与注册服务的 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-identifier", path = "/ums/account/identifier",
        configuration = UmsAccountDeFaultFeignConfig.class)
public interface UmsAccountClient {

    /**
     * 通过 {@link Identifier#getIdentifier()} 来获取 {@link Identifier}
     * @param identifier    用户唯一标识
     * @return  返回 {@link AuthLoginDto}
     */
    @GetMapping("/load/{identifier}")
    Response<AuthLoginDto> loadAccountByIdentifier(@PathVariable("identifier") String identifier);

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers   identifiers 数组
     * @return  identifiers 对应的结果集.
     */
    @RequestMapping(value = "/exists", method = {RequestMethod.POST})
    Response<List<Boolean>> exists(List<String> identifiers);


}
