package com.unichain.pay.yeepay.domain;

import lombok.Data;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/3/2019
 * @Description ${Description}
 */
@Data
public class PayForAnotherResponse {
    //处理结果代码	10	下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
    private String dealCode;
    //处理结果信息	300	处理结果信息，如 dealCode 为10000 则信息为成功，其他代码则返回对应的错误信息
    private String dealMsg;
    //	签名数据	1024	对签名字符串进行校验
    private String sign;
}
