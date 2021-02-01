package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Tag;
import org.springframework.stereotype.Service;

/**
 * 账号-标签(Tag) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TagService extends CrudService<Tag, java.lang.Long> {

}