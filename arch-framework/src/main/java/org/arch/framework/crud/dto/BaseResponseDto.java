package org.arch.framework.crud.dto;

import org.springframework.hateoas.server.core.Relation;

/**
 * 响应基础Dto信息<br>
 * 所有的响应类Dto都继承该对象<br>
 */
@Relation(collectionRelation = "resources")
public abstract class BaseResponseDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
