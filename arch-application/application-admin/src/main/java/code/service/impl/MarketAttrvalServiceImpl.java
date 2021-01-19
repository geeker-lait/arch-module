package code.service.impl;

import code.dao.MarketAttrvalDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.MarketAttrvalService;
import org.springframework.stereotype.Service;

/**
 * 营销-自定义动态属性值服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MarketAttrvalServiceImpl implements MarketAttrvalService {
    private final MarketAttrvalDao marketAttrvalDao;

}