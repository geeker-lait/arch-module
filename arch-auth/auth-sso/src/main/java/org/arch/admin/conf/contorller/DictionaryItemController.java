package org.arch.admin.conf.contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.arch.ums.conf.client.ConfDictionaryItemFeignService;
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
@RestController
@RequestMapping("/conf/dictionary/item")
public class DictionaryItemController implements FeignCrudController<DictionaryItemSearchDto, java.lang.Long, DictionaryItemRequest, ConfDictionaryItemFeignService> {

    private final ConfDictionaryItemFeignService confDictionaryItemService;

    @Override
    public ConfDictionaryItemFeignService getFeignService() {
        return this.confDictionaryItemService;
    }

}
