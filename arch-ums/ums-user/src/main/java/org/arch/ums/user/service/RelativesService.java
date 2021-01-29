package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.Relatives;
import org.springframework.stereotype.Service;

/**
 * 用户亲朋信息(Relatives) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:10:07
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RelativesService extends CrudService<Relatives, java.lang.Long> {

}