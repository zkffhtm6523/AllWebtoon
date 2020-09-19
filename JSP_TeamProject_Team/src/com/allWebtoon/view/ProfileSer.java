package com.allWebtoon.view;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/profile")
public class ProfileSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		ViewResolver.accessForward("profile", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그인 세션 정보 받아오기  
		UserVO loginUser = MyUtils.getLoginUser(request);
		
		String u_name = request.getParameter("updName");
		System.out.println(u_name);
		//업로드한 이미지 저장 공간 에러  
		String savePath = getServletContext().getRealPath("images") + "/u_profile/user/" + loginUser.getU_no(); //저장경로
		//저장 경로를 매개변수로 받는 File 타입의 변수 하나를 만든다.
		File directory = new File(savePath);
		//만약 디렉토리가 존재하지 않는다면, 디렉토리를 만든다.
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		//1024 * 1024 * 10(10mb) 매번 곱하는 것보다 곱한 값이 빠르다.
		int maxFileSize = 10_485_760;
		
		String fileNm = "";
		String originFileNm = "";
		String saveFileNm = null;
		
		String updName = null;
		String updBirth = null;
		String updEmail = null;
		try {
			//이름 중복되면 자동으로 이름 바꿔서 저장해줌. 저장 후 파일이름 변경해야됨
			MultipartRequest mr = new MultipartRequest(request, savePath, 
					maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration files = mr.getFileNames();
			//type : file이 이외의 form 태그 값들 받아오기
			updName = mr.getParameter("updName");
			updBirth = mr.getParameter("updBrith");
			updEmail = mr.getParameter("updEmail");
			//파일의 다음 엘리멘트가 더 있냐
			while(files.hasMoreElements()) {
				String key = (String)files.nextElement();
				fileNm = mr.getFilesystemName(key);
				originFileNm = mr.getOriginalFileName(key);
				if(originFileNm != null) {
					System.out.println("originFileNm : "+originFileNm);
					//확장자 추출
					int pos = fileNm.lastIndexOf( "." );
					String ext = fileNm.substring(pos);
					
					//예전 파일
					File oldFile = new File(savePath+"/"+fileNm);
					//공파일 만들기
					saveFileNm = UUID.randomUUID()+ext;
					File newFile = new File(savePath+"/"+saveFileNm);
					oldFile.renameTo(newFile);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		UserVO param = new UserVO();
		param.setU_no(loginUser.getU_no());
		param.setU_name(updName);
		param.setU_email(updEmail);
		param.setU_birth(updBirth);
		param.setU_id(loginUser.getU_id());
		//DB에 프로필 파일명 저장
		if(saveFileNm != null) {
			param.setU_profile(saveFileNm);
			loginUser.setU_profile(saveFileNm);
			loginUser.setChkProfile(saveFileNm.substring(0, 4));
		}
		UserDAO.updUser(param);
		//수정된 정보 다시 가져오기  
		UserDAO.selSNSUser(param);
		
		loginUser.setU_name(param.getU_name());
		loginUser.setU_email(param.getU_email());
		loginUser.setU_birth(param.getU_birth());
		loginUser.setM_dt(param.getM_dt());
		response.sendRedirect("/profile");
	}

}
