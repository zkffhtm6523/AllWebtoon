<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<link rel="stylesheet" type="text/css" href="/css/common.css">
</head>
<header>
	<div id="topMenu">
		<h1><img alt="모두의 웹툰" src="/images/logo2.png" id="logo" onclick="goHome()" title="모두의 웹툰"></h1>
<!--  	<div id="centerMenu">
		<input type="search" id="search" placeholder="웹툰, 작가를 검색하세요" onkeydown="moveToResult()" title="검색어 입력">
	</div>-->
		<fieldset id="centerMenu">
			<legend class="hidden">검색 </legend>
			<input type="search" id="search" placeholder="제목, 작가를 검색하세요" onkeydown="moveToResult()" title="검색어 입력" maxlength="50">
		</fieldset>
		<fieldset id="rightMenu">
			<legend class="hidden">회원관련 </legend>
			
			<c:choose>
				<c:when test="${loginUser.u_name eq null}">
				<div id="rightMenu3">
					<button id="login" onclick="moveToLogin()" title="로그인">로그인</button>
					<button id="signin" onclick="moveToJoin()" title="회원가입">회원가입</button>
				</div>
				</c:when>
				<c:otherwise>
					<div id="rightMenu2">
						<div class="containerPImg" onclick="moveToProfile()" title="프로필 설정">
							<c:choose>
								<c:when test="${(loginUser.u_profile eq '') or (loginUser.u_profile eq null)}">
									<img class="pImg" src="/images/u_profile/default_image.jpg" alt="프로필 설정 가기">
								</c:when>
								
								<c:when test="${loginUser.chkProfile eq 'http'}">
									<img class="pImg" src="${loginUser.u_profile}" alt="프로필 설정 가기">
								</c:when>
								
								<c:otherwise>
									<img class="pImg" src="/images/u_profile/user/${loginUser.u_no}/${loginUser.u_profile}" alt="프로필 설정 가기">
								</c:otherwise>
							</c:choose>
						</div>
						<div id="menuBtn">
							<button id="myPage" onclick="moveToMyPage()" title="내 정보">${loginUser.u_name}님</button>
							<button id="review" title="평가하기" onclick="moveToReview()">평가하기</button>
							<button id="logout" onclick="moveToLogOut()" title="로그아웃">로그아웃</button>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</fieldset>
	</div>
</header>

<script>
//로그인으로 넘어가기
function moveToLogin() {
	location.href = '/login'
}
//회원가입으로 넘어가기
function moveToJoin() {
	location.href = '/join'
}

//홈으로 가기
function goHome() {
	location.href = '/home'
  }
//마이 페이지로 넘어가기
function moveToMyPage() {
	location.href = '/myPage'
}
//프로필로 넘어가기
function moveToProfile() {
	location.href = '/profile'
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
//검색결과로 넘어가기
	function moveToResult() {
		if(event.keyCode == 13){
			var result = search.value
			location.href = '/searchResult?result='+result
		}
	}
</script>
</html>