package code.service.impl;

import code.dao.FromBytearrayDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import code.service.FromBytearrayService;
import org.springframework.stereotype.Service;

/**
 * 表单数据服务接口实现
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FromBytearrayServiceImpl implements FromBytearrayService {
    private final FromBytearrayDao fromBytearrayDao;

}