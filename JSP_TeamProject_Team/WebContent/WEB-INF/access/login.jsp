<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="/css/access/login.css" rel="stylesheet" type="text/css" media="all" />
<title>로그인</title>
</head>
<body>
    <div id="container">
    <jsp:include page="../template/header.jsp"/>
    <section>
    	<div id="sec_container">
	        <h1>모두의 웹툰과 함께하기</h1>
	        <div class="err">${msg }</div>
	        <div class="login_win">
	            <form action="/login" method="post">
	                <input type="text" id="user_id" name="u_id" placeholder="아이디" value="${u_id}" autofocus><br>
	                <input type="password" id="user_pw" name="u_pw" placeholder="비밀번호"><br>
	                <input id="login_btn" type="submit" value="로그인">
	            </form>
		        <a href="/join"><button id="join_btn" class="">회원가입</button></a>
	            <div class="snsbtn"><img class="snsimg" src="/images/login_logo/kakao_bigBtn.png" id="kakao" onclick="goKakao()"></div>
	            <div class="snsbtn"><img class="snsimg" src="/images/login_logo/naver_bigBtn.png" id="naver" onclick="goNaver()"></div>
	            <div class="snsbtn"><img class="snsimg" src="/images/login_logo/google_bigB	tn.png" id="google" onclick="goGoogle()"></div>  	
	        	<div class="newPw"><a href="/newPw">비밀번호를 잊으셨나요?</a></div>
	        </div>
    	</div>
    </section>
	<jsp:include page="../template/footer.jsp"/>
	</div>	
</body>
<script type="text/javascript">
	function goKakao() {
		location.href = "/SNSController?snsPlatform=kakao"
	}
	function goNaver() {
		location.href = "/SNSController?snsPlatform=naver"
	}
	function goGoogle() {
		location.href = "/SNSController?snsPlatform=google"
	}
</script>
</html>
