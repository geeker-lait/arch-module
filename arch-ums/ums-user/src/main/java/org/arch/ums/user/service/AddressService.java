package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.Address;
import org.springframework.stereotype.Service;

/**
 * 用户地址表(Address) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:22
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService extends CrudService<Address, java.lang.Long> {

}