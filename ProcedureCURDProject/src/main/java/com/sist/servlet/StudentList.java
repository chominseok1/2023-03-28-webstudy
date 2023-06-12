package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
//JDBC (원시소스) => DBCP (속도의 최적화) = 1차
// ORM (MyBatis(2차) , JPA (3차) , Hibernate)
// 연습용 => 오라클 사용(X) => MySQL, MariaDB
// Spring
@WebServlet("/StudentList")
public class StudentList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		StudentDAO dao=StudentDAO.newInstance();
		List<StudentVO> list=dao.studentListData();
		out.print("<html>");
		out.print("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.print("<style>");
		out.print(".container{margin-top:50px}");
		out.print(".row{margin:0px auto;width:700px;}");
		out.print("h1{text-align:center}");
		out.print("</style>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class=container>");
		out.print("<h1>학생목록</h1>");
		out.print("<div class=row>");
		out.print("<table class=table>");
		out.print("<tr>");
		out.print("<td>");
		out.print("<a href=StudentInsert class=\"btn btn-sm btn-primary\">등록</a>");
		out.print("</td>");
		out.print("</tr>");
		out.print("</table>");
		out.print("<table class=table>");
		out.print("<tr class=danger>");
		out.print("<th class=text-center>학번</th>");
		out.print("<th class=text-center>이름</th>");
		out.print("<th class=text-center>국어</th>");
		out.print("<th class=text-center>영어</th>");
		out.print("<th class=text-center>수학</th>");
		out.print("<th class=text-center>총점</th>");
		out.print("<th class=text-center>평균</th>");
		out.print("<th class=text-center></th>");
		out.print("</tr>");
		for(StudentVO vo:list)
		{
			out.print("<tr>");
			out.print("<td class=text-center>"+vo.getHakbun()+"</td>");
			out.print("<td class=text-center>"+vo.getName()+"</td>");
			out.print("<td class=text-center>"+vo.getKor()+"</td>");
			out.print("<td class=text-center>"+vo.getEng()+"</td>");
			out.print("<td class=text-center>"+vo.getMath()+"</td>");
			out.print("<td class=text-center>"+vo.getTotal()+"</td>");
			out.print("<td class=text-center>"+vo.getAvg()+"</td>");
			out.print("<td class=text-center>");
			out.print("<a href=StudentUpdate?hakbun="+vo.getHakbun()+" class=\"btn btn-sm btn-primary\">수정</a>&nbsp;");
			out.print("<a href=StudentDelete?hakbun="+vo.getHakbun()+" class=\"btn btn-sm btn-info\">삭제</a>");
			out.print("</td>");
			out.print("</tr>");
		}
		out.print("</table>");
		out.print("</div>");
		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
	}

}
