package org.arch.framework.automate.xmind.nodespace;


/**
 * Prams type
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-04-28 15:04
 */
public enum ParamType {
    /**
     * 实体类
     */
    ENTITY("", ""),
    /**
     * 包路径
     */
    IMPORT("", ""),
    /**
     * 泛型
     */
    GENERIC("", ""),
    /**
     * 泛型类型, 如: {@code String -> <String>}, {@code String, Object -> <String, Object>},
     * {@code Map<String, Object> -> <Map<String, Object>>}
     */
    GENERIC_TYP("", ""),
    /**
     * 泛型值, 如: T/E/R
     */
    GENERIC_VAL("", ""),
    /**
     * 注解字段(as ANNOTATION)
     */
    ANNOT("", ""),
    /**
     * 注解字段(as ANNOT)
     */
    ANNOTATION("", ""),
    /**
     * 注解对象
     */
    ANNOT_E("", ""),
    /**
     * 注解键值对
     */
    ANNOT_VAL("", ""),
    /**
     * 普通方法
     */
    METHOD("", ""),
    /**
     * 接口方法, 与 GET/POST/PUT/DELETE 功能相似
     */
    INTERFACE("", ""),
    /**
     * 接口入参
     */
    IN("", ""),
    /**
     * 接口返回值
     */
    OUT("", ""),
    /**
     * 查询请求
     */
    GET("", ""),
    /**
     * 新增请求
     */
    POST("", ""),
    /**
     * 修改请求
     */
    PUT("", ""),
    /**
     * 删除请求
     */
    DEL("", ""),
    /**
     * 属于 TitleType.API 子节点, 如: uri = user, 那么在接口上注解 @RequestMapping("/user").<br>
     * 属于 ParamType(INTERFACE/GET/POST/PUT/DELETE) 子节点, 如: uri = user, 那么在接口方法上根据接口方法的类型添加对应的注解.
     * <pre>
     *  TitleType.INTERFACE     @RequestMapping("/user")
     *  TitleType.GET           @GetMapping("/user")
     *  TitleType.POST          @PostMapping("/user")
     *  TitleType.PUT           @PutMapping("/user")
     *  TitleType.DELETE        @DeleteMapping("/user")
     * </pre>
     */
    URI("", ""),
    OBJECT("Object", ""),
    OBJ("Object", ""),
    LIST("List", "java.util.List"),
    SET("Set", "java.util.Set"),
    MAP("Map", "java.util.Map"),
    COLLECTION("Collection", "java.util.Collection"),
    LONG("Long", ""),
    STRING("String", ""),
    INT("Integer", ""),
    INTEGER("Integer", ""),
    VOID("void", ""),
    BOOLEAN("Boolean", ""),
    FLOAT("Float", ""),
    DOUBLE("Double", ""),
    BYTE("Byte", ""),
    SHORT("Short", ""),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),
    DATE("Date", "java.util.Date");

    /**
     * 类型
     */
    private final String type;
    private final String pkg;

    ParamType(String type, String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    public String getType() {
        return type;
    }

    public String getPkg() {
        return pkg;
    }
}