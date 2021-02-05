package org.arch.application.demo.ums.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;
import org.arch.application.demo.ums.entity.Identifier;

/**
 * 用户-标识(Identifier) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:52:33
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IdentifierService extends CrudService<Identifier, java.lang.Long> {

}
