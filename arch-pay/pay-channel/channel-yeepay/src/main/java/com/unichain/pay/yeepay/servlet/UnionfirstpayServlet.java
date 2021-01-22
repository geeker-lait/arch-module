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


public class UnionfirstpayServlet extends HttpServlet {
    String unionfirstpayUri = Config.getInstance().getValue("unionfirstpayUri");

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
        String identityid = format(request.getParameter("identityid"));
        String identitytype = format(request.getParameter("identitytype"));
        String cardno = format(request.getParameter("cardno"));
        String idcardno = format(request.getParameter("idcardno"));
        String idcardtype = format(request.getParameter("idcardtype"));
        String username = format(request.getParameter("username"));
        String phone = format(request.getParameter("phone"));
        String amount = format(request.getParameter("amount"));
        String authtype = format(request.getParameter("authtype"));
        String issms = format(request.getParameter("issms"));
        String advicesmstype = format(request.getParameter("advicesmstype"));
        String smstempldatemsg = format(request.getParameter("smstempldatemsg"));
        String smstemplateid = format(request.getParameter("smstemplateid"));
        String avaliabletime = format(request.getParameter("avaliabletime"));
        String callbackurl = format(request.getParameter("callbackurl"));
        String requesttime = format(request.getParameter("requesttime"));
        String productname = format(request.getParameter("productname"));
        String terminalno = format(request.getParameter("terminalno"));
        String dividecallbackurl = format(request.getParameter("dividecallbackurl"));
        String newdividejstr = format(request.getParameter("newdividejstr"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("requestno", requestno);
        map.put("identityid", identityid);
        map.put("identitytype", identitytype);
        map.put("cardno", cardno);
        map.put("idcardno", idcardno);
        map.put("idcardtype", idcardtype);
        map.put("username", username);
        map.put("phone", phone);
        map.put("amount", amount);
        map.put("authtype", authtype);
        map.put("issms", issms);
        map.put("advicesmstype", advicesmstype);
        map.put("smstempldatemsg", smstempldatemsg);
        map.put("smstemplateid", smstemplateid);
        map.put("avaliabletime", avaliabletime);
        map.put("callbackurl", callbackurl);
        map.put("requesttime", requesttime);
        map.put("productname", productname);
        map.put("terminalno", terminalno);
        map.put("dividecallbackurl", dividecallbackurl);
        map.put("newdividejstr", newdividejstr);

        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, unionfirstpayUri);


        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/31unionfirstpayResponse.jsp");
        view.forward(request, response);


    }
}




