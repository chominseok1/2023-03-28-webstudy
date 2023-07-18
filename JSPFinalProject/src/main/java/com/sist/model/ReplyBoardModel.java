package com.sist.model;
/*
 * 	Java: 오라클 연동  ======> DAO
 * 		  브라우저로 값 전송  ====> Model
 * 			
 * 			.do(request)
 *  JSP(웹) === Model ===> DAO
 *  			1.값을 받는다
 *  			request.getParameter()
 *  			DAO연동 => 데이터 읽기
 *  			request.setAttribute()
 *  			2.request를 받을 JSP설정 
 *  	     <==   	  <===
 *  	 ===> 사용자 요청 (여러개) ==> 구분(조건문) if
 *  							 -------------- 어노테이션
 *  							 -------------- 안에 내용이 중복되면 오류
 *  							@RequestMapping("|")
 *  								중복제거 => 폴더
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import com.sist.dao.ReplyBoardDAO;
import com.sist.vo.ReplyBoardVO;

public class ReplyBoardModel {
  @RequestMapping("replyboard/list.do")
  public String replyboard_list(HttpServletRequest request, HttpServletResponse response)
  {
	  // 목록 전송 => DAO
	  String page=request.getParameter("page");
	  if(page==null)
		  page="1";
	  int curpage=Integer.parseInt(page);
	  ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
	  List<ReplyBoardVO> list=dao.replyBoardListData(curpage);
	  int totalpage=dao.replyBoardTotalPage();
	  // java => jsp (request,session)
	  request.setAttribute("curpage", curpage);
	  request.setAttribute("totalpage", totalpage);
	  request.setAttribute(page, list)
	  request.setAttribute("main_jsp", "../replyboard/list.jsp");
	  return "../main/main.jsp";
  }
}
