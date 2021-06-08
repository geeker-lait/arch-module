package org.arch.ums.conf.api;

import org.arch.framework.beans.Response;
import org.arch.framework.web.feign.FeignApi;
import org.arch.framework.web.feign.config.DeFaultFeignConfig;
import org.arch.ums.conf.dto.FileInfoRequest;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * arch-conf-api {@code FileInfoService}服务远程调用的 feign 客户端.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 16:51
 */
@Component
@FeignClient(name = "arch-ums-api", contextId = "arch-ums-api-confFileInfo", path = "/ums/conf/file/info",
        configuration = DeFaultFeignConfig.class)
public interface ConfFileInfoApi extends FeignApi<FileInfoSearchDto, FileInfoRequest, Long> {

    /**
     * 根据 filePath 与 uploadType 删除 文件信息
     *
     * @param filePath   文件路径
     * @param uploadType 上传类型
     * @return 删除的文件信息
     */
    @NonNull
    @DeleteMapping(value = "/deleteByFilePathAndUploadType", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    Response<FileInfoSearchDto> deleteByFilePathAndUploadType(@RequestParam(value = "filePath") String filePath,
                                                              @RequestParam(value = "uploadType") String uploadType);
}
