package org.arch.auth.sso.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.28 12:19
 */
@ConfigurationProperties(prefix = "arch.file")
@Getter
@Setter
@Validated
public class FileProperties {
    // =========== 文件上下传相关 ============
    /**
     * 图片文件存放的 url. 默认: ""
     */
    private String url = "";
    /**
     * 图片文件存放的根目录. 默认: /image/account/
     */
    private String rootPath = "/image/account/";
    /**
     * 上传的类型, 即目录名称. 默认: avatar
     */
    private String uploadType = "avatar";

    /**
     * 允许最大的上传图片大小, 默认: 2 * 1024 * 1024
     */
    private Integer imageMaxSize = 2 * 1024 * 1024;
}
