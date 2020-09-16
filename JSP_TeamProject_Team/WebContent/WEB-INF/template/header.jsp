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
			<input type="search" id="search" placeholder="웹툰, 작가를 검색하세요" onkeydown="moveToResult()" title="검색어 입력">
		</fieldset>
		<fieldset id="rightMenu">
			<legend class="hidden">회원관련 </legend>
			
			<c:choose>
				<c:when test="${loginUser.u_name eq null}">
				<div id="rightMenu3">
					<button id="signin" onclick="moveToJoin()" title="회원가입">회원가입</button>
					<button id="login" onclick="moveToLogin()" title="로그인">로그인</button>
				</div>
				</c:when>
				
				<c:otherwise>
					<div id="rightMenu2">
						<div class="containerPImg" onclick="moveToProfile()" title="프로필 설정">
							<c:choose>
								<c:when test="${loginUser.u_profile eq ''}">
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
</html>