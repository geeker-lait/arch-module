package org.arch.payment.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.payment.core.entity.PayDirective;
import org.arch.payment.core.mapper.PayDirectiveMapper;
import org.arch.payment.core.service.IPayDirectiveService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class PayDirectiveServiceImpl extends ServiceImpl<PayDirectiveMapper, PayDirective> implements IPayDirectiveService {

}
