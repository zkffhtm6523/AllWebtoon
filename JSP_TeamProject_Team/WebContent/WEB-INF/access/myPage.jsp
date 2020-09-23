<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 페이지</title>
<style type="text/css">
section{
	width:100%;
	background-color: #F8F8F8;
	margin: 0 auto;
	background-color: #F8F8F8;
	border-top: 1px solid #EAEAEA;
	text-align: center;
}
section #myPageContainer{
	width: 90%;
	margin: 30px auto;
}
section #myPageContainer .printImage{
 	width: 60%;
 	margin: 0 auto;
 	border: 3px solid #EAEAEA;
	border-radius: 8px;
	padding: 10px;
}
section .profileImg{
	width: 160px;
 	height:160px;
 	border-radius: 50%; 
 	object-fit: cover; 
 	overflow: hidden;
 }
section .name{
	color: gray; 
	font-weight: gray;
	font-weight: bold;
	width: 75px;
	display: inline-block;
	text-align: left;
}
 section #myPageContainer .printImage ul{
 	display: inline-block;
 	vertical-align: top;
 	position:relative;
 	top: 8px;
 	list-style-type: none;
 	text-align: left;
 }
 section #myPageContainer .printImage ul li{
 	margin: 15px auto;
 }
.result_view .listItem {display: inline-block;}
.result_view .listItem ul {list-style-type:none;}
.result_view .listItem img {width: 125px; height: 100px;}
</style>
</head>
<body>
<div id="container">
	<jsp:include page="../template/header.jsp"/>
	<section>
		<div id="myPageContainer">
			<div class="printImage">
			<c:choose>
				<c:when test="${loginUser.u_profile eq ''}">
					<img class="profileImg" src="/images/u_profile/default_image.jpg" alt="프로필 설정 가기">
				</c:when>
				<c:when test="${loginUser.chkProfile eq 'http'}">
					<img class="profileImg" src="${loginUser.u_profile}" alt="프로필 설정 가기">
				</c:when>
				<c:otherwise>
					<img class="profileImg" src="/images/u_profile/user/${loginUser.u_no}/${loginUser.u_profile}" alt="프로필 설정 가기">
				</c:otherwise>
			</c:choose>
			<ul>
				<li>
					<span class="name">성별</span>&nbsp;
				 	${loginUser.u_name}
				 </li>
				<li>
					<span class="name">이메일</span>&nbsp;
					${loginUser.u_email}
				</li>
				<li>
					<span class="name">생년월일</span>&nbsp;
					${loginUser.u_birth}
				</li>
			</ul>
			</div>
			<div class="result_view">
				<c:if test="${fn:length(list) > 0}">
					<c:forEach var="i" begin="0" end="${fn:length(list) > 11 ? 10 : fn:length(list) - 1}">
						<div class="listItem">
							<ul>
								<li><a href="/webtoon/detail?w_no=${list[i].w_no}"><img src="${list[i].w_thumbnail}"></a></li>
								<li>${list[i].w_title}</li>
								<li>${list[i].c_rating}</li>
							</ul>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</section>
	<jsp:include page="../template/footer.jsp"/>
</div>
</body>
<script type="text/javascript">
function moveToDetail(w_no) {
	location.href = '/webtoon/detail?w_no='+w_no
}
function moveToLogin() {
	location.href = '/login'
}
function moveToJoin() {
	location.href = '/join'
}
function moveToResult() {
	if(event.keyCode == 13){
		var result = search.value
		location.href = '/searchResult?result='+result
	}
}
function goHome() {
	location.href = '/home'
  }
function moveToMyPage() {
	location.href = '/myPage?i_user=${loginUser.u_no}'
}
function moveToProfile() {
	location.href = '/profile?i_user=${loginUser.u_no}'
}
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