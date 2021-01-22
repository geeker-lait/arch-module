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

public class AuthbindcardresendServlet extends HttpServlet {

    // 鉴权绑卡重发
    String authbindcardresendUri = Config.getInstance().getValue("authbindcardresendUri");

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
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        String merchantno = Config.getInstance().getValue("merchantno");
        String requestno = format(request.getParameter("requestno"));
        String advicesmstype = format(request.getParameter("advicesmstype"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("requestno", requestno);
        map.put("advicesmstype", advicesmstype);

        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, authbindcardresendUri);

        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/13authbindcardresendResponse.jsp");
        view.forward(request, response);

    }
}
