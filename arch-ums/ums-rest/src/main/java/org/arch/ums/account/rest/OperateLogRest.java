package org.arch.ums.account.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.account.dto.OperateLogRequest;
import org.arch.ums.account.dto.OperateLogSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号操作记录(OperateLog) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:51:36
 * @since 1.0.0
 */

@RestController
@RequestMapping("/account/operate/log")
public interface OperateLogRest extends CrudRest<OperateLogRequest, java.lang.Long, OperateLogSearchDto> {

}

