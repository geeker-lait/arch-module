package org.arch.alarm.biz.notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 6:33 PM
 */
@Slf4j
public abstract class AbstractMsgNotifier {
//    @Autowired
//    protected UserCommunicationService userCommunicationService;
//
////    @Autowired
////    protected ShopService shopService;
//
//    protected UserCommunication getUserCommunication(String storeNo, Long uid) {
//
//        /**
//         * todo ums 领域提供服务，根据用户id 获取用户的可用的通讯信息（手机号，微信号，邮箱等.....）
//         * FUmsService.getUserCommunication(uid);
//         * 模拟查ums 或数据库，获取用户通信数据
//         */
//        Predicate<UserCommunication> predicate = u -> u.getUserId() == uid;
//        predicate.and(u -> u.getUserTyp() == UserTyp.USER.getTyp());
//        if (null != storeNo) {
//            // 如果有传递storeNo 就根据StoreNo 查询相关联的用户
//            predicate.and(u -> u.getStoreNo().equals(storeNo));
//        }
//        return userCommunicationService.getUserCommunication(storeNo, uid, UserTyp.USER).stream()
//                .filter(predicate).findAny().orElse(null);
//    }
//
//    protected List<UserCommunication> getUserGroupCommunication(String storeNo, Long uid) {
//
//        /**
//         * todo ums 领域提供服务，根据用户id 获取用户的可用的通讯信息（手机号，微信号，邮箱等.....）
//         * FUmsService.getUserCommunication(uid);
//         * 这里需要处理，根据组id 再去查询所有的组用户
//         */
//        Predicate<UserCommunication> predicate = u -> u.getUserId() == uid;
//        predicate.and(u -> u.getUserTyp() == UserTyp.USER.getTyp());
//        if (null != storeNo) {
//            predicate.and(u -> u.getStoreNo().equals(storeNo));
//        }
//        return userCommunicationService.getUserCommunication(storeNo, uid, UserTyp.GROUP).stream()
//                .filter(predicate).collect(Collectors.toList());
//    }

}
