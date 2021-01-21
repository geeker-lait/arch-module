package org.arch.framework.automate.from.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.dao.FromBytearrayDao;
import org.arch.framework.automate.from.service.FromBytearrayService;
import org.springframework.stereotype.Service;

/**
 * 表单数据服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FromBytearrayServiceImpl implements FromBytearrayService {
    private final FromBytearrayDao fromBytearrayDao;

}
