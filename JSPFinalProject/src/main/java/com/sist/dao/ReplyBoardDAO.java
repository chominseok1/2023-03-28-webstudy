package com.sist.dao;
 import java.util.*;
 import java.sql.*;
 import com.sist.common.*;
 import com.sist.vo.*;
public class ReplyBoardDAO {
  private Connection conn;
  private PreparedStatement ps;
  private CreateDataBase db=new CreateDataBase();
  private static ReplyBoardDAO dao;
  
  public static ReplyBoardDAO newInstance()
  {
	  if(dao==null)
		  dao=new ReplyBoardDAO();
	  return dao;
  }
  
  // 사용자 
  /*
   * 	 try
	  {
		  conn=db.getConnection();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  db.disConnection(conn, ps);
	  }
   */
  // 목록
  public List<ReplyBoardVO> replyBoardListData(int page)
  {
	  List<ReplyBoardVO> list=new ArrayList<>();
	  try
	  {
		  conn=db.getConnection();
		  String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,group_tab,num "
		  		+ "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
		  		+ "FROM (SELECT no,subject,name,regdate,hit,group_tab "
		  		+ "FROM project_replyBoard ORDER BY group_id DESC,group_step ASC)) "
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
			ReplyBoardVO vo=new ReplyBoardVO();
			vo.setNo(rs.getInt(1));
			vo.setSubject(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setDbday(rs.getString(4));
			vo.setHit(rs.getInt(5));
			vo.setGroup_tab(rs.getInt(6));
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
  // 총페이지 
  public int replyBoardTotalPage()
  {
	  int total=0;
	  try
	  {
		  conn=db.getConnection();
		  String sql="SELECT CEIL(COUNT(*)/10.0) FROM project_replyBoard ";
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
  // 묻기 (새글)
  
  public void replyBoardInsert(ReplyBoardVO vo)
  {
	  try
	  {
		  conn=db.getConnection();
		  String sql="INSERT INTO project_replyboard(no,id,name,subject,content,group_id) "
		  		+ "VALUES(pr_no_seq.nextval,?,?,?,?,"
		  		+ "(SELECT NVL(MAX(group_id)+1,1) FROM project_replyboard))";
		  ps=conn.prepareStatement(sql);
		  ps.setString(1, vo.getId());
		  ps.setString(2, vo.getName());
		  ps.setString(3, vo.getSubject());
		  ps.setString(4, vo.getContent());
		  ps.executeUpdate();
	  }catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  db.disConnection(conn, ps);
	  }
  }
  // 삭제 
  // 내용 보기
  // 관리자
  
  // 목록
  // 답변 (새글)
  // 삭제 
  // 수정
}
