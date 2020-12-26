package org.arch.ums.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String appId;
    private String orderId;
    private String paymentId;
    private String productId;
    private String orderStatus;
}
