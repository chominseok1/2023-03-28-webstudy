package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;


@WebServlet("/SeoulNatureServlet")
public class SeoulNatureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전송 방식 -> 브라우저로 보낸다 (미리 알려준다)

		response.setContentType("text/html;charset=UTF-8");

		//html = > text/html  , xml보낼땐 =>  text/xml , json => text/plain

		// HTML을 저장 => 브라우저에 읽어가는 위치에 저장

		PrintWriter out = response.getWriter();

		//				=> 사용자의 브라우저

		

		//데이터베이스 연결

		SeoulDAO dao = SeoulDAO.newInstance();

		List<SeoulVO> list = dao.seoulNatureList(1);  // 카테고리 정보를 오라클로부터 받아온다 30개 들어있음 //1page요청

		out.println("<html>");

		out.println("<head>");

		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");

		out.println("<style>");

		out.println(".container{margin-top:50px}");

		out.println(".row{");

		out.println("margin:0px auto;");

		out.println("width:1024px}</style>");

		

		out.println("</head>");

		out.println("<body>");

		out.println("<div class=container>");

		

		out.println("<h1>서울 nature</h1>");

		out.println("<div class=row>");

		

		for(SeoulVO vo:list) {	//12개 뿔여줌

			


			//그림 하나 들어가는 부분

			out.println("<div class=\"col-md-3\">");	//한줄에 4개 출력 >> 12/3

			out.println("<div class=\"thumbnail\">");

			out.println("<a href=\"#\">");	//카테고리의 번호에 해당하는

			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:280%;height:250px\">");

			out.println("<div class=\"caption\">");

			out.println("<p style=\"font-size:9px\">"+vo.getTitle()+"</p>");

			out.println("</div>");

			out.println("</a>");

			out.println("</div>");

			out.println("</div>");

		}
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
