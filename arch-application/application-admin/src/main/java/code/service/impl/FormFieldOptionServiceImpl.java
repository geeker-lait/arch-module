package code.service.impl;

import code.dao.FormFieldOptionDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.FormFieldOptionService;
import org.springframework.stereotype.Service;

/**
 * 表单字段选项服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormFieldOptionServiceImpl implements FormFieldOptionService {
    private final FormFieldOptionDao formFieldOptionDao;

}