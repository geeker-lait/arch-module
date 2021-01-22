package com.unichain.pay.yeepay.servlet;

import com.unichain.pay.yeepay.config.Config;
import com.unichain.pay.yeepay.service.YeepayService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TopupServlet extends HttpServlet {

    // 线下汇入接口
    String authbindcardconfirmUri = Config.getInstance().getValue("topupServlet");

    public static String format(String text) {
        return text == null ? "" : text.trim();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String merchantNo = Config.getInstance().getValue("merchantno");
        String orderId = format(request.getParameter("orderId"));
        String orderAmount = format(request.getParameter("orderAmount"));
        String bizLoadType = "ACCOUNT";
        String payType = "OFFIMPORT";
        String notifyUrl = format(request.getParameter("notifyUrl"));
        String bankId = format(request.getParameter("bankId"));
        String payerCardNo = format(request.getParameter("payerCardNo"));
        String payerCardName = format(request.getParameter("payerCardName"));
        String receiveBankCode = "ICBC";
        String receiveCardNo = "4000021119200988479";
        String receiveDate = format(request.getParameter("receiveDate"));
        String remark = format(request.getParameter("remark"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantNo", merchantNo);
        map.put("orderId", orderId);
        map.put("orderAmount", orderAmount);
        map.put("bizLoadType", bizLoadType);
        map.put("payType", payType);
        map.put("notifyUrl", notifyUrl);
        map.put("bankId", bankId);
        map.put("payerCardNo", payerCardNo);
        map.put("payerCardName", payerCardName);
        map.put("receiveBankCode", receiveBankCode);
        map.put("receiveCardNo", receiveCardNo);
        map.put("receiveDate", receiveDate);
        map.put("remark", remark);

        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, authbindcardconfirmUri);

        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/72topupResponse.jsp");
        view.forward(request, response);

    }
}
