package org.arch.framework.id;


/**
 * @Description 分布式ID生成接口
 */

public interface IdService {

    /**
     * @return
     * @Description 生成分布式ID
     */
    String generateId(IdKey idBizCode);

    String generateId(String prefix, IdKey idKey);

    /**
     * @return
     * @Description 生成分布式ID
     */
    Long randomLongId(int length);


}
