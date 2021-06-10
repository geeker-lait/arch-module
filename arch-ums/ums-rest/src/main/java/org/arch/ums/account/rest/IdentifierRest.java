package org.arch.ums.account.rest;

import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.Auth2ConnectionDto;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.dto.IdentifierRequest;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 用户-标识(Identifier) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:10
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/identifier")
public interface IdentifierRest extends CrudRest<IdentifierRequest, java.lang.Long, IdentifierSearchDto> {
    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers    identifiers 列表
     * @return  identifiers 对应的结果集.
     */
    @PostMapping(value = "/exists")
    @NonNull
    List<Boolean> exists(@RequestParam(value = "identifiers") List<String> identifiers);


    /**
     * 根据 identifier 获取用户信息 {@link AuthLoginDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthLoginDto}. 不存在返回 null.
     */
    @GetMapping("/load/{identifier}")
    @NonNull
    AuthLoginDto loadAccountByIdentifier(@PathVariable(value = "identifier") String identifier);

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
    Boolean logicDeleteByIdentifier(@PathVariable(value = "identifier") String identifier);

    /**
     * 注册用户
     * @param authRegRequest    注册用户参数封装
     * @return  返回 {@link AuthLoginDto}, 注册失败 {@link Response#getData()} 返回 null.
     */
    @PostMapping(value = "/register")
    @NotNull
    AuthLoginDto register(@Validated AuthRegRequest authRegRequest);

    /**
     * 根据 aid 解绑 identifier(第三方标识) :<br>
     * & account_identifier:<br>
     * 1. 更新 deleted 字段值为 1.<br>
     * 2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     * 添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     * 添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     *
     * @param aid        {@link Identifier#getAid()}
     * @param identifier {@link Identifier#getIdentifier()}
     * @return 是否解绑成功.
     */
    @DeleteMapping(value = "/unbinding/{aid}/{identifier}")
    @NonNull
    Boolean unbinding(@PathVariable(value = "aid") Long aid,
                      @PathVariable(value = "identifier") String identifier);

    /**
     * 删除账号
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return  true 表示成功, false 表示失败
     */
    @DeleteMapping(value = "/del/{accountId}")
    @NonNull
    Boolean deleteByAccountId(@PathVariable(value = "accountId") Long accountId);

    /**
     * 查询 accountId 下所有的第三方绑定账号
     *
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return 绑定账号集合
     */
    @GetMapping(value = "/listAllConnections/{accountId:[0-9]+}")
    @NonNull
    Map<String, List<Auth2ConnectionDto>> listAllConnections(@PathVariable("accountId") Long accountId);
}

