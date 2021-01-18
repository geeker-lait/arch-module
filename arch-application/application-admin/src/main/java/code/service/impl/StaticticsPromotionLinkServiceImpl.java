package code.service.impl;

import code.dao.StaticticsPromotionLinkDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.StaticticsPromotionLinkService;
import org.springframework.stereotype.Service;

/**
 * 统计-推广链接服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StaticticsPromotionLinkServiceImpl implements StaticticsPromotionLinkService {
    private final StaticticsPromotionLinkDao staticticsPromotionLinkDao;

}