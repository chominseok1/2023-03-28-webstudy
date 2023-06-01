package com.sist.dao;

// => 카테고리 => 카테고리별 맛집 => 맛집에 대한 상세보기 => 지도출력,검색(Ajax)
import java.util.*;
import java.sql.*;

public class FoodDAO {
	//연결 객체
	private Connection conn;
	//송수신
	private PreparedStatement ps;
	//오라클 URL 주소 설정
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	//싱글턴
	private static FoodDAO dao;
	//1. 드라이버 등록 => 한번 수행 => 시작과 동시에 한번 수행 , 멤버변수 초기화: 생성자
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ClassNotFoundException => 체크 예외처리 => 반드시 예외처리 한다
			// java.io, java.net , java.sql => 체크 예외처리
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//2. 오라클 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			//conn hr/happy => 명령어를 오라클 전송
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//3. 오라클 해제
	public void disConnection()  {
		try {
			if(ps!=null) ps.close();	// 통신이 열려있으면 닫는다
			if(conn!=null) conn.close();
			// exit => 오라클 닫기	
		} catch (Exception e) { }
	}
	//4. 싱글턴설정 => static은 메모리 공간이 1개만 가지고 있다
	// 메모리누수 현상을 방지 ...
	// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다
	// 싱글턴은 데이터베이스에서는 필수 조건
	public static FoodDAO newInstance() {
		if(dao == null)
			dao = new FoodDAO();
		return dao;
	}

	//5. 기능
	//5-1. 카테고리 출력
	public List<CategoryVO> food_category_list()
	{
		//카테고리 1개의 정보 (번호,이미지,제목,부제목) => CategoryVO
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			getConnection();
			String sql = "SELECT cno,title,subject,poster "
					 	+ "FROM food_category "
					 	+ "ORDER BY cno ASC";
			
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())  {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			// list => 받아서 브라우저로 전송실행
			//			   ----------- Servlet, JSP
			//				Spring => Servlet => DispatcherServlet
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return list;
	}
	
	//5-1-1. 카테고리 정보
	public CategoryVO food_category_info(int cno) {
		CategoryVO vo =new CategoryVO();
		try {
			getConnection();
			String sql = "SELECT title, subject FROM food_category "
						+"WHERE cno="+cno;
			ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
	}
	//5-2. 카테고리별 맛집 출력
	//List<FoodVO>
	public List<FoodVO> food_category_data(int cno) {
		List<FoodVO> list = new ArrayList<FoodVO>();
		try {
			getConnection();
			String sql ="SELECT fno,name,poster,address,phone,type,score "
						+"FROM food_house "
						+"WHERE cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster = rs.getString(3);
				/*
				 https://mp-seoul-image-production-s3.mangoplate.com/549779_1651303822210401.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				 ^https://mp-seoul-image-production-s3.mangoplate.com/338150_1664817280152062.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				 ^https://mp-seoul-image-production-s3.mangoplate.com/338150_1664817288679843.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				 ^https://mp-seoul-image-production-s3.mangoplate.com/338150_1664817291213808.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				 ^https://mp-seoul-image-production-s3.mangoplate.com/338150_1664817292352977.jpg?fit=around|512:512#crop=512:512;*,*#output-format=jpg#output-quality=80
				 */
				poster = poster.substring(0,poster.indexOf("^")); //이미지를 한번에 5개 가져옴 >> 구분자가 ^
				poster = poster.replace('#','&'); //가져올때 & > # 바꿨기때문에 원위치
				vo.setPoster(poster);
				
				/*
				 	서울특별시 종로구 돈화문로11가길 25
					지번 서울시 종로구 익선동 156
				 */
				String address = rs.getString(4);
				address =address.substring(0,address.lastIndexOf("지"));
				vo.setAddress(address.trim());
				vo.setPhone(rs.getString(5));
				vo.setType(rs.getString(6));
				vo.setScore(rs.getDouble(7));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
	//5-3. 맛집 상세보기
	public FoodVO foodDetailData(int fno)
	{
		FoodVO vo=new FoodVO();
		try
		{
			getConnection();
			String sql="SELECT fno,cno,name,poster,phone,type,address,"
						+"time,parking,menu,price,score "
					    +"FROM food_house "
						+"WHERE fno=?";
			ps=conn.prepareStatement(sql);
			//?에 값을 채운다 => JSP/1차프로젝트 동일
			//2차 => MyBatis, 보안 (비밀번호 암호화), 실시간 => Betch
			//3차 => 오라클(MySQL), JPA
			//기반 => MSA기반 CI/CD => 설치
			ps.setInt(1, fno);
			// 실행요청 => 결과값 받기
			ResultSet rs=ps.executeQuery();
			rs.next(); // 커서 위치 변경 => 데이터가 출력한 위치로 변경
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setPhone(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setAddress(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setParking(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPrice(rs.getString(11));
			vo.setScore(rs.getDouble(12));
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}
	// FoodVO
	//5-4. 맛집 검색 => Ajax
	//List<FoodVO>
	public List<FoodVO> foodFindData(String addr,int page)
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try
		{
			getConnection();
			/*String sql="SELECT fno,name,poster,score "
					+"FROM food_location "
					+"WHERE address LIKE '%'||?||'%'"; // LIKE문장 코딩
			// => MySql => LIKE CONCAT('%',?,'%')*/
			String sql="SELECT fno,name,poster,score,num "
					 +"FROM(SELECT fno,name,poster,score,rownum as num "
					 +"FROM (SELECT fno,name,poster,score "
					 +"FROM food_location "
					 +"WHERE address LIKE '%'||?||'%')) "
					 +"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setString(1, addr);
			ps.setInt(2,start);
			ps.setInt(3, end);
			
			// 결과값 읽기 
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster=rs.getString(3);
				if(poster.contains("^"))
				{
					poster=poster.substring(0, poster.indexOf("^"));
				}
				poster=poster.replaceAll("#", "& ");
				vo.setPoster(poster);
				vo.setScore(rs.getDouble(4));
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
	// 5-4-1 총페이지 구하기 ==> 데이터(오라클) 
	public int foodRowCount(String addr)
	{
		int count=0;
		try
		{
			getConnection();
			String sql="SELECT COUNT(*) FROM food_location "
					  +"WHERE address LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, addr);
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
	//5-5. 댓글 (crud) => 로그인 
}