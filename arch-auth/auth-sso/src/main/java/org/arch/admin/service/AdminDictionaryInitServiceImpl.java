package org.arch.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.init.service.DictionaryInitService;
import org.arch.framework.ums.enums.DictionaryItemInfo;
import org.arch.framework.ums.enums.DictionaryType;
import org.arch.ums.conf.client.ConfDictionaryFeignService;
import org.arch.ums.conf.client.ConfDictionaryItemFeignService;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 数据字典初始化
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.27 14:11
 */
@Service
@Slf4j
public class AdminDictionaryInitServiceImpl implements DictionaryInitService {
    private final ConfDictionaryFeignService dictionaryFeignService;
    private final ConfDictionaryItemFeignService dictionaryItemFeignService;

    public AdminDictionaryInitServiceImpl(ConfDictionaryFeignService dictionaryFeignService, ConfDictionaryItemFeignService dictionaryItemFeignService) {
        this.dictionaryFeignService = dictionaryFeignService;
        this.dictionaryItemFeignService = dictionaryItemFeignService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean init() {
        List<DictionaryRequest> dictionaryRequestList = new ArrayList<>();
        List<DictionaryItemRequest> dictionaryItemRequestList = new ArrayList<>();

        fillingDictionary(dictionaryRequestList);
        Response<List<DictionarySearchDto>> response = dictionaryFeignService.saveAll(dictionaryRequestList);
        List<DictionarySearchDto> dictionarySearchDtoList = response.getSuccessData();

        if (isNull(dictionarySearchDtoList)) {
            return true;
        }

        fillingItem(dictionaryItemRequestList, dictionarySearchDtoList);
        dictionaryItemFeignService.saveAll(dictionaryItemRequestList);
        return true;
    }

    private void fillingDictionary(@NonNull List<DictionaryRequest> dictionaryRequestList) {
        for (DictionaryType type : DictionaryType.values()) {
            String mark = type.getMark();
            String title = type.getTitle();
            dictionaryRequestList.add(new DictionaryRequest()
                    .setCode(type.name().toLowerCase())
                    .setTitle(title)
                    .setMark(hasText(mark) ? mark : title)
                    .setDeleted(Boolean.FALSE));
        }
    }

    private void fillingItem(@NonNull List<DictionaryItemRequest> dictionaryItemRequestList,
                             @NonNull List<DictionarySearchDto> dictionarySearchDtoList) {

        dictionarySearchDtoList.forEach(dictionarySearchDto -> {
            DictionaryType dictionaryType = DictionaryType.valueOf(dictionarySearchDto.getCode().toUpperCase());
            DictionaryItemInfo[] dictionaryItemInfos = dictionaryType.getDictionaryItemInfo();
            if (nonNull(dictionaryItemInfos)) {
                Arrays.stream(dictionaryItemInfos)
                        .forEach(info -> {
                            String title = info.getTitle();
                            String mark = info.getMark();
                            dictionaryItemRequestList.add(new DictionaryItemRequest()
                                    .setDictionaryId(dictionarySearchDto.getId())
                                    .setTitle(title)
                                    .setVal(info.getVal())
                                    .setSeq(info.getSeq())
                                    .setMark(hasText(mark) ? mark : title)
                                    .setDeleted(Boolean.FALSE));
                        });
            }
        });
    }
}
