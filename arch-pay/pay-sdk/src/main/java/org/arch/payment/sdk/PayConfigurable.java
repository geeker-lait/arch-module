package org.arch.payment.sdk;

/**
 * 支付客户端配置存储
 */
public interface PayConfigurable extends Attrs {

    /**
     * 附加支付配置
     *
     * @return 附加信息
     */
    Object getAttach();

    /**
     * 应用id
     *
     * @return 应用id
     */
    String getAppId();

    /**
     * 获取商户号
     *
     * @return
     */
    String getMerchantNo();

    /**
     * 合作商唯一标识
     *
     * @return 合作商唯一标识
     */
    String getPid();


    /**
     * 授权令牌
     *
     * @return 授权令牌
     */
    String getToken();

    /**
     * 获取指令
     *
     * @return
     */
    String getDirectiveUri();

    /**
     * 服务端异步回调Url
     *
     * @return 异步回调Url
     */
    String getNotifyUrl();

    /**
     * 服务端同步回调Url
     *
     * @return 同步回调Url
     */
    String getReturnUrl();

    /**
     * 签名方式
     *
     * @return 签名方式
     */
    String getSignType();

    /**
     * 字符编码格式
     *
     * @return 字符编码
     */
    String getInputCharset();

    /**
     * 支付平台公钥(签名校验使用)
     *
     * @return 公钥
     */
    String getKeyPublic();

    /**
     * 应用私钥(生成签名时使用)
     *
     * @return 私钥
     */
    String getKeyPrivate();

    /**
     * 支付类型 自定义
     * 这里暂定 aliPay 支付宝， wxPay微信支付
     *
     * @return 支付类型
     */
    String getPayType();


    /**
     * 应该是线程安全的
     *
     * @param accessToken      新的accessToken值
     * @param expiresInSeconds 过期时间，以秒为单位 多少秒
     */
    void updateAccessToken(String accessToken, int expiresInSeconds);

    /**
     * 应该是线程安全的
     *
     * @param accessToken 新的accessToken值
     * @param expiresTime 过期时间，时间戳
     */
    void updateAccessToken(String accessToken, long expiresTime);

    /**
     * 是否为测试环境， true测试环境
     *
     * @return true测试环境
     */
    boolean isTest();


}
