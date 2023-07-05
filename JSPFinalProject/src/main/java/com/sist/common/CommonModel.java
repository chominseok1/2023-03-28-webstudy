package com.sist.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class CommonModel {
	public static void commonRequestData(HttpServletRequest request)
	{
		// footer에 들어가는 ss
		FoodDAO dao=FoodDAO.newInstance();
		// => 공지사항 => 최신 뉴스
		// => 방문 맛집 
		List<FoodVO> flist=dao.foodTop7();
		request.setAttribute("flist", flist);
	}
}
