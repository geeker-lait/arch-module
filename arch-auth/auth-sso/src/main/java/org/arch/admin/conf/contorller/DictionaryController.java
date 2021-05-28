package org.arch.admin.conf.contorller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudController;
import org.arch.ums.conf.client.ConfDictionaryFeignService;
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
public class DictionaryController implements FeignCrudController<DictionarySearchDto, Long, DictionaryRequest, ConfDictionaryFeignService> {

    private final ConfDictionaryFeignService confDictionaryService;

    @Override
    public ConfDictionaryFeignService getFeignService() {
        return this.confDictionaryService;
    }

}
