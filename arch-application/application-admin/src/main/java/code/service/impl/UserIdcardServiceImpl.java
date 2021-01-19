package code.service.impl;

import code.dao.UserIdcardDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.UserIdcardService;
import org.springframework.stereotype.Service;

/**
 * 用户-身份信息服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserIdcardServiceImpl implements UserIdcardService {
    private final UserIdcardDao userIdcardDao;

}