package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.OauthTokenRequest;
import org.arch.ums.account.dto.OauthTokenSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方账号授权(OauthToken) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:31
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/oauth/token")
public interface OauthTokenRest extends CrudRest<OauthTokenRequest, java.lang.Long, OauthTokenSearchDto> {

}

