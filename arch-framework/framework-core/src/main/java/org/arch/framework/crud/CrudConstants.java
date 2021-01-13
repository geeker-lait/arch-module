package org.arch.framework.crud;

/**
 * 系统初始化常量
 */
public interface CrudConstants {

    /**
     * 编码格式UTF8
     */
    String UTF8 = "UTF-8";

    /**
     * 统计（日）的数组
     */
    String[] DAY = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    /**
     * 统计（月） 的数组
     */
    String[] MONTH = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};

    // -------------------日期格式 START-----------------------
    /**
     * 精确到分钟的时间格式
     */
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 精确到秒的时间格式
     */
    String DATE_TIME_MS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到天的时间格式
     */
    String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 小时分钟秒的时间格式
     */
    String TIME_FORMAT = "HH:mm:ss";

    /**
     * 时间格式数组
     */
    String[] DATE_FORMAT_PATTERNS = {DATE_TIME_FORMAT, DATE_FORMAT, TIME_FORMAT};
    // -------------------日期格式 END-----------------------

    // -------------------分页参数封装 START-----------------------

    /**
     * 分页请求的页码字段
     */
    String DEFAULT_PAGE_NUM_FIELD = "number";

    /**
     * 分页请求的分页大小字段
     */
    String DEFAULT_PAGE_SIZE_FIELD = "size";

    /**
     * 分页请求的排序字段<br>
     * 如：sortTypes=id,name 则生成sql后的排序顺序为：order by id,name desc
     */
    String DEFAULT_SORT_TYPES_FIELD = "sortTypes";

    /**
     * 分页请求的默认排序字段为主键排序
     */
    String DEFAULT_SORT_TYPE_VAL = "id";

    /**
     * 默认的数据分页大小
     */
    String DEFAULT_PAGE_SIZE_VAL = "10";
    /**
     * 默认的分页的页码
     */
    String DEFAULT_PAGE_NUM_VAL = "1";

    /**
     * 搜索默认前缀
     */
    String SEARCH_PREFIX = "search_";

    /**
     * 空的搜索默认前缀
     */
    String EMPTY_SEARCH_PREFIX = "";

    // -------------------分页参数封装 END-----------------------

    /**
     * 登录用户失效的提示信息
     */
    String MSG_ERROR_USER = "用户已失效，请重新进入！";

    /**
     * 权限不足的提示
     */
    String MSG_ERROR_PERMISSION = "权限不足！";

    /**
     * 操作成功的提示
     */
    String MSG_SUCCESS = "操作成功！";

    /**
     * 操作失败的提示
     */
    String MSG_ERROR = "操作失败！";

    /**
     * 操作成功标记
     */
    int SUCCESS = 200;

    /**
     * 操作失败标记
     */
    int ERROR = 1;

}
