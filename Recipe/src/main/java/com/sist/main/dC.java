package com.sist.main;



import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;



import com.sist.dao.RecipeDAO;

import com.sist.vo.RecipeVO;



public class dC {

	 public void foodCategoryData()

	   {

		   // 오라클에 추가 

		   //div.h_row_center p.h_ellipsis_1

		 	RecipeDAO dao=new RecipeDAO();

		   try

		   {

			   // 사이트 연결 

			   

			   for(int page=1;page<=388;page++) {

			   Document doc=Jsoup.connect("https://www.10000recipe.com/recipe/list.html?cat4=54&order=reco&page="+page).get();

			   Elements title=doc.select("div.common_sp_caption_tit");//30개 

			  Elements chef=doc.select("div.common_sp_caption_rv_name");//30개

			 Elements poster=doc.select("div.common_sp_thumb >a>img");//30개

			  Elements link=doc.select("div.common_sp_thumb a");//30개

			  Elements hit=doc.select("span.common_sp_caption_buyer");

			   //System.out.println(title.toString());

			   //System.out.println("==========================");

			  //System.out.println(chef.toString());

			   //System.out.println("==========================");

			   //System.out.println(poster.toString());

			   //System.out.println("==========================");

			  //System.out.println(link.toString());

			   for(int i=0;i<title.size();i++)

			   {

				   System.out.println(title.get(i).text());

				 System.out.println(chef.get(i).text());

				 

				   System.out.println(poster.get(i).attr("src"));

				   

				  System.out.println("https://www.10000recipe.com/"+link.get(i).attr("href"));

				  System.out.println(hit.get(i).text());

				  // System.out.println("====================================");

				  		RecipeVO vo=new RecipeVO();

				  		vo.setTitle(title.get(i).text());

				  		vo.setChef(chef.get(i).text());

				  		vo.setPoster(poster.get(i).attr("src"));

				  		vo.setLink("https://www.10000recipe.com/"+link.get(i).attr("href"));

				  		vo.setHit(hit.get(i).text());

				  		dao.RecipeInsert(vo);

				   /*

				    *   https://mp-seoul-image-production-s3.mangoplate.com/

				    *   keyword_search/meta/pictures/zclmkfuclkli41io.png?

				    *   fit=around|600:400=600:400;

				    *   *,*&output-format=jpg=80

				    */

			   }

			   }

			  

			   System.out.println("저장 완료!!");

			 

			   

		   }catch(Exception ex){}

	   }

	   public static void main(String[] args) {

		dC dc=new dC();

		dc.foodCategoryData();

	}



}