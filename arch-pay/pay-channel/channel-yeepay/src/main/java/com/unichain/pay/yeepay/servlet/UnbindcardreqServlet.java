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

public class UnbindcardreqServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String UnbindcardUri = Config.getInstance().getValue("UnbindcardUri");

    public static String format(String text) {
        return text == null ? "" : text.trim();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        super.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");


        String merchantno = Config.getInstance().getValue("merchantno");
        String requestno = format(request.getParameter("requestno"));
        String identityid = format(request.getParameter("identityid"));
        String identitytype = format(request.getParameter("identitytype"));
        String cardtop = format(request.getParameter("cardtop"));
        String cardlast = format(request.getParameter("cardlast"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("requestno", requestno);
        map.put("identityid", identityid);
        map.put("identitytype", identitytype);
        map.put("cardtop", cardtop);
        map.put("cardlast", cardlast);

        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, UnbindcardUri);


        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/17Unbindcardreqesponse.jsp");
        view.forward(request, response);

    }
}
