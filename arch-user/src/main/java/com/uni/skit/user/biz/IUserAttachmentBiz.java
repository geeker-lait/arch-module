package com.uni.skit.user.biz;

import com.uni.skit.user.dto.UserAttachmentDto;
import com.uni.skit.user.vo.ApiBaseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: 用户证件相关上传
 *
 * @author kenzhao
 * @date 2020/3/31 15:34
 */
public interface IUserAttachmentBiz {

    /**
     * 上传用户身份证信息
     * @param file
     * @param userAttachmentDto
     * @return
     */
    ApiBaseResult uploadUserCard(MultipartFile file, UserAttachmentDto userAttachmentDto);
}