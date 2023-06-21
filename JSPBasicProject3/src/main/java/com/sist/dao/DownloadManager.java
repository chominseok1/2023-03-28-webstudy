package com.sist.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;

public class DownloadManager {
	/*
	 * public void download(HttpServletRequest request,HttpServletResponse response)
	 * {
	 * 
	 * %E%ddd => 디코딩 POST : setCharacterEncoding("UTF-8") GET : window10은 자동 처리
	 * 
	 * String fn=request.getParameter("fn"); //System.out.println(fn); try { File
	 * file=new File("c:\\download\\"+fn);
	 * response.setHeader("Content-Disposition","attachement;filename="+URLEncoder.
	 * encode(fn, "UTF-8")); response.setContentLength((int)file.length());
	 * 
	 * BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
	 * BufferedOutputStream bos= new
	 * BufferedOutputStream(response.getOutputStream());
	 * 
	 * int i=0; byte[] buffer=new byte[1024]; while((i=bis.read(buffer,0,1024))!=-1)
	 * { bos.write(buffer,0,i); } bis.close(); bos.close(); //out=null;
	 * //pageContext.pushBody(); }catch(Exception ex){} }
	 */
}
