package code.service.impl;

import code.dao.UserEducationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.UserEducationService;
import org.springframework.stereotype.Service;

/**
 * 用户-学历信息服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserEducationServiceImpl implements UserEducationService {
    private final UserEducationDao userEducationDao;

}