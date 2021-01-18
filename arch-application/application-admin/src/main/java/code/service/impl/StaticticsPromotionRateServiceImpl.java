package code.service.impl;

import code.dao.StaticticsPromotionRateDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.StaticticsPromotionRateService;
import org.springframework.stereotype.Service;

/**
 * 统计-推广费率服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StaticticsPromotionRateServiceImpl implements StaticticsPromotionRateService {
    private final StaticticsPromotionRateDao staticticsPromotionRateDao;

}