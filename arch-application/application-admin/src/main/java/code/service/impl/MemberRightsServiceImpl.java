package code.service.impl;

import code.dao.MemberRightsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MemberRightsService;
import org.springframework.stereotype.Service;

/**
 * 会员权益服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberRightsServiceImpl implements MemberRightsService {
    private final MemberRightsDao memberRightsDao;

}