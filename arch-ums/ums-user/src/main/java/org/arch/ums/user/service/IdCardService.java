package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.IdCard;
import org.springframework.stereotype.Service;

/**
 * 用户身份证表(IdCard) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:05:59
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IdCardService extends CrudService<IdCard, java.lang.Long> {

}