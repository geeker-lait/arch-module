package org.arch.framework.beans.exception.constant;

import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.BaseException;
import org.arch.framework.beans.exception.assertion.CommonExceptionAssert;

/**
 * 通用返回结果
 */
public enum CommonStatusCode implements CommonExceptionAssert {

    /**
     * 服务器繁忙，请稍后重试
     */
    SERVER_BUSY(9998, "服务器繁忙"),
    /**
     * 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    SERVER_ERROR(9999, "网络异常"),


    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    // retry
    DUPLICATE_KEY(7001, "主键或唯一索引重复"),

    // 推荐码与账号类型
    EXTRACT_ACCOUNT_TYPE(7990, "提取账号类型失败, aid[%s]"),
    NOT_USER_OR_ARCH_SOURCE_TYPE(7991, "推荐类型不是 USER 或 ARCH 类型"),
    EXTRACT_USER_RECOMMEND_CODE_FAILED(7992, "提取 USER 推荐码失败: code[%s]"),
    QUERY_USER_RECOMMEND_CODE_FAILED(7993, "查询 USER 推荐码失败: code[%s]"),
    GENERATE_USER_RECOMMEND_CODE_FAILED(7994, "生成用户推荐关系失败: accountId[%s]"),
    SAVE_USER_RECOMMEND_RELATION_FAILED(7995, "保存用户推荐关系失败: code[%s]"),

    // Time
    DATE_NOT_NULL(5001, "日期不能为空"), DATETIME_NOT_NULL(5001, "时间不能为空"), TIME_NOT_NULL(5001, "时间不能为空"),
    DATE_PATTERN_MISMATCH(5002, "日期[%s]与格式[%s]不匹配，无法解析"), PATTERN_NOT_NULL(5003, "日期格式不能为空"),
    PATTERN_INVALID(5003, "日期格式[%s]无法识别"),


    WORK_ID_GENERATE_FAILED(4000,"ID生成器的workerID已经被占满了")
    ;

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;

    /**
     * 校验返回结果是否成功
     *
     * @param response 远程调用的响应
     */
    public static void assertSuccess(Response<?> response) {
        SERVER_ERROR.assertNotNull(response);
        int code = response.getCode();
        if (CommonStatusCode.SUCCESS.getCode() != code) {
            String msg = response.getMsg();
            throw new BaseException(code, msg);
        }
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescr() {
        return message;
    }

    CommonStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
