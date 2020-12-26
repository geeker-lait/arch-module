package org.arch.application.user.controller;

import org.arch.ums.account.biz.IUserAttachmentBiz;
import org.arch.ums.account.dto.UserAttachmentDto;
import org.arch.ums.account.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 用户证件相关信息接口
 *
 * @author kenzhao
 * @date 2020/3/31 15:30
 */
@Api(tags = "用户证件相关")
@RestController
public class UserAttachmentController {

    @Autowired
    private IUserAttachmentBiz iUserAttachmentBiz;
    /**
     * 上传证件-用户身份证-正面
     *
     * @param file 上传文件
     * @return 文件地址
     */
    @PostMapping(value = "/user/uploadIdCardFront", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiBaseResult uploadIdCardFront(MultipartFile file, HttpServletRequest request) {
//        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file1 = multiRequest.getFile("file");
        UserAttachmentDto userAttachmentDto = new UserAttachmentDto();
        userAttachmentDto.setAttchmentTyp("正面");
        userAttachmentDto.setAttachmentName("身份证");
        return iUserAttachmentBiz.uploadUserCard(file, userAttachmentDto);
    }
    /**
     * 上传证件-用户身份证-反面
     *
     * @param file 上传文件
     * @return 文件地址
     */
    @PostMapping(value = "/user/uploadIdCardBack", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiBaseResult uploadIdCardBack(MultipartFile file) {
        UserAttachmentDto userAttachmentDto = new UserAttachmentDto();
        userAttachmentDto.setAttchmentTyp("反面");
        userAttachmentDto.setAttachmentName("身份证");
        return iUserAttachmentBiz.uploadUserCard(file, userAttachmentDto);
    }
}