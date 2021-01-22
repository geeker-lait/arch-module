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


public class BindcardconfirmServlet extends HttpServlet {
    String bindcardconfirmUri = Config.getInstance().getValue("bindcardconfirmUri");

    public static String format(String text) {
        return text == null ? "" : text.trim();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String merchantno = Config.getInstance().getValue("merchantno");
        String requestno = format(request.getParameter("requestno"));
        String validatecode = format(request.getParameter("validatecode"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("requestno", requestno);
        map.put("validatecode", validatecode);


        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, bindcardconfirmUri);


        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/22bindcardconfirmResponse.jsp");
        view.forward(request, response);


    }
}


