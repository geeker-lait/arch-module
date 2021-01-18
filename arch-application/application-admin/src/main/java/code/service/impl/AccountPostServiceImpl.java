package code.service.impl;

import code.dao.AccountPostDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountPostService;
import org.springframework.stereotype.Service;

/**
 * 账号-岗位服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountPostServiceImpl implements AccountPostService {
    private final AccountPostDao accountPostDao;

}