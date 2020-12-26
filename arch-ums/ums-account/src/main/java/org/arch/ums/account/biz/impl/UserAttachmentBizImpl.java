package org.arch.ums.account.biz.impl;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.uni.common.entity.TokenInfo;
import com.uni.common.exception.BizException;
import com.uni.common.utils.GsonUtils;
import com.uni.common.utils.SecurityUtils;
import com.uni.common.utils.StringUtils;
import org.arch.ums.account.biz.IUserAttachmentBiz;
import org.arch.ums.account.dto.UserAttachmentDto;
import org.arch.ums.account.vo.ApiBaseResult;
import com.uni.user.entity.UUserAttachment;
import com.uni.user.service.IUUserAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2020/3/31 15:38
 */
@Slf4j
@Service
public class UserAttachmentBizImpl implements IUserAttachmentBiz {
    @Value("${baidubos.access_key_id}")
    private String ACCESS_KEY_ID;
    @Value("${baidubos.secret_access_key}")
    private String SECRET_ACCESS_KEY;
    @Value("${baidubos.endpoint}")
    private String ENDPOINT;
    @Value("${baidubos.bucketName.public}")
    private String YUESHANG_SKIT_PUBLIC;
    @Value("${baidubos.bucketName.private}")
    private String YUESHANG_SKIT;
    @Autowired
    private IUUserAttachmentService userAttachmentService;
    /**
     * 上传文件信息
     *
     * @param file
     * @return
     */
    public ApiBaseResult uploadFile(MultipartFile file,String keyCode) {
        if (file == null) {
            throw new BizException("证件不可为空");
        }
        // 非图片
        String contentType = file.getContentType();
        if (contentType != null && !contentType.contains("image")) {
            throw new BizException("证件类型出错");
        }
        // 大小判断
        long size = file.getSize();
        if (size < 1) {
            throw new BizException("请选择上传的文件");
        }
        try {
            byte[] bytes = file.getBytes();
            BosClientConfiguration config = new BosClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
            config.setEndpoint(ENDPOINT);
            BosClient client = new BosClient(config);
            client.putObject(YUESHANG_SKIT, keyCode,bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiBaseResult.error("请稍后重试");
        }
        return ApiBaseResult.success();
    }

    /**
     * 上传用户身份证信息
     *
     * @param file
     * @param userAttachmentDto
     * @return
     */
    @Override
    public ApiBaseResult uploadUserCard(MultipartFile file, UserAttachmentDto userAttachmentDto) {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String userId = tokenInfo.getUserId();
        String appId = tokenInfo.getAppId();
        String appCode = tokenInfo.getAppCode();
        if (StringUtils.isEmpty(userId)) {
            return ApiBaseResult.error("获取用户信息失败请重新登陆");
        }
        String keyCode = String.format("image/%s/%s/%s/%s", appId, userAttachmentDto.getAttachmentName(), userId, userAttachmentDto.getAttchmentTyp());;
        //上传文件
        ApiBaseResult apiBaseResult = uploadFile(file,keyCode);
        //添加记录
        String userAttachmentDtoStr = GsonUtils.toJson(userAttachmentDto);
        UUserAttachment userAttachment = GsonUtils.fromJson(userAttachmentDtoStr,UUserAttachment.class);

        userAttachment.setUserId(userId);
        userAttachment.setAppId(appId);
        userAttachment.setAppCode(appCode);
        userAttachmentService.saveOrUpdate(userAttachment);
        return apiBaseResult;
    }
}