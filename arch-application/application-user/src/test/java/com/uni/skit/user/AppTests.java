package com.uni.skit.user;

import com.uni.common.utils.RedisUtils;
import com.uni.user.service.IUUserBankcardService;
import com.uni.user.service.IUUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    private IUUserBankcardService userBankcardService;
    @Autowired
    private IUUserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void addBankCrd() {
    }

}
