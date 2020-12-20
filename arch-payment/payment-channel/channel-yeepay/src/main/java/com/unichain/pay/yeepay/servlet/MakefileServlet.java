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


public class MakefileServlet extends HttpServlet {
    String requestUri = null;
    String authfileUri = Config.getInstance().getValue("authfileUri");
    String payfileUri = Config.getInstance().getValue("payfileUri");
    String refundfileUri = Config.getInstance().getValue("refundfileUri");
    String splitfileUri = Config.getInstance().getValue("splitfileUri");

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
        String startdate = format(request.getParameter("startdate"));
        String enddate = format(request.getParameter("enddate"));
        String orderType = format(request.getParameter("orderType"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantno", merchantno);
        map.put("startdate", startdate);
        map.put("enddate", enddate);

        //针对选择的文件类型不同对应不同的地址
        if (orderType.equals("pay")) {
            requestUri = payfileUri;
        } else if (orderType.equals("refund")) {
            requestUri = refundfileUri;
        } else if (orderType.equals("divide")) {
            requestUri = splitfileUri;
        } else if (orderType.equals("authentic")) {
            requestUri = authfileUri;
        }


        Map<String, String> yopresponsemap = YeepayService.yeepayYOP(map, requestUri);


        request.setAttribute("yopresponsemap", yopresponsemap == null ? "系统异常" : yopresponsemap);
        RequestDispatcher view = request.getRequestDispatcher("/jsp/51makefileResponse.jsp");
        view.forward(request, response);
    }


	/*public static String DownloadFile(Map<String, String> yopresponsemap, String path) {

		String time					= String.valueOf(System.currentTimeMillis());
		String fileName				= "SQKK" + "_" + time + "." + "txt";
		String filePath				= path + File.separator + fileName;


		String	resultdata	=yopresponsemap.get("resultdata") ;

		try {
			File file	= new File(filePath);
			file.getParentFile().mkdirs();
			file.createNewFile();

			FileOutputStream orderFile 	= new FileOutputStream(filePath);
			byte bytes[]=new byte[1024];
            bytes=resultdata.getBytes();
            int b=bytes.length;   //是字节的长度，不是字符串的长度

            orderFile.write(bytes,0,b);
            orderFile.write(bytes);
            orderFile.close();

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return filePath;
	}*/
}







