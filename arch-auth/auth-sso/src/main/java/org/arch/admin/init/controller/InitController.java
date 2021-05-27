package org.arch.admin.init.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.init.service.DictionaryInitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * 应用初始化接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.27 14:56
 */
@RestController
@RequestMapping("/init")
@RequiredArgsConstructor
@Slf4j
public class InitController {

    private final Map<String, DictionaryInitService> dictionaryInitServiceMap;

    @PostMapping("/dictionary")
    public void initDictionary() {
        if (nonNull(this.dictionaryInitServiceMap)) {
            this.dictionaryInitServiceMap.values().forEach(DictionaryInitService::init);
        }
    }

}
