package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Name;
import org.springframework.stereotype.Service;

/**
 * 账号名(Name) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:53:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NameService extends CrudService<Name, java.lang.Long> {

}