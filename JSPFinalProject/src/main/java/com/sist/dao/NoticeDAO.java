package com.sist.dao;
/*
 * JSP(link) => button,<a>,ajax => .do(출력할 데이터 전송)
 * --------------------------------------- jsp => jsp
 * 1) DispatcherServlet => Model안에 @RequestMapping => 찾은 메소드에 request를 넘겨준다
 * 2) Model 안에서 요청처리 => 결과값을 request에 담는다
 * 3) 결과값이 담긴 request를 DispatcherServlet이 받는다
 * 4) 화면에 보여줄 JSP를 찾아서 => request를 전송
 * 
 * 
 * 
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.taglibs.standard.lang.jstl.test.StaticFunctionTests;

import com.sist.common.*;
import com.sist.vo.*;
public class NoticeDAO {
 private Connection conn;
 private PreparedStatement ps;
 private CreateDataBase db=new CreateDataBase();
 private static NoticeDAO dao;
 
 public static NoticeDAO newInstance()
 {
	 if(dao==null)
		 dao=new NoticeDAO();
	 return dao;
 }
 public List<NoticeVO> noticeListData(int page)
 {
	 List<NoticeVO> list=new ArrayList<>();
	 try
	 {
		 conn=db.getConnection();
		 String sql="SELECT no,subject,name,type,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit,num "
		 		+ "FROM (SELECT no,subject,name,type,regdate,hit,rownum as num "
		 		+ "FROM (SELECT /*+INDEX_DESC(project_notice pn_no_pk)*/ no,subject,name,type,regdate,hit "
		 		+ "FROM project_notice)) "
		 		+ "WHERE num BETWEEN ? AND ?";
		 ps=conn.prepareStatement(sql);
		 int rowSize=10;
		 int start=(rowSize*page)-(rowSize-1);
		 int end=rowSize*page;
		 
		 ps.setInt(1, start);
		 ps.setInt(2, end);
		 
		 ResultSet rs=ps.executeQuery();
		 while(rs.next())
		 {
			 NoticeVO vo=new NoticeVO();
			 vo.setNo(rs.getInt(1));
			 vo.setSubject(rs.getString(2));
			 vo.setName(rs.getString(3));
			 vo.setType(rs.getInt(4));
			 vo.setDbday(rs.getString(5));
			 vo.setHit(rs.getInt(6));
			 list.add(vo);
		 }
		 rs.close();
		 
	 }catch(Exception ex)
	 {
		 ex.printStackTrace();
	 }
	 finally
	 {
		 db.disConnection(conn, ps);
	 }
	 return list;
 }
 public int noticeTotalPage()
 {
	 int total=0;
	 try
	 {
		 conn=db.getConnection();
		 String sql="SELECT CEIL(COUNT(*)/10.0) FROM project_notice";
		 ps=conn.prepareStatement(sql);
		 ResultSet rs=ps.executeQuery();
		 rs.next();
		 total=rs.getInt(1);
		 rs.close();
	 }catch(Exception ex)
	 {
		 ex.printStackTrace();
	 }
	 finally
	 {
		 db.disConnection(conn, ps);
	 }
	 return total;
 }
 //상세보기
 public NoticeVO noiceDetailData(int no)
 {
	 NoticeVO vo=new NoticeVO();
	 try
	 {
		 conn=db.getConnection();
		 String sql="UPDATE project_notice SET "
		 		+ "hit=hit+1"
		 		+ "WHERE no=?";
		 ps=conn.prepareStatement(sql);
		 ps.setInt(1, no);
		 ps.executeUpdate();
		 
		 sql="SELECT no,name,subject,content,type,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit "
		 		+ "FROM project_notice "
		 		+ "WHERE no=?";
		 ps=conn.prepareStatement(sql);
		 ps.setInt(1, no);
		 ResultSet rs=ps.executeQuery();
		 rs.next();
		 vo.setNo(rs.getInt(1));
		 vo.setName(rs.getString(2));
		 vo.setSubject(rs.getString(3));
		 vo.setContent(rs.getString(4));
		 vo.setType(rs.getInt(5));
		 vo.setDbday(rs.getString(6));
		 vo.setHit(rs.getInt(7));
		 rs.close();
	 }catch(Exception ex)
	 {
		 ex.printStackTrace();
	 }
	 finally
	 {
		 db.disConnection(conn, ps);
	 }
	 return vo;
 }
}
