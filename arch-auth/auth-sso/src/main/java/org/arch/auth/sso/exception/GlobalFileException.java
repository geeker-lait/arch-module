package org.arch.auth.sso.exception;

/**
 * 文件相关的异常
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 12:01
 */
public class GlobalFileException extends RuntimeException {
    private static final long serialVersionUID = -1928296864775113484L;

    public GlobalFileException(String message) {
        super(message);
    }

    public GlobalFileException(String message, Throwable cause) {
        super(message, cause);
    }

}