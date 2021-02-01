package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.mapper.PhoneMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户电话信息(Phone) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:09:02
 * @since 1.0.0
 */
@Slf4j
@Repository
public class PhoneDao extends ServiceImpl<PhoneMapper, Phone> implements CrudDao<Phone> {

}