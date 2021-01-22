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


public class UnibindcardpayServlet extends HttpServlet {
    String unibindcardpayUri = Config.getInstance().getValue("unibindcardpayUri");

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
        String issms = format(request.getParameter("issms"));
        String identityid = format(request.getParameter("identityid"));
        String identitytype = format(request.getParameter("identitytype"));
        String cardtop = format(request.getParameter("cardtop"));
        String cardlast = format(request.getParameter("cardlast"));
        String amount = format(request.getParameter("amount"));
        String advicesmstype = format(request.getParameter("advicesmstype"));
        String avaliabletime = format(request.getParameter("avaliabletime"));
        String productname = format(request.getParameter("productname"));
        String callbackurl = format(request.getParameter("callbackurl"));
        String requesttime = format(request.getParameter("requesttime"));
        String terminalno = format(request.getParameter("terminalno"));
        String remark = format(request.getParameter("remark"));
        String extinfos = format(request.getParameter("extinfos"));
        String dividecallbackurl = format(request.getParameter("dividecallbackurl"));
        String dividejstr = format(request.getParameter("dividejstr"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("requestno", requestno);
        map.put("issms", issms);
        map.put("identityid", identityid);
        map.put("identitytype", identitytype);
        map.put("cardtop", cardtop);
        map.put("cardlast", cardlast);
        map.put("amount", amount);
        map.put("advicesmstype", advicesmstype);
        map.put("avaliabletime", avaliabletime);
        map.put("productname", productname);
        map.put("callbackurl", callbackurl);
        map.put("requesttime", requesttime);
        map.put("terminalno", terminalno);
        map.put("remark", remark);
        map.put("extinfos", extinfos);
        map.put("dividecallbackurl", dividecallbackurl);
        map.put("dividejstr", dividejstr);

        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, unibindcardpayUri);


        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/21unibindcardpayResponse.jsp");
        view.forward(request, response);


    }
}


