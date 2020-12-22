package org.arch.payment.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.payment.core.entity.StatusCode;
import org.arch.payment.core.mapper.StatusCodeMapper;
import org.arch.payment.core.service.IStatusCodeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class StatusCodeServiceImpl extends ServiceImpl<StatusCodeMapper, StatusCode> implements IStatusCodeService {

}
