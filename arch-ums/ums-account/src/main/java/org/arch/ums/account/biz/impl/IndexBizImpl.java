package org.arch.ums.account.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uni.common.entity.TokenInfo;
import com.uni.common.utils.SecurityUtils;
import com.uni.common.utils.StringUtils;
import com.uni.pay.entity.mybatis.PayBindedRecord;
import com.uni.pay.service.IPayBindedRecordService;
import org.arch.ums.account.biz.IIndexBiz;
import org.arch.ums.account.biz.IOrderBiz;
import org.arch.ums.account.vo.ApiBaseResult;
import com.uni.user.entity.UUserAttachment;
import com.uni.user.entity.UUserJob;
import com.uni.user.entity.UUserRelationship;
import com.uni.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 订单相关业务
 *
 * @author kenzhao
 * @date 2020/4/14 11:30
 */
@Service
@Slf4j
public class IndexBizImpl implements IIndexBiz {
    @Autowired
    private IPayBindedRecordService payBindedRecordService;
    @Autowired
    private IUUserJobService userJobService;
    @Autowired
    private IUUserRelationshipService userRelationshipService;
    @Autowired
    private IUUserAttachmentService userAttachmentService;
    @Autowired
    private IOrderBiz orderBiz;

    /**
     * 首页
     *
     * @return
     */
    @Override
    public ApiBaseResult index() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        //是否编辑个人资料-基本信息
        String userId = tokenInfo.getUserId();
        if (StringUtils.isEmpty(userId)) {
            //个人资料-基本信息
            return ApiBaseResult.success("JBXX");
        }

        ApiBaseResult result = orderBiz.getPayOrders();
        if (result != null && result.getStatus() == 200) {
            //查询贷款app产品信息
            return ApiBaseResult.success("DKAPP");
        }
        //是否编辑个人资料-工作信息
        UUserJob userJob = new UUserJob();
        userJob.setAppId(tokenInfo.getAppId());
        userJob.setUserId(userId);
        List userJobs = userJobService.list(new QueryWrapper<>(userJob));
        if (userJobs == null || userJobs.size() < 1) {
            return ApiBaseResult.success("GZXX");
        }
        //是否编辑个人资料-联系人信息
        UUserRelationship userRelationship = new UUserRelationship();
        userRelationship.setAppId(tokenInfo.getAppId());
        userRelationship.setUserId(userId);
        List userRelationships = userRelationshipService.list(new QueryWrapper<>(userRelationship));
        if (userRelationships == null || userRelationships.size() < 1) {
            return ApiBaseResult.success("LXXX");
        }
        //是否进行身份认证
        UUserAttachment userAttachment = new UUserAttachment();
        userAttachment.setAppId(tokenInfo.getAppId());
        userAttachment.setUserId(userId);
        List userAttachments = userAttachmentService.list(new QueryWrapper<>(userAttachment));
        if (userAttachments == null || userAttachments.size() < 2) {
            return ApiBaseResult.success("SFRZ");
        }
        //查看用户是否已绑卡
        PayBindedRecord bindedRecord = new PayBindedRecord();
        bindedRecord.setAppId(tokenInfo.getAppId());
        bindedRecord.setAccountId(tokenInfo.getAccountId());
        QueryWrapper<PayBindedRecord> queryWrapper = new QueryWrapper<>(bindedRecord);
        List<PayBindedRecord> payBindedRecords = payBindedRecordService.list(queryWrapper);
        if (payBindedRecords == null || payBindedRecords.size() < 1) {
            return ApiBaseResult.success("YHBK");
        }
        //立即拿钱
        return ApiBaseResult.success("LJNQ");
    }


}