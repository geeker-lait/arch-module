package com.unichain.pay.channel.mfe88.demo.entity;

import lombok.Data;

@Data
public class Trade {
    String page;
    String payType;
    String refundMoney;
    String notifyUrl;
    String acctType;
    String sceneCode;
    String sCustomerName;

    String id_card_no;
    String expect_amount;
    String product_id;
    String bank_id;
    String rpUserIds;
    String moneys;
    String reOrgNo;
    String payPassWords;

    //	收银台
    String tokenId;
    //银联刷卡刷卡
    String terminfo;
    String qrcodetype;
    String termid;
    String longitude;
    String latitude;
    String networkLicense;
    String deviceType;
    String serialNum;
    String encryptRandNum;
    String sercetText;
    String appVersion;

    String isTuikuan;
    Double refundAmount;
    String refundTime;
    String refundNo;
    String refundDesc;
    String mRefundOrderNo;

    String openid;
    String openLoginName;
    String relativePath;
    //	wap
    String productName;
    String productNum;
    String productCode;
    String productDesc;
    String payerName;
    String payerIdCode;
    String payerMobile;
    String payerEmail;
    String orderTimeOut;


    String bankPublicKey;
    String payChannelType;
    String validPeriod;
    String cvv2;
    String smsCode;

    String userName;
    String idCode;
    String signVersion;
    String riskVersion;

    String orderNoList;
    String keyPassword;
    String orderTime;
    String orderNo;
    String orderAmount;
    String version;
    String orderSource;
    //	String bankPublicKey;
    String merchantPublicKey;
    String merchantPrivateKey;
    String md5Key;
    String keyType;
    String merchantNo;
    String service;
    String privateKey;
    String publicKey;
    String postUrl;
    String payChannelCode;
    String authCode;
    String curCode;
    String ext1;
    String ext2;
    String bgUrl;
    String signType;


    Long accountProp;
    String currency;
    String accountNo;
    String accountName;
    String bankGenneralName;
    String bankName;
    String bankCode;
    String bankProvcince;
    String bankCity;
    String cause;


    String payerIp;
    String rechargeAmt;
    String mOrderNo;
    String orgNo;
    String userId;
    String pageUrl;
    String charSet;
    String timestamp;
    String bizContent;
    //batchpayorder
    String code;//短信验证码
    String payPassWord;//支付密码
    String bankCardNo;//银行卡号
    //bankbind
    String bankAcctName;//开户人姓名
    String openBank;
    String province;
    String city;
    String phone;//手机号
    String cvv;//信用卡cvv码
    String acctExpireDate;//信用卡有效期
    String idNo;//证件号
    String idType;//证机类型  固定值01 身份证
    String isPrivate;//账户类型 1对私2对公
    String bankCardType;//银行卡类型 1储蓄卡 6信用卡
    String isKuaijie;//银行卡类型 1储蓄卡 6信用卡
    String bangkaType;//绑卡类型
    String aesKey;

    String orderTimestamp;
    //upp
    String idCardName;
    String idCardNo;
    String msgType;
    String loginPwd;
    String pageNum;
    String newPwd;
    String type;
    String spOrderNo;
    String gatheringUserId;
    String stageOrderId;
    String money;
    String payUserId;

    String parentProductCode;
    String startTime;
    String endTime;
    String status;
    String merchantOrgNo;
    //credit card
    String name;

}
