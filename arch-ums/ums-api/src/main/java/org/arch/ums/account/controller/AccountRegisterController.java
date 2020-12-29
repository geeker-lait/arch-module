package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号注册
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.28 16:08
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountRegisterController {

//    private final IAccountRegisterService accountRegisterService;
//    private final INickNameService nickNameService;
//    private final IAuthClientService authClientService;
//    private final IdService idService;
//
//    @PostMapping("/signUp")
//    public ResponseVo appSingUp(AuthRegAppDto authRegAppDto) {
//
//        authClientService.exists(authRegAppDto.getClientId(), authRegAppDto.getClientSecret());
//
//        authRegWebDto.setId(null);
//        authRegWebDto.setChannelType(ChannelType.ACCOUNT);
//
//        if (!hasText(authRegWebDto.getNickName())) {
//            authRegWebDto.setNickName(nickNameService.generateNickName());
//        }
//
//        accountRegisterService.addAccount(authRegWebDto);
//
//        return null;
//    }

}
