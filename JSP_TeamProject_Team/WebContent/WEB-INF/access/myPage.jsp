<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 페이지</title>
<style type="text/css">
.result_view .listItem {display: inline-block;}
.result_view .listItem ul {list-style-type:none;}
.result_view .listItem img {width: 125px; height: 100px;}
</style>
</head>
<body>
<div class="container">
	<jsp:include page="../template/header.jsp"/>
	<section>
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