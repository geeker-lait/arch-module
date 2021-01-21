package org.arch.framework.automate.from.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.dao.FormInstanceDao;
import org.arch.framework.automate.from.service.FormInstanceService;
import org.springframework.stereotype.Service;

/**
 * 表单实例服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormInstanceServiceImpl implements FormInstanceService {
    private final FormInstanceDao formInstanceDao;

}
