package org.arch.ums.conf.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典(Dictionary) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:55:45
 * @since 1.0.0
 */

@RestController
@RequestMapping("/conf/dictionary")
public interface DictionaryRest extends CrudRest<DictionaryRequest, java.lang.Long, DictionarySearchDto> {

}

