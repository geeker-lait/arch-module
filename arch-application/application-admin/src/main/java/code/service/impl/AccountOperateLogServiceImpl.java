package code.service.impl;

import code.dao.AccountOperateLogDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountOperateLogService;
import org.springframework.stereotype.Service;

/**
 * 账号操作记录服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountOperateLogServiceImpl implements AccountOperateLogService {
    private final AccountOperateLogDao accountOperateLogDao;

}