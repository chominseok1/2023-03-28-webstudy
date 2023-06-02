package com.sist.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * 	WEB
 * 	  java => jsp/servlet (Spring)
 * 	  c# => aspx / asp
 * 	  python =>django
 * 	  php
 * 	  nodejs
 * 	  ------------------------------- request,response,cookie,session
 * 	  브라우저 => HTML만 인식
 * 			   Java => 일반 텍스트
 */

@WebServlet("/SeoulDetailServlet")
public class SeoulDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
