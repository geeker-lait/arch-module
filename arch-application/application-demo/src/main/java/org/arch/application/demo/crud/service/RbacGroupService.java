package org.arch.application.demo.crud.service;

import org.arch.application.demo.crud.entity.RbacGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

/**
 * 组织机构表服务接口实现
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RbacGroupService extends CrudService<RbacGroup, Long> {

}