package com.sist.dao;



import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.List;



import com.sist.vo.RecipeVO;



public class RecipeDAO {

	private Connection conn; // 오라클 연결 객체(오라클뿐만아니라 모든 데이터베이스연결)

	 private PreparedStatement ps; // SQL 문장 전송 / 결과값읽기

	 private final String URL="jdbc:oracle:thin:@localhost:1521:XE";

	 // mySQL => jdbc:mysql://localhost/mydb

	 private static RecipeDAO dao; // 싱글턴 패턴 스태틱없이 생성하면 계속 생성됨 느려짐.

	 // DAO객체를 한사람당 한개만 사용이 가능하게 만든다.

	 //드라이버 설정 => 소프트웨어 (메모리 할당 요청) Class.forName()

	 // 클래스의 정보를 전송

	 // 드라이버 설치는 1번만수행*

	 public RecipeDAO() // 생성자 안에다가

	 {

		 try

		 {

			 Class.forName("oracle.jdbc.driver.OracleDriver");

		 }catch(Exception ex) {}

	 }

	  // 오라클 연결

	  public void getConnection()

	  {

		  try

		  {

			  conn=DriverManager.getConnection(URL,"hr","happy");

			   //=> 오라클 전송 :conn hr/happy

		  }catch(Exception ex) {}

	  }

	  // 오라클 연결 종료

	  public void disConnection()

	  {

		  try

		  {

			  if(ps!=null) ps.close(); // 연결 되어있으면

			  if(conn!=null)conn.close();

			  //=> 오라클 전송 : exit

		  }catch(Exception ex) {}

	  }

	  //DAO객체를 1개만 생성해서 사용 => 메모리 누수 현상 방지 (싱글톤 패턴)

	  // 싱글톤/팩토리 => 면접 (스프링:패턴 8개)

	  public static RecipeDAO newInstance()

	  {

		  //newInstance() , getInstance() 나오면 => 싱글턴 패턴

		  if(dao==null)

			  dao=new RecipeDAO();

		  return dao;

	  }

	  // ========================== jdbc 기본셋팅 (모든 DAO는 동일)

	  // 여기부터 기능 FOODDAO의 기능

	  //1. 데이터 수집 (INSERT)

	  /*

	   *  Statement => SQL => 생성과 동시에 데이터 추가

	   *  		"'"+name+"','"+sex+"','"+...

	   *  PreparedStatement => 미리 sql문장을 만들어놓고 나중에 값을 채워주는 형태

	   *   => default 

	   *  CallableStatement => procedure 호출(함수호출)

	   */

	  public void RecipeInsert(RecipeVO vo)

	  {

		  try

		  {

			  //1. 연결

			  getConnection();

			  //2. SQL문장 생성

			  String sql="INSERT INTO recipe_example VALUES("

					  +"rp_cno_seq.nextVal,?,?,?,?,?)"; //? 1 2 3 4 0번없음

			  /*

			   *  "'"+vo.getTitle()+"','"

			   *  

			   *  INSERT ~ VALUES ('홍길동','서울'

			   */

			  //3. SQL문장을 오라클로 전송

			  ps=conn.prepareStatement(sql); // PreparedStatement =>미리 sql문장을 만들어놓고 나중에 값을 채워주는 형태

			  //3-1 => ?에 값 채워주기

			 ps.setString(1, vo.getTitle()); // "'"+vo.getTitle()+"'" 작은따옴표 붙여주는 

			 ps.setString(2, vo.getChef());

			 ps.setString(3, vo.getPoster());

			 ps.setString(4, vo.getLink());

			 ps.setString(5, vo.getHit());

			  // 단점 => 번호가 잘못되면 오류 발생 번호 1번부터 시작 , 데이터형 틀리면 오류남 

			 // IN,OUT ~오류 : 번호가 틀렸다.

			  //4. SQL문장을 실행 명령 => SQL문장을 작성하고 execute 

			 ps.executeUpdate(); // executeUpdate() => INSERT,UPDATE,DELETE 수행할때  executeQuery() => SELECT

		  }catch(Exception ex) 

		  {

			  ex.printStackTrace();// 에러확인

		  }

		  finally

		  {

			  disConnection(); // 오라클 연결 해제 => 무조건

		  }

	  }

	  public List<RecipeVO> recipeList(int page)

	  {

		  List<RecipeVO> list=new ArrayList<RecipeVO>();

		  try

		  {

			  getConnection();

			  String sql="SELECT cno,title,chef,poster,hit,num "

			  		+ "FROM (SELECT cno,title,chef,poster,hit,rownum as num "

			  		+ "FROM(SELECT cno,title,chef,poster,hit "

			  		+ "FROM recipe_practice ORDER BY cno ASC)) "

			  		+ "WHERE num BETWEEN ? AND ? ";

			  ps=conn.prepareStatement(sql);

			  int rowSize=12;

			  int start=(page*rowSize)-(rowSize-1);

			  int end=rowSize*page;

			  ps.setInt(1, start);

			  ps.setInt(2, end);

			  

			  ResultSet rs=ps.executeQuery();

			  while(rs.next())

			  {

				  RecipeVO vo=new RecipeVO();

				  vo.setCno(rs.getInt(1));

				  vo.setTitle(rs.getString(2));

				  vo.setChef(rs.getString(3));

				  vo.setPoster(rs.getString(4));

				  vo.setHit(rs.getString(5));

				  list.add(vo);

			  }

			  rs.close();

		  }catch(Exception ex)

		  {

			  ex.printStackTrace();

		  }

		  finally

		  {

			  disConnection();

		  }

		  return list;

	  }

	  public int recipeCount()

	  {

		  int count=0;

		  try

		  {

			  getConnection();

			  String sql="SELECT COUNT(*) FROM recipe_example";

			  ps=conn.prepareStatement(sql);

			  ResultSet rs=ps.executeQuery();

			  rs.next();

			  count=rs.getInt(1);

			  rs.close();

		  }catch(Exception ex)

		  {

			  ex.printStackTrace();

		  }

		  finally

		  {

			  disConnection();

		  }

		  return count;

	  }

}