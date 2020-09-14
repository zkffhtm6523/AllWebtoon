<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>로그인</title>
    <style>
    section{
		width:100%;
		background-color: #F8F8F8;
		margin: 0 auto;
		background-color: #F8F8F8;
	 	border-top: 1px solid #EAEAEA;
	 	text-align: center;
	}
    section h1 {
        text-align: center;
        margin-top: 40px;
        font-size: 2em;
    }
    section .login_win {
        text-align: center; padding: 20px; margin: 10px;
    }
    section #frm{
    	width: 400px;
    	margin: 0 auto;
    }
    section #user_id, #user_pw {
        width: 300px;
        height: 30px;
        padding: 10px;
        padding-left: 20px;
        padding-top:15px;
        margin: 10px auto;
        border-radius: 8px;
        border : 1px solid #4FA2C7;
    }
    section #login_btn{
        width: 358px;
    	padding: 20px;
    	margin: 5px auto;
    	border: none;
        color: white;
        background-color: #ccb2e5;
        border-radius: 8px;
        font-size: 1.05em;
    }
    section #join_btn{
    	width: 358px;
    	padding: 20px;
    	margin: 5px auto;
    	border: none;
    	background-color: #4FA2C7;
    	border-radius: 8px;
    	font-size: 1.05em;
    	color: white;
    }
    section #join_btn:hover, #login_btn:hover{
    	cursor: pointer;
    }
    section a{
    	text-decoration: none;
    }
    section .snsimg{width: 360px; height: 60px;}
    section .snsimg:hover{cursor: pointer;}
    </style>
</head>
<body>
    <div id="container">
    <jsp:include page="../template/header.jsp"/>
    <section>
        <h1>모두의 웹툰과 함께하기</h1>
        <div class="err">${msg }</div>
        <div class="login_win">
            <form action="/login" method="post">
                <input type="text" id="user_id" name="u_id" placeholder="아이디" value="${u_id}" autofocus><br>
                <input type="password" id="user_pw" name="u_pw" placeholder="비밀번호"><br>
                <input id="login_btn" type="submit" value="로그인">
            </form>
	        <a href="/join"><button id="join_btn" class="">회원가입</button></a>
            <div class="snsbtn"><img class="snsimg" src="/images/login_logo/kakao_btn.PNG" id="kakao" onclick="goKakao()"></div>
            <div class="snsbtn"><img class="snsimg" src="/images/login_logo/naver_btn.PNG" id="naver" onclick="goNaver('${state}')"></div>
            <div class="snsbtn"><img class="snsimg" src="/images/login_logo/google_btn.PNG" id="google" onclick="goGoogle()"></div>  	
        </div>
    </section>
	<jsp:include page="../template/footer.jsp"/>
	</div>	
</body>
<script type="text/javascript">
	function goKakao() {
		location.href = 'https://kauth.kakao.com/oauth/authorize'
			    		+'?client_id=48c16d63af5493c7ae43a1433ec7760f'
			            +'&redirect_uri=http://localhost:8089/login?platNo=1'
			            +'&response_type=code'
	}
	function goNaver(state) {
		var encoding = encodeURIComponent('http://localhost:8089/naverAPI')
		location.href = 'https://nid.naver.com/oauth2.0/authorize?response_type=code'
						+'&client_id=gtb_8Ij5V31vLTCJA7F3'
						+'&redirect_uri='+encoding
						+'&state='+state
	}
	function goGoogle() {
		location.href = 'https://accounts.google.com/o/oauth2/auth?'
			 + 'scope=https://www.googleapis.com/auth/userinfo.profile'
			 + '&approval_prompt=force'
			 + '&access_type=offline'
			 + '&response_type=code'
			 + '&client_id=659641044041-d8d9d26ubldu5veldv2g3cqaqedv6htq.apps.googleusercontent.com'
			 + '&redirect_uri=http://localhost:8089/googleAPI'
			 //scope=https://www.googleapis.com/auth/userinfo.email'
	}
	//검색결과로 넘어가기
	function moveToResult() {
		if(event.keyCode == 13){
			var result = search.value
			location.href = '/searchResult?result='+result
		}
	}
	//웹툰 상세페이지 가기
  	function moveToDetail(w_no) {
  		location.href = '/webtoon/detail?w_no='+w_no
  	}
  	//로그인으로 넘어가기
	function moveToLogin() {
		location.href = '/login'
	}
	//회원가입으로 넘어가기
	function moveToJoin() {
		location.href = '/join'
	}
	//검색결과로 넘어가기
	function moveToResult() {
		if(event.keyCode == 13){
			var result = search.value
			location.href = '/searchResult?result='+result
		}
	}
	//홈으로 가기
	function goHome() {
		location.href = '/home'
	  }
	//마이 페이지로 넘어가기
	function moveToMyPage() {
		location.href = '/myPage?i_user=${loginUser.u_no}'
	}
	//프로필로 넘어가기
	function moveToProfile() {
		location.href = '/profile?i_user=${loginUser.u_no}'
	}
	//로그아웃하기
	function moveToLogOut() {
		if(confirm('로그아웃 하시겠습니까?')){
    		location.href = '/logout'
		}
	}
	//평가페이지 가기
	function moveToReview(){
		location.href = '/webtoon/cmt'
	}
</script>
</html>
