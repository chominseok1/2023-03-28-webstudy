package com.sist.model;

import javax.servlet.http.HttpServletRequest;
/*
 * 		사용자 요청 =====> Contoller ========> Model을 찾는다 
 * 											 |
 * 											요청 처리
 * 							|				------ 결과를 request, session에 담는다
 * 						request,session을 전송을 받는다
 * 							|
 * 						   jsp를 찾는다
 * 							|
 * 						   jsp에 request, session 전송  
 */
public class ListModel {
	public String execute(HttpServletRequest request)
	 {
		 request.setAttribute("msg", "목록출력하기");
		 return "list.jsp";
	 }
}
