package org.arch.admin.conf.contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.conf.api.ConfDictionaryApi;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 数据字典(Dictionary) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:33:01
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminDictionaryController")
@RequestMapping("/conf/dictionary")
public class DictionaryApi implements FeignCrudApi<DictionarySearchDto, Long, DictionaryRequest, ConfDictionaryApi> {

    private final ConfDictionaryApi confDictionaryApi;

    @Override
    public ConfDictionaryApi getFeignApi() {
        return this.confDictionaryApi;
    }

}
