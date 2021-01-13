package org.arch.framework.crud;

/**
 * Created by Lait on 2017/7/10.
 */
public enum CrudMethod {
    // 插入
    IN, INS,

    // 插入/更新
    IN_OR_UPD,INS_OR_UPDS, UPD, UPDS,

    // 查询
    GET, LIST, FIND,QUERY,

    // 删除
    DEL, DELS,

    //函数
    COUNT,SUM,MAX,MIN,AVG,

    //树
    TREE;

    public static CrudMethod get(String crudMethod) {
        if (crudMethod != null) {
            try {
                return Enum.valueOf(CrudMethod.class, crudMethod.trim());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }
}
