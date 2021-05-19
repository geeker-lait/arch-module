package org.arch.admin.conf.contorller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.feign.FeignCrudController;
import org.arch.ums.conf.dto.FileInfoRequest;
import org.arch.ums.conf.dto.FileInfoSearchDto;
import org.arch.ums.conf.client.ConfFileInfoFeignService;
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
public class FileInfoController implements FeignCrudController<FileInfoSearchDto, java.lang.Long, FileInfoRequest, ConfFileInfoFeignService> {

    private final ConfFileInfoFeignService confFileInfoService;

    @Override
    public ConfFileInfoFeignService getFeignService() {
        return this.confFileInfoService;
    }

}
