package org.arch.ums.user.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.mapper.PhoneMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 用户电话信息(Phone) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:31:28
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class PhoneDao extends CrudServiceImpl<PhoneMapper, Phone> implements CrudDao<Phone> {
    private final PhoneMapper phoneMapper;
}
