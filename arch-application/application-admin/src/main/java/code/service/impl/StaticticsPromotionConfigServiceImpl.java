package code.service.impl;

import code.dao.StaticticsPromotionConfigDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.StaticticsPromotionConfigService;
import org.springframework.stereotype.Service;

/**
 * 统计-统计配置服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StaticticsPromotionConfigServiceImpl implements StaticticsPromotionConfigService {
    private final StaticticsPromotionConfigDao staticticsPromotionConfigDao;

}