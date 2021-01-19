package code.service.impl;

import code.dao.MemberLevelDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MemberLevelService;
import org.springframework.stereotype.Service;

/**
 * 会员级别服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberLevelServiceImpl implements MemberLevelService {
    private final MemberLevelDao memberLevelDao;

}