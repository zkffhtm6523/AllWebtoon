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
    	color: #0c65c6;
    	font-weight: bold;
    	font-size: 1.1em;
    }
    section .newPw{
    	margin: 20px auto;
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
        	<div class="newPw"><a href="/newPw">비밀번호를 잊으셨나요?</a></div>
        </div>
        
    </section>
	<jsp:include page="../template/footer.jsp"/>
	</div>	
</body>
<script type="text/javascript">
	function goKakao() {
		location.href = 'https://kauth.kakao.com/oauth/authorize'
		    		+'?client_id=48c16d63af5493c7ae43a1433ec7760f'
		            +'&redirect_uri=http://101.101.219.238:8080/login?platNo=1'
		        	//+'&redirect_uri=http://192.168.2.8:8089/login?platNo=1'
		        	//+'&redirect_uri=http://localhost:8090/login?platNo=1'
		            +'&response_type=code'
	}
	function goNaver(state) {
		var encoding = encodeURIComponent('http://101.101.219.238:8080/naverAPI')
		location.href = 'https://nid.naver.com/oauth2.0/authorize?response_type=code'
						+'&client_id=gtb_8Ij5V31vLTCJA7F3'
						+'&redirect_uri='+encoding
						+'&state='+state
	}
	function goGoogle() {
		location.href = 'https://accounts.google.com/o/oauth2/auth?'
			 + 'scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/plus.me+https://www.googleapis.com/auth/userinfo.profile'
			 + '&approval_prompt=force'
			 + '&access_type=offline'
			 + '&response_type=code'
			 + '&client_id=659641044041-d8d9d26ubldu5veldv2g3cqaqedv6htq.apps.googleusercontent.com'
			 + '&redirect_uri=http://101.101.219.238:8080/googleAPI'
			 //scope=https://www.googleapis.com/auth/userinfo.email'
	}
</script>
</html>
