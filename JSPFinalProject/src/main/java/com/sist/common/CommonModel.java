package com.sist.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.manager.*;
// => Spring (공통모듈 => AOP) 
// Spring => DI / AOP (사용법 : XML,Annotation)
public class CommonModel {
	public static void commonRequestData(HttpServletRequest request)
	{
		// 공통 모듈 
		// footer에 들어가는 ss
		FoodDAO dao=FoodDAO.newInstance();
		// => 공지사항 => 최신 뉴스
		List<NewsVO> nList=NewsManager.newsSearchData("맛집");
		for(NewsVO vo:nList)
		{
			String title=vo.getTitle();
			if(title.length()>15)
			{
				title=title.substring(0,14)+"...";
				vo.setTitle(title);
			}
			vo.setTitle(title);
		}
		request.setAttribute("nList", nList);
		// => 방문 맛집 
		List<FoodVO> flist=dao.foodTop7();
		request.setAttribute("flist", flist);
	}
}
