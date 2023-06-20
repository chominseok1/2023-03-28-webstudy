<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//response.sendRedirect("response_2.jsp");
	RequestDispatcher rd=request.getRequestDispatcher("response_2.jsp");
	rd.forward(request, response); //paste 파일명은 안바뀌고 덮어쓰기
	System.out.println(request);
%>