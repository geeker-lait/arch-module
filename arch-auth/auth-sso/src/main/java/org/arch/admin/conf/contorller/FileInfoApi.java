package org.arch.admin.conf.contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.web.feign.FeignCrudApi;
import org.arch.ums.conf.api.ConfFileInfoApi;
import org.arch.ums.conf.dto.FileInfoRequest;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 对象存储文件信息(FileInfo) CRUD 控制器
 *
 * @author YongWu zheng
 * @date 2021-05-19 19:33:02
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController("adminFileInfoController")
@RequestMapping("/conf/file/info")
public class FileInfoApi implements FeignCrudApi<FileInfoSearchDto, Long, FileInfoRequest, ConfFileInfoApi> {

    private final ConfFileInfoApi confFileInfoApi;

    @Override
    public ConfFileInfoApi getApi() {
        return this.confFileInfoApi;
    }

}
