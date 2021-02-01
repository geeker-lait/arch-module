package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.Phone;
import org.springframework.stereotype.Service;

/**
 * 用户电话信息(Phone) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:09:06
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneService extends CrudService<Phone, java.lang.Long> {

}