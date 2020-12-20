package com.unichain.pay.channel.mfe88.demo.entity;

public enum PostCode {
    /*平台_页面名称_url(提交地址尾部)*/
    cashier_getCodeUrl("nativePay"),
    cashier_bankPay("bankPay"),
    cashier_offPayGate("offPayGate"),
    cashier_cxapp("appPay"),
    cashier_jsapi("jsapiPay"),
    cashier_auth("verifyTheTruth"),
    cashier_quickPayBind("quickPay"),
    cashier_quickPayBindConfirm("quickPay"),
    cashier_quickPayReSendVfCode("quickPay"),
    cashier_quickPayUnBind("quickPay"),
    cashier_quickPayApply("quickPay"),
    cashier_quickCertificationPay("quickPay"),
    cashier_quickPayConfirm("quickPay"),
    cashier_cashierQuickPayBind("quickPay"),
    cashier_cashierQuickPayApply("quickPay"),
    cashier_content_cashierQuickPayBindSearch("quickPay"),
    cashier_batchPayOrder("agentPay"),
    cashier_sponsorPayOrder("sponsorPay/agentPay"),
    cashier_content_orderSearch("paySearchOrder"),
    cashier_content_quickPaySameSearch("quickPay"),
    cashier_content_payForAnotherOneSearch("agentPay"),
    cashier_content_sponsorSearchMoney("sponsorPay/searchSponsorMoney"),
    cashier_content_sponsorSearchOrder("sponsorPay/searchSponsorOrder"),
    cashier_content_getAccount("selMoney"),
    cashier_content_selBankBindInfo("selBankBindInfo"),
    cashier_content_getTimestamp("getTimestamp"),
    cashier_refundOrder("refundOrder"),
    cashier_content_searchRefund("searchRefund"),

    upp_balancePay("uppPay/balancePay"),
    upp_withdrawMoney("uppPay/withdrawMoney"),
    upp_balancePayConfirm("uppPay/balancePayConfirm"),
    upp_bindBankCardConfrim("bankBindInfo/bindBankCardConfrim"),
    upp_createCard("creditCard/createCard"),
    upp_getDynamicCode("user/getDynamicCode"),
    upp_mergeJsapi("order/fenzhangPay/mergePayOrder"),
    upp_quickPay("uppPay/quickPay"),
    upp_quickPayConfirm("uppPay/quickPayConfirm"),
    upp_registerUserNew("user/registerUserNew"),
    upp_repayment("user/repayment"),
    upp_transferAccounts("uppPay/transferAccounts"),
    upp_addBindBankCard("bankBindInfo/addBindBankCard"),
    upp_addSplitOrder("order/split/manageSplitOrder"),
    upp_batchPayOrder("uppPay/batchPayOrder"),
    upp_bindIdCard("user/bindIdCard"),
    upp_cashier("uppPay/cashier"),
    upp_delBankCard("bankBindInfo/delBankCard"),
    upp_findPwd("user/findPwd"),
    upp_h5("uppPay/h5"),
    upp_jsapi("uppPay/jsapi"),
    upp_mergeBalancePay("order/fenzhangPay/mergeBalancePay"),
    upp_mergePayOrder("order/fenzhangPay/mergePayOrder"),
    upp_offPayGate("uppPay/offPayGate"),
    upp_refundOrder("uppPay/refundOrder"),
    upp_registerUser("user/registerUser"),
    upp_sendMsgApi("user/sendMsgApi"),
    upp_splitOrderConfrim("order/split/manageSplitOrder"),
    upp_content_splitOrderSearch("uppPay/splitOrderSearch"),
    upp_content_getBankList("bankBindInfo/getBankList"),
    upp_content_cardList("creditCard/cardListPage?orgNo=uup004&userId=10000606"),
    upp_content_getAccount("user/getAccount"),
    upp_content_getMoneyRecord("user/getMoneyRecord"),
    upp_content_orderSearch("uppPay/orderSearch"),
    upp_content_orderListSearch("uppPay/orderListSearch"),
    upp_content_orderListSearchForUser("uppPay/orderListSearchForUser"),
    upp_content_getUserInfo("user/getUserInfo"),
    upp_mergeWxApp("order/fenzhangPay/mergeWxApp");
    String url;

    PostCode(String url) {
        this.url = url;
    }

    public static String getPostUrl(String prefixUrl, String name) {
        if (!name.contains("_readme") && !name.contains("_testHistory") && !name.contains("_notify") && !name.contains("_log"))
            return prefixUrl + PostCode.valueOf(name).url;
        return "";
    }

    public static void main() {

    }
}
