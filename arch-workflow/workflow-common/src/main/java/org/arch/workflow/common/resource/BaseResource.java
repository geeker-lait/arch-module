package org.arch.workflow.common.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.arch.workflow.common.exception.ExceptionFactory;
import org.arch.workflow.common.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.Map;

/**
 * 资源基类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月8日
 */
public abstract class BaseResource {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected ExceptionFactory exceptionFactory;
    @Autowired
    protected ObjectMapper objectMapper;

    protected Pageable getPageable(Map<String, String> requestParams) {
        int page = -1;
        if (ObjectUtils.isNotEmpty(requestParams.get("pageNum"))) {
            page = ObjectUtils.convertToInteger(requestParams.get("pageNum"), 1);
        }
        int size = 10;
        if (ObjectUtils.isNotEmpty(requestParams.containsKey("pageSize"))) {
            size = ObjectUtils.convertToInteger(requestParams.get("pageSize"), 10);
        }

        if (page < 0) {
            return null;
        }

        Order order = null;
        if (ObjectUtils.isNotEmpty(requestParams.get("sortName"))) {
            String sortName = requestParams.get("sortName");
            String sortOrder = requestParams.get("sortOrder");
            if (ObjectUtils.isEmpty(sortOrder) || "desc".equals(sortOrder)) {
                order = new Order(Direction.DESC, sortName);
            } else {
                order = new Order(Direction.ASC, sortName);
            }
        }

        if (order == null) {
            return PageRequest.of(page - 1, size);
        } else {
            return PageRequest.of(page - 1, size, Sort.by(order));
        }
    }

    @SuppressWarnings("rawtypes")
    protected PageResponse createPageResponse(Page page) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setData(page.getContent());
        pageResponse.setTotal(page.getTotalElements());
        return pageResponse;
    }

}
