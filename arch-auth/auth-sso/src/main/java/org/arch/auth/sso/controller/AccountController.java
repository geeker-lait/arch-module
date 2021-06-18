package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.event.DeleteAccountEvent;
import org.arch.ums.account.api.AccountIdentifierApi;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 账号相关操作控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 16:45
 */
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController implements ApplicationContextAware {

    private final AccountIdentifierApi accountIdentifierApi;
    private ApplicationContext applicationContext;

    /**
     * 删除账号
     *
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return true 表示成功, false 表示失败
     */
    @DeleteMapping("/{accountId}")
    public Response<Boolean> deleteAccount(@PathVariable("accountId") Long accountId) {
        try {
            Response<Boolean> response = accountIdentifierApi.deleteByAccountId(accountId);
            Boolean successData = response.getSuccessData();
            if (nonNull(successData) && successData) {
                this.applicationContext.publishEvent(new DeleteAccountEvent(accountId));
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed(accountId + " 删除失败");
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
