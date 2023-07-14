package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;

public class RecommandModel {
	@RequestMapping("weather/weather.do")

	public String today_weather(HttpServletRequest request, HttpServletResponse response) {

		try {
			Document doc = Jsoup.connect("https://korean.visitseoul.net/weather").get();
			Element section = doc.selectFirst("section#content");
			String html = "<section id=\"content\">";			
			html = section.html();
			html += "</section>";	
			//<img src="/resources/theme/images/weather/img-air01.png?param=1" alt="미세먼지좋음">
			html = html.replace("src=\"", "src=\"https://korean.visitseoul.net");
			request.setAttribute("html", html);
			System.out.println(html);
		} catch (Exception e) {}
		request.setAttribute("main_jsp", "../weather/weather.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
}


