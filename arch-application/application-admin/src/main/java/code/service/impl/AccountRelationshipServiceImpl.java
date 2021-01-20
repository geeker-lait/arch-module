package code.service.impl;

import code.dao.AccountRelationshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.AccountRelationshipService;
import org.springframework.stereotype.Service;

/**
 * 账号-关系服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountRelationshipServiceImpl implements AccountRelationshipService {
    private final AccountRelationshipDao accountRelationshipDao;

}