package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.ums.feign.account.client.UmsAccountIdentifierFeignService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号相关操作控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 16:45
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final UmsAccountIdentifierFeignService umsAccountIdentifierFeignService;

    /**
     * 删除账号
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return  true 表示成功, false 表示失败
     */
    @DeleteMapping("/{accountId}")
    public Response<Boolean> deleteAccount(@PathVariable("accountId") Long accountId) {
        try {
            return umsAccountIdentifierFeignService.deleteByAccountId(accountId);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.failed(accountId + " 删除失败");
        }
    }
}
