package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.StudentDAO;
import com.sist.dao.StudentVO;


@WebServlet("/StudentInsert")
public class StudentInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//화면 출력 (폼)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.print("<html>");
		out.print("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.print("<style>");
		out.print(".container{margin-top:50px}");
		out.print(".row{margin:0px auto;width:350px;}");
		out.print("h1{text-align:center}");
		out.print("</style>");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class=container>");
		out.print("<h1>학생 등록</h1>");
		out.print("<div class=row>");
		out.println("<form method=post action=StudentInsert>");
		//post => doPost
		// 나머지는 doGet 
		/*
		 * 	<form method=get> doGet()
			<form method=aaa> doGet()
			<form> doGet()
			if(method.equals("post")||method.equals("POST"))
				doPost()
			else
				doGet()
		 * 
		 */
		out.print("<table class=table>");
		out.print("<tr>");
		out.print("<td width=35%>이름</td>");
		out.print("<td width=65%>");
		out.print("<input type=text name=name size=20 class=input-sm>");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td width=35%>국어</td>");
		out.print("<td width=65%>");
		out.print("<input type=number name=kor size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td width=35%>영어</td>");
		out.print("<td width=65%>");
		out.print("<input type=number name=eng size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td width=35%>수학</td>");
		out.print("<td width=65%>");
		out.print("<input type=number name=math size=20 class=input-sm max=100 min=0 step=5 value=50>");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td colspan=2 class=text-center>");
		out.print("<input type=submit value=등록 class=\"btn btn-sm btn-warning\">&nbsp;");
		out.print("<input type=button value=취소 class=\"btn btn-sm btn-warning\" onclick=\"javascript:history.back()\">");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("</table>");
		out.print("</form>"); //호출 => submit버튼 클릭시 
		out.print("</div>");
		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
	}

	// doGet에서 입력된 데이터를 받아서 데이터 추가 (기능)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 한글 => 디코딩
		// 사용자가 보내준 값을 받는다 
		//<input type=text name=name> => name속성을 설정하지 않으면 null값을 출력한다 
		// servlet에서만 적용되는 것이 아니라 jsp/spring포함 
		//								  ------- servlet 으로
		String name=request.getParameter("name");
		String kor=request.getParameter("kor");
		String eng=request.getParameter("eng");
		String math=request.getParameter("math");
		//public String insert(StudentVO vo)
		StudentVO vo=new StudentVO();
		vo.setName(name);
		vo.setKor(Integer.parseInt(kor));
		vo.setEng(Integer.parseInt(eng));
		vo.setMath(Integer.parseInt(math));
		
		StudentDAO dao=StudentDAO.newInstance();
		dao.studentInsert(vo);
		
		// insert 후 이동
		response.sendRedirect("StudentList");
		
	}

}
