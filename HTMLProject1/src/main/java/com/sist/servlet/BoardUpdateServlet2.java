package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
@WebServlet("/BoardUpdateServlet2")
public class BoardUpdateServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송 방식
		response.setContentType("text/html;charset=UTF-8");
		// 클라이언트가 보낸값을 받는다.
		// =>BoardDetailServlet?no=1
		// a.jsp?page=1 a.jsp한테 보낸
		String no=request.getParameter("no");
		/*
		 *  ?no=1&page=10&name=mmm
		 *  -------------------------&로 구분
		 *  ajax => {"no":1}
		 *  react/vue => params:{"no":1}
		 */
		// 오라클에서 값을 얻어온다
		BoardDAO dao=BoardDAO.newInstance();
		BoardVO vo=dao.boardDetailData(Integer.parseInt(no));
		
		// 브라우저가 읽을 수 있게 출력
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글쓰기</h1>");
		out.println("<form method=post action=BoardInsertServlet>"); // 밑에 do post 호출
		// 입력된 데이터를 한번에 => action에 등록된 클래스로 전송
		// 메소드 => method=post => doPost()
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<th width=15%>이름</th>");
		out.println("<td width=85%><input type=text name=name size=15 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 required></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15%>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=50 name=content required></textarea></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15%>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=글쓰기>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			request.setCharacterEncoding("UTF-8");// 한글변환 //디코딩 해주는 코딩
			// 디코딩 => byte[]를 한글로 변환
			// 자바 => 2byte => 브라우저는 1byte로 받는다 => 2byte
		}catch(Exception ex) {}
		//%ED%99%8D%EA%B8%B8%EB%8F%99& => 인코딩
		// 홍길동 => 디코딩
		// 값을 받는다
		String name=request.getParameter("name");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String pwd=request.getParameter("pwd");
		
		/*System.out.println("이름:"+name); 
		System.out.println("제목:"+subject);
		System.out.println("내용:"+content);
		System.out.println("비밀번호:"+pwd); // 위에 name이랑*/
		BoardVO vo=new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		// 오라클로 insert 요청
		BoardDAO dao=BoardDAO.newInstance();
		dao.boardInsert(vo);
		
		//화면이동
		response.sendRedirect("BoardListServlet");
	}
}
