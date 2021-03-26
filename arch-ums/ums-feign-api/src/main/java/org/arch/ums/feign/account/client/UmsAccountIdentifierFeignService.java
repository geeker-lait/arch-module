package org.arch.ums.feign.account.client;

import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.arch.ums.account.dto.Auth2ConnectionDto;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.entity.Identifier;
import org.arch.framework.feign.config.DeFaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 用户登录与注册服务的 feign 客户端.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.12 13:44
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-identifier", path = "/ums/account/identifier",
        configuration = DeFaultFeignConfig.class)
public interface UmsAccountIdentifierFeignService extends BaseFeignService<Identifier, Long> {

    /**
     * 通过 {@link Identifier#getIdentifier()} 来获取 {@link Identifier}
     * @param identifier    用户唯一标识
     * @return  返回 {@link AuthLoginDto}
     */
    @NonNull
    @GetMapping("/load/{identifier}")
    Response<AuthLoginDto> loadAccountByIdentifier(@PathVariable("identifier") String identifier);

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers   identifiers 数组
     * @return  identifiers 对应的结果集.
     */
    @NonNull
    @PostMapping(value = "/exists", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response<List<Boolean>> exists(@RequestParam("identifiers") List<String> identifiers);

    /**
     * 注册用户
     * @param authRegRequest    注册用户参数封装
     * @return  返回 {@link AuthLoginDto}
     */
    @NonNull
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response<AuthLoginDto> register(AuthRegRequest authRegRequest);

    /**
     * 保存 identifier
     * @param identifier     实体类
     * @return  {@link Response}
     */
    @Override
    @NonNull
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<Identifier> save(@RequestBody @Valid Identifier identifier);

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param identifier    {@link Identifier#getIdentifier()}
     * @return  是否删除成功.
     */
    @DeleteMapping(value = "/username/{identifier}")
    @NonNull
    Response<Boolean> logicDeleteByIdentifier(@PathVariable(value = "identifier") String identifier);

    /**
     * 根据 aid 解绑 identifier(第三方标识) :<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * @param aid           {@link Identifier#getAid()}
     * @param identifier    {@link Identifier#getIdentifier()}
     * @return  是否解绑成功.
     */
    @DeleteMapping(value = "/unbinding/{aid}/{identifier}")
    @NonNull
    Response<Boolean> unbinding(@PathVariable(value = "aid") Long aid, @PathVariable(value = "identifier") String identifier);

    /**
     * 删除账号
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return  true 表示成功, false 表示失败
     */
    @DeleteMapping(value = "/del/{accountId:[0-9]+}")
    @NonNull
    Response<Boolean> deleteByAccountId(@PathVariable(value = "accountId") Long accountId);

    /**
     * 查询 accountId 下所有的第三方绑定账号
     *
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return 绑定账号集合
     */
    @GetMapping(value = "/listAllConnections/{accountId:[0-9]+}")
    @NonNull
    Response<Map<String, List<Auth2ConnectionDto>>> listAllConnections(@PathVariable("accountId") Long accountId);
}
