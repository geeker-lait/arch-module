package org.arch.ums.account.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 4/11/2020
 * @Description ${Description}
 */
@Data
public class StatisticsDto implements Serializable {
    private static final long serialVersionUID = 5783973394435479911L;

    private Long id;
    private String productId;
    private String source;
}
