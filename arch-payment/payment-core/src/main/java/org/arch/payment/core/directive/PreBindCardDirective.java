package org.arch.payment.core.directive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.arch.payment.core.DirectiveExecutable;
import org.arch.payment.core.entity.PayAppMerchantChannel;
import org.arch.payment.core.entity.PayDirective;
import org.arch.payment.core.entity.PayMerchantChannel;
import org.arch.payment.core.service.*;
import org.arch.payment.sdk.DirectiveRouting;
import org.arch.payment.sdk.PayConfigurable;
import org.arch.payment.sdk.PayRequest;
import org.arch.payment.sdk.PayResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/24/2019
 * @Description ${Description}
 */
@Service
public class PreBindCardDirective implements DirectiveRouting, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private IPayBindedRecordService bindedRecordervice;

    @Autowired
    private IPayAppMerchantChannelService appMerchantChannelService;

    @Autowired
    private IPayMerchantChannelService merchantChannelService;

    @Autowired
    private IPayChannelBankService channelBankService;
    @Autowired
    private IPayDirectiveService directiveService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ChuangLanSmsService chuangLanSmsService;

    @Override
    public PayResponse routing(PayRequest payRequest) {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        PayResponse payResponse = new PayResponse();
        // 0、判断有无绑卡，如果已经绑定模拟发送验证码，让用户继续往下走支付流程
        if (bindedRecordervice.hasBindedCard(payRequest)) {
            //生成验证码
            String accountCode = RandomStringUtils.random(4, "0123456789");
            // 保存
            //调用短信，发送
            try {
                chuangLanSmsService.sendSms(accountCode, tokenInfo.getAccountName());
            } catch (Exception e) {
                throw new BizException("短信发送失败，请重试");
            }
            payResponse.setHasBindCard(true);
            // 先返回
            return payResponse;

        }
        // 1、根据appId 获取商户应用通道集合，并按权重从高到低排序
        List<PayAppMerchantChannel> appMerchantChannels = appMerchantChannelService.getSortedAppMerchantChannelsByAppId(payRequest.getAppId());
        // 借助工具类获取bankcode
        String bankCode = BankCardNoUtil.getBankCardNo(payRequest.getBankcard());
        if (bankCode == null) {
            throw new BizException("银行卡输入错误");
        }
        // 2、根据银行卡获取支持通道ID集合，并按权重从高到低排序
        List<String> bankChannelIdList = channelBankService.getSortedChannelIdListByBankcard(bankCode);
        if (bankChannelIdList == null || bankChannelIdList.size() < 1) {
            throw new BizException("该银行卡通道暂不支持");
        }

        // 3、根据1和2步骤中的条件，选择支持当前银行卡的商户通道集合
        List<PayMerchantChannel> merchantChannelList = selectMerchantChannel(appMerchantChannels, bankChannelIdList);
        if (merchantChannelList == null || merchantChannelList.size() < 1) {
            throw new BizException("该银行卡商户通道暂不支持");
        }
        // 3.1、递归调用
        payResponse = recursion(null, merchantChannelList, payRequest);
        return payResponse;
    }


    /**
     * 递归商户通道
     *
     * @param merchantChannelList
     * @return
     */
    public PayResponse recursion(PayMerchantChannel merchantChannel, List<PayMerchantChannel> merchantChannelList, PayRequest payRequest) {

        if (merchantChannel == null && merchantChannelList.size() > 0) {
            merchantChannel = merchantChannelList.get(0);
        }

        // 4、根据3选择出来的商户通道的ID和指令名称获取具体的指令

        PayDirective directive = new PayDirective();
        directive.setChannelId(merchantChannel.getChannelId());
        directive.setName(payRequest.getDirectiveName().name());
        directive = directiveService.getOne(new QueryWrapper<>(directive));
        // 5、根据商户通道和指令创建通道参数配置对象
        PayConfigurable payConfigurable = buildChannelConfig(merchantChannel, directive);

        // 6、根据具体指令CODE获取具体的支付指令对象
        DirectiveExecutable payDirective = applicationContext.getBean(directive.getCode(), DirectiveExecutable.class);
        // 7、执行
        // 创建指令参数
        //PayParam payParam = payDirective.buildPayParams(payRequest);
        //SzsharelinkChannelDirecvite channelDirective = GsonUtils.fromJson(GsonUtils.toJson(merchantChannel),SzsharelinkChannelDirecvite.class);
        //channelDirective.setDirectiveUri(directive.getUri());
        //payRequest.setChannelId(merchantChannel.getChannelId());
        PayResponse response = payDirective.exec(payRequest, payConfigurable);
        //JSONObject jsonObject = JSONUtil.parseObj(JSONUtil.toJsonStr(response.getT()));
        //PayResult payResult = GsonUtils.fromJson(GsonUtils.toJson(response.getT()),PayResult.class);
        // 不是预留手机号
        if (response == null || !"1000000".equals(response.getCode())) {
            int i = merchantChannelList.indexOf(merchantChannel);
            if (i+1 >= merchantChannelList.size()) {
                return response;
            }
            // 获取下一个去执行
            merchantChannel = merchantChannelList.get(i + 1);
            recursion(merchantChannel, merchantChannelList, payRequest);
        }
        // 8、返回执行结果
        return response;
    }

    private List<PayMerchantChannel> selectMerchantChannel(List<PayAppMerchantChannel> merchantAppChannelList, List<String> bankChannelIdList) {
        List<PayMerchantChannel> merchantChannelList = new ArrayList<>();
        for (PayAppMerchantChannel merchantAppChannel : merchantAppChannelList) {
            if(bankChannelIdList.contains(merchantAppChannel.getChannelId())){
                PayMerchantChannel merchantChannel = new PayMerchantChannel();
                merchantChannel.setChannelId(merchantAppChannel.getChannelId());
                merchantChannel.setMerchantId(merchantAppChannel.getMerchantId());
                merchantChannel.setAppId(merchantAppChannel.getAppId());
                merchantChannel = merchantChannelService.getOne(new QueryWrapper<>(merchantChannel));
                merchantChannelList.add(merchantChannel);
            }
        }
        return merchantChannelList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
