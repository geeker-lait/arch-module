package org.arch.application.user.config;//package com.lmt.mbsp.cms.admin.application.config;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.core.MethodParameter;
//import org.springframework.util.Assert;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Param
// * @return
// * @Description 返回时对结果做一些处理
// */
//public class ReturnHandler implements HandlerMethodReturnValueHandler {
//
//    /**
//     * 该处理程序是否支持给定的方法返回类型(只有返回true才回去调用handleReturnValue)
//     */
//    @Override
//    public boolean supportsReturnType(MethodParameter methodParameter) {
//        return methodParameter.getParameterType() == SessionContext.class;
//    }
//
//    /**
//     * 处理给定的返回值
//     * 通过向 ModelAndViewContainer 添加属性和设置视图或者
//     * 通过调用 ModelAndViewContainer.setRequestHandled(true) 来标识响应已经被直接处理(不再调用视图解析器)
//     */
//    @Override
//    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
//        Assert.isInstanceOf(SessionContext.class, o);
//        SessionContext sessionContext=((SessionContext)o);
//        sessionContext.setValid(true);
//        /**
//         * 标识请求是否已经在该方法内完成处理
//         */
//        modelAndViewContainer.setRequestHandled(true);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().append(JSON.toJSONString(sessionContext)).flush();
//    }
//}
