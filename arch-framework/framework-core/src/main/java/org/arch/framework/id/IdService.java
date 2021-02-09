package org.arch.framework.id;


import org.arch.framework.api.IdKey;

/**
 * @Description 分布式ID生成接口
 */
public interface IdService {

    /**
     * 生成分布式 ID, 支持一个小时100w个 ID(21) 的生成 012023410351100000001 .<br>
     * {@link IdKey#APP_ID} 返回 ID格式 0020123000001
     * <pre>
     * // 返回 id: 0020123000001
     * // idBizCode  年  日   redis原子自增
     * //   00       20 123   000001
     * </pre>
     * 除 {@link IdKey#APP_ID} 外返回
     * <pre>
     * // 返回 id: 1202348639900000001
     * // idBizCode  年  日  日内的秒数 redis原子自增
     * //   1        20 234 86399 0000001
     * </pre>
     * @param idBizCode     {@link IdKey}
     * @return  返回 id.
     * @Description 生成分布式 ID, 支持一个小时100w个订单号的生成
     * 1 202348639900000001
     */
    String generateId(IdKey idBizCode);

    /**
     * {@link IdKey#APP_ID} 返回 ID格式 prefix + 0020123000001 .<br>
     * <pre>
     * // 返回 id: prefix + 0020123000001
     * // prefix  idBizCode  年  日   redis原子自增
     * // prefix    00       20 123   000001
     * </pre>
     * 除 {@link IdKey#APP_ID} 外返回
     * <pre>
     * // 返回 id: prefix + 202348639900000001
     * // prefix      年  日  日内的秒数 redis原子自增
     * //   1        20 234 86399 0000001
     * </pre>
     * @param prefix    bizPrefix
     * @param idKey     {@link IdKey}
     * @return  返回 id.
     */
    String generateId(String prefix, IdKey idKey);

    /**
     * @return  随机 ID
     * @Description 随机 ID
     */
    Long randomLongId(int length);


    long nextId(IdKey idType);


}
