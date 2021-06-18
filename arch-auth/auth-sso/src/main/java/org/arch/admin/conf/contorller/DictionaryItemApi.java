package org.arch.admin.conf.contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.conf.api.ConfDictionaryItemApi;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 数据字典明细(DictionaryItem) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:33:01
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminDictionaryItemController")
@RequestMapping("/conf/dictionary/item")
public class DictionaryItemApi implements FeignCrudApi<DictionaryItemSearchDto, Long, DictionaryItemRequest, ConfDictionaryItemApi> {

    private final ConfDictionaryItemApi confDictionaryItemApi;

    @Override
    public ConfDictionaryItemApi getApi() {
        return this.confDictionaryItemApi;
    }

}
