package org.arch.application.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 用户证件相关信息接口
 */
@Api(tags = "用户证件相关")
@RestController
public class UserAttachmentController {

//    @Autowired
//    private IUserAttachmentBiz iUserAttachmentBiz;
//    /**
//     * 上传证件-用户身份证-正面
//     *
//     * @param file 上传文件
//     * @return 文件地址
//     */
//    @PostMapping(value = "/user/uploadIdCardFront", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ApiBaseResult uploadIdCardFront(MultipartFile file, HttpServletRequest request) {
////        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
////        MultipartFile file1 = multiRequest.getFile("file");
//        UserAttachmentDto userAttachmentDto = new UserAttachmentDto();
//        userAttachmentDto.setAttchmentTyp("正面");
//        userAttachmentDto.setAttachmentName("身份证");
//        return iUserAttachmentBiz.uploadUserCard(file, userAttachmentDto);
//    }
//    /**
//     * 上传证件-用户身份证-反面
//     *
//     * @param file 上传文件
//     * @return 文件地址
//     */
//    @PostMapping(value = "/user/uploadIdCardBack", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ApiBaseResult uploadIdCardBack(MultipartFile file) {
//        UserAttachmentDto userAttachmentDto = new UserAttachmentDto();
//        userAttachmentDto.setAttchmentTyp("反面");
//        userAttachmentDto.setAttachmentName("身份证");
//        return iUserAttachmentBiz.uploadUserCard(file, userAttachmentDto);
//    }
}
