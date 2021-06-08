package org.arch.ums.conf.rest;

import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudRest;
import org.arch.ums.conf.dto.MobileInfoRequest;
import org.arch.ums.conf.dto.MobileInfoSearchDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 手机号归属地信息(MobileInfo) rest 层
 *
 * @author zheng
 * @date 2021-06-06 11:56:28
 * @since 1.0.0
 */

@RestController
@RequestMapping("/conf/mobile/info")
public interface MobileInfoRest extends CrudRest<MobileInfoRequest, java.lang.Long, MobileInfoSearchDto> {
    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     *
     * @param requestList 实体的 request 类型列表
     * @return {@link Response (Boolean)}
     */
    @PostMapping("/savesOnDuplicateKeyUpdate")
    Boolean insertOnDuplicateKeyUpdate(@Valid @RequestBody List<MobileInfoRequest> requestList) throws SQLException;

    /**
     * 批量上传手机归属地信息.  注意: 必须拥有 ROLE_ADMIN 角色才能上传.<br>
     * 格式: 1999562  甘肃-兰州 <br>
     * 分隔符可以自定义.
     *
     * @param file      csv 格式的手机归属地信息
     * @param delimiter csv 行格式分隔符
     * @return {@link Response}
     */
    @PostMapping(value = "/uploadInfos", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Object addMobileInfo(@RequestParam("file") MultipartFile file,
                         @RequestParam("delimiter") String delimiter) throws SQLException, IOException;

}

