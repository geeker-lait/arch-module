package org.arch.ums.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.entity.Education;
import org.springframework.stereotype.Service;

/**
 * 用户学历信息(Education) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:04:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EducationService extends CrudService<Education, java.lang.Long> {

}