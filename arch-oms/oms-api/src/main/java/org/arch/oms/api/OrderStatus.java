//package org.arch.oms.api;
//
//import org.apache.commons.lang3.EnumUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.arch.framework.beans.enums.StringCodeDescr;
//import org.arch.oms.api.exception.OrderStatusNotFoundException;
//
//import java.util.Iterator;
//import java.util.List;
//
//public enum OrderStatus implements StringCodeDescr {
//    NONE("none", "无"),
//    PENDING("pending", "待定"),
//    WAITING_PAYMENT("waiting.payment", "等待支付"),
//    WAITING_PAYMENT_CONFIRM("waiting.payment.confirm", "支付待确认"),
//    IN_PROGRESS("in.progress", "正在处理"),
//    CANCELLED("cancelled", "已取消"),
//    CANCELLED_WAITING_PAYMENT("cancelled.waiting.payment", "已取消等待退款"),
//    RETURNED("returned", "已退货"),
//    RETURNED_WAITING_PAYMENT("returned.waiting.payment", "已退货等待退款"),
//    PARTIALLY_RETURNED("returned", "已退货"),
//    PARTIALLY_RETURNED_WAITING_PAYMENT("returned.waiting.payment", "已退货等待退款"),
//    SHIPPING("shipping", "送货中"),
//    COMPLETED("completed", "已完成"),
//    REFUNDING("refunding", "退款审核中"),
//    RETURNING("returning", "退货审核中"),
//    REFUNDED_COMPLETED("refund.completed", "已退款"),
//    RETURN_COMPLETED("return.completed", "已退货"),
//    SPLIT("split", "已拆分");
//
//    private static List<OrderStatus> VALUES = EnumUtils.getEnumList(OrderStatus.class);
//    private String code;
//    private String descr;
//
//    private OrderStatus(String value, String description) {
//        this.code = "os." + value;
//        this.descr = description;
//    }
//
//    public static OrderStatus getOrderStatus(String code) {
//        Iterator var1 = VALUES.iterator();
//
//        OrderStatus orderStatus;
//        do {
//            if (!var1.hasNext()) {
//                throw new OrderStatusNotFoundException(code);
//            }
//
//            orderStatus = (OrderStatus) var1.next();
//        } while (!StringUtils.equals(orderStatus.getCode(), code));
//
//        return orderStatus;
//    }
//
//
//    @Override
//    public String getCode() {
//        return code;
//    }
//
//    @Override
//    public String getDescr() {
//        return descr;
//    }
//}
