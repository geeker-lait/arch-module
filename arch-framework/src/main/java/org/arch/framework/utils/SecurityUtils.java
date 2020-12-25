//package org.arch.framework.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
///**
// * 获取当前登录的用户
// */
//@Slf4j
//public class SecurityUtils {
//
//    /**
//     * 获取当前登录的账户信息
//     *
//     * @return
//     */
//    public static TokenInfo getCurrentUser() {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
//        }
//        if (authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return GsonUtils.fromJson(userDetails.getUsername(), TokenInfo.class);
//        }
//        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
//    }
//
//
//
//    /**
//     * 获取系统用户名称
//     * @return 系统用户名称
//     */
//    public static String getAccountName() {
//        TokenInfo tokenInfo = getCurrentUser();
//        return tokenInfo.getAccountName();
//    }
//
//    /**
//     * 获取系统用户ID
//     *
//     * @return 系统用户ID
//     */
//    public static String getCurrentUserId() {
//        TokenInfo tokenInfo = getCurrentUser();
//        return tokenInfo.getUserId();
//    }
//
//
//
//}
