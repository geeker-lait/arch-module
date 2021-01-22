package com.unichain.pay.yeepay.servlet;

import com.unichain.pay.yeepay.config.Config;
import com.unichain.pay.yeepay.service.YeepayService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class BatchpayqueryServlet extends HttpServlet {
    String batchpayqueryUri = Config.getInstance().getValue("batchpayqueryUri");

    public static String format(String text) {
        return text != null ? text.trim() : "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("html/text");
        PrintWriter pw = response.getWriter();

        String merchantno = Config.getInstance().getValue("merchantno");
        String merchantbatchno = format(request.getParameter("merchantbatchno"));
        String ybbatchno = format(request.getParameter("ybbatchno"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("merchantbatchno", merchantbatchno);
        map.put("ybbatchno", ybbatchno);

        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, batchpayqueryUri);


        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/42batchpayqueryresponse.jsp");
        view.forward(request, response);
    }

}
