package code.service.impl;

import code.dao.MemberLifeDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MemberLifeService;
import org.springframework.stereotype.Service;

/**
 * 会员生命周期服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberLifeServiceImpl implements MemberLifeService {
    private final MemberLifeDao memberLifeDao;

}