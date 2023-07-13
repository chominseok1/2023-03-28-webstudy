package com.sist.model;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.controller.RequestMapping;

public class FoodJjimLikeModel {
	@RequestMapping("jjim/jjim_insert.do")
	public String jjim_insert(HttpServletRequest request,HttpServletResponse response)
	{
		// insert여서 forward할 필요가 없음
		String fno=request.getParameter("fno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id"); // id session에서 갖고오기
		// db에 값 넣기
		FoodJJimVO vo=new FoodJJimVO();
		vo.setId(id);
		vo.setFno(Integer.parseInt(fno));
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodJjimInsert(vo);
		// 화면 이동 (서버)
		// sendRedirect() => 재호출 => return .do (request를 초기화) 
		// forward() => 새로운 데이터 전송 return .jsp (request에 값을 담아서 전송)
		// return "redirect:"; 받았던 request를 초기화 해서 리턴 
		return "redirect:../food/food_detail.do?fno="+fno; //redirect request.setAttribute(x) 
	}
	@RequestMapping("jjim/jjim_cancel.do")
	public String jjim_cancel(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodJjimCancle(Integer.parseInt(no));
		
		return "redirect:../mypage/mypage_jjim_list.do";
	}
	@RequestMapping("like/like_insert.do")
	public String like_insert(HttpServletRequest request,HttpServletResponse response)
	{
		// insert하면 자신이 보고 있던 페이지로 넘어가야함 
		String fno=request.getParameter("fno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id"); 
		FoodLikeVO vo=new FoodLikeVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setId(id);
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodLikeInsert(vo);
		return "redirect:../food/food_detail.do?fno="+fno;
	}
}
