package org.arch.ums.conf.rest;

import org.arch.framework.crud.CrudRest;
import org.arch.ums.conf.dto.MobileSegmentRequest;
import org.arch.ums.conf.dto.MobileSegmentSearchDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 手机号段信息(MobileSegment) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:56:34
 * @since 1.0.0
 */

@RestController
@RequestMapping("/conf/mobile/segment")
public interface MobileSegmentRest extends CrudRest<MobileSegmentRequest, java.lang.Long, MobileSegmentSearchDto> {
    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param requestList  实体的 request 类型列表
     * @return  {@link Boolean}
     */
    @PostMapping("/savesOnDuplicateKeyUpdate")
    Boolean insertOnDuplicateKeyUpdate(@Valid @RequestBody List<MobileSegmentRequest> requestList);

    /**
     * 批量上传手机号段信息. <br>
     * 格式: 1. 格式: 1345   CMCC	0 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机号段信息
     * @param delimiter csv 行格式分隔符
     * @return {@link Boolean}
     */
    @PostMapping(value = "/uploadSegments", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Boolean addMobileSegment(@RequestParam("file") MultipartFile file, @RequestParam("delimiter") String delimiter) throws IOException;
}

