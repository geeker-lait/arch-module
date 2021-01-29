package org.arch.oms.api;

import java.util.List;
import org.apache.commons.lang3.EnumUtils;
import org.arch.framework.beans.enums.StringCodeDescr;

public enum OrderEvent implements StringCodeDescr {
    CREATE("create", "订单创建"),
    PAYMENT_PROCESSED("payment.processed", "支付处理中"),
    NOTIFY_PAYMENT("notify.payment", "通知支付"),
    PAYMENT_OK("payment.ok", "订单支付成功"),
    ORDER_CANCEL("order.cancel", "订单取消"),
    ORDER_CANCEL_WITH_REFUND("order.cancel.refund", "订单取消并退款"),
    ORDER_CANCEL_WITH_RETURN("order.cancel.return", "订单取消并退货"),
    REFUND_PROCESSED("refund.processed", "退款处理中"),
    REFUND_OK("refund.ok", "订单退款成功"),
    PARTIAL_REFUND_OK("partial.refund.ok", "部分退款成功"),
    RETURN_OK("return.ok", "退货完成"),
    RELEASE_TO_PACK("release.to.pack", "开始打包"),
    PACKING_COMPLETE("packing.complete", "打包完成"),
    SAVE_LOGISTIC_INFO("save.logistic.info", "保存物流信息"),
    RELEASE_TO_SHIPMENT("release.to.shipment", "发货"),
    SHIPMENT_COMPLETE("shipment.complete", "配送完成"),
    SPLIT_BY_ITEMS("split.by.items", "拆分"),
    SPLIT_BY_OUTSTOCK("split.by.outstock", "split.by.outstock"),
    ORDER_DENY_REFUND("order.deny.refund", "拒绝退款"),
    ORDER_DENY_RETURN("order.deny.return", "拒绝退货"),
    PARTNER_CONFIRM("partner.confirm", "管家确认"),
    RELEASE_TO_WAREHOUSE_PACK("release.to.warehouse.pack", "大仓开始打包"),
    RELEASE_TO_WAREHOUSE_SHIPPING("release.to.warehouse.shipping", "大仓开始配送"),
    WAREHOUSE_SHIPMENT_COMPLETE("warehouse.shipment.complete", "大仓打包完成"),
    UPDATE_DELIVERY_ADDRESS("update.delivery.address", "更新配送地址"),
    UPDATE_DELIVERY_TIME("update.delivery.time", "更新预约配送时间"),
    UPDATE_PARTNER_INFO("update.delivery.partner.info", "更新管家信息"),
    UPDATE_RECEIVER_COORDINATE("update.receiver.coordinate", "更新收货人经纬度"),
    UPDATE_VIRTUAL_ORDER("update.virtual.order", "更新虚拟店订单真实门店"),
    SPLIT_BY_WAREHOUSEID("split.by.warehouseid", "多档口拆单"),
    SPLIT_BY_MIX_SKU_DELIVERY("split.by.mix.sku.delivery", "多配送(履约)类型拆单"),
    UPDATE_MIX_SKU_DELIVERY("update.mix.sku.delivery.order", "更新多配送(履约)类型订单"),
    REPLENISHMENT_ORDER("replenishment.order", "补单");

    private static List<OrderEvent> VALUES = EnumUtils.getEnumList(OrderEvent.class);
    private String code;
    private String descr;

    private OrderEvent(String code, String descr) {
        this.code = "oe." + code;
        this.descr = descr;
    }

    public static OrderEvent getOrderEvent(String value) {
        //return (OrderEvent)EnumUtils.getEnum(VALUES, OrderEvent.class, value);
        return null;
    }



    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescr() {
        return descr;
    }
}
