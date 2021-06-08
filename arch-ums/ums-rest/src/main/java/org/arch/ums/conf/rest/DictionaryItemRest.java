package org.arch.ums.conf.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典明细(DictionaryItem) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:56:05
 * @since 1.0.0
 */

@RestController
@RequestMapping("/conf/dictionary/item")
public interface DictionaryItemRest extends CrudRest<DictionaryItemRequest, java.lang.Long, DictionaryItemSearchDto> {

}

