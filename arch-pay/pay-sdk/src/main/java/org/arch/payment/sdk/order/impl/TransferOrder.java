package org.arch.payment.sdk.order.impl;

import org.arch.payment.sdk.*;
import org.arch.payment.sdk.order.Order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 转账订单
 */
public class TransferOrder implements Order {

    /**
     * 转账批次订单单号
     */
    private String batchNo;

    /**
     * 转账订单单号
     */
    private String outNo;

    /**
     * 收款方账户, 用户openid,卡号等等
     */
    private String payeeAccount;

    /**
     * 转账金额
     */
    private BigDecimal amount;

    /**
     * 付款人名称
     */
    private String payerName;

    /**
     * 收款人名称
     */
    private String payeeName;
    /**
     * 收款人地址
     */
    private String payeeAddress;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收款开户行
     */
    private Bank bank;

    /**
     * 收款开户行地址
     */
    private String payeeBankAddress;

    /**
     * 币种
     */
    private CurType curType;
    /**
     * 国家代码
     */
    private CountryCode countryCode;
    /**
     * 转账类型，收款方账户类型，比如支付宝账户或者银行卡
     */
    private TransferType transferType;

    /**
     * 操作者ip，根据支付平台所需进行设置
     */
    private String ip;


    /**
     * 订单附加信息，可用于预设未提供的参数，这里会覆盖以上所有的订单信息，
     */
    private Map<String, Object> attr;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getPayeeBankAddress() {
        return payeeBankAddress;
    }

    public void setPayeeBankAddress(String payeeBankAddress) {
        this.payeeBankAddress = payeeBankAddress;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public CurType getCurType() {
        return curType;
    }

    public void setCurType(CurType curType) {
        this.curType = curType;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Map<String, Object> getAttrs() {
        if (null == attr) {
            attr = new HashMap<>();
        }
        return attr;
    }

    @Override
    public Object getAttr(String key) {
        return getAttrs().get(key);
    }


    /**
     * 添加订单信息
     *
     * @param key   key
     * @param value 值
     */
    @Override
    public void addAttr(String key, Object value) {
        getAttrs().put(key, value);
    }


}
