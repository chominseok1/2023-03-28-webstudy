package com.sist.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class GoodsModel {
@RequestMapping("goods/goods_list.do")
public String goods_list(HttpServletRequest request,HttpServletResponse response)
{
	String page=request.getParameter("page");
	if(page==null)
		page="1";
	String type=request.getParameter("type");
	if(type==null)
		type="1"; //goodsAlll 
	int curpage=Integer.parseInt(page);
	GoodsDAO dao=GoodsDAO.newInstance();
	List<GoodsVO> list=dao.goodsListData(curpage, Integer.parseInt(type));
	int totalpage=dao.goodsTotalPage(Integer.parseInt(type));
	request.setAttribute("list", list);
	request.setAttribute("curpage", curpage);
	request.setAttribute("totalpage", totalpage);
	request.setAttribute("type", type);
	String[] msg= {"","전체 상품 목록","베스트 상품 목록","신상품 목록","특가 상품 목록"};
	request.setAttribute("msg", msg[Integer.parseInt(type)]);
	request.setAttribute("main_jsp", "../goods/goods_list.jsp");
	
	CommonModel.commonRequestData(request);
	return "../main/main.jsp";
}
}
