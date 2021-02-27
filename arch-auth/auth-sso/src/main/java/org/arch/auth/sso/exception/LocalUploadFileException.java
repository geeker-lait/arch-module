package org.arch.auth.sso.exception;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 12:26
 */
public class LocalUploadFileException extends RuntimeException {
    private static final long serialVersionUID = -1928296864775113488L;

    public LocalUploadFileException(String message) {
        super(message);
    }

    public LocalUploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

}