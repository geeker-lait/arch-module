package code.service.impl;

import code.dao.FormFieldTypDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.FormFieldTypService;
import org.springframework.stereotype.Service;

/**
 * 表单字段类型服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormFieldTypServiceImpl implements FormFieldTypService {
    private final FormFieldTypDao formFieldTypDao;

}