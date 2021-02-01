package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Menu;
import org.springframework.stereotype.Service;

/**
 * 账号-菜单(Menu) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:15:12
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService extends CrudService<Menu, java.lang.Long> {

}