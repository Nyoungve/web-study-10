package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//파일 업로드를 하기 위한 클래스에 대한 import
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
	
	//post 방식으로 전송
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println("응답!");
		
		
		String savePath = "upload"; // 여기를 바꿔주면 다운 받는 경로가 바뀜 
		int uploadFileSizeLimit = 5 * 1024 * 1024; // 최대 업로드 파일 크기 5MB로 제한 
		String encType= "UTF-8";
		
		//서버상의 실제 경로를 찾아준다.
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		System.out.println("서버상의 실제 디렉토리 : ");
		System.out.println(uploadFilePath);
	
		try{ 
			
			MultipartRequest multi = new MultipartRequest(
					request, //request 객체 
					uploadFilePath, //서버상의 실제 디렉토리
					uploadFileSizeLimit,// 최대 업로드 파일 크기 
					encType, //인코딩 방법
					//동일한 이름이 존재하면 새로운 이름이 부여됨
					new DefaultFileRenamePolicy());
			
			//업로드 된 파일 이름 열기 
			String fileName = multi.getFilesystemName("uploadFile");
			
			if(fileName == null){
				System.out.println("파일 업로드 되지 않았음");
			}else{//파일이 업로드 되었을 때 
				out.println("<br> 글쓴이 : "+multi.getParameter("name"));
				out.println("<br> 제 &nbsp 목: "+multi.getParameter("title"));
				out.println("<br> 파일명 : "+fileName);
			}//else
			
		}catch(Exception e){
			System.out.println("예외 발생 : "+ e );
		}//catch
		
	}
}
