package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FromBytearrayDao;
import org.arch.framework.automate.from.entity.FromBytearray;
import org.springframework.stereotype.Service;

/**
 * 表单数据(FromBytearray) 表服务层
 *
 * @author lait
 * @date 2021-02-08 13:25:30
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FromBytearrayService extends CrudService<FromBytearray, java.lang.Long> {
    private final FromBytearrayDao fromBytearrayDao = (FromBytearrayDao) crudDao;
}
