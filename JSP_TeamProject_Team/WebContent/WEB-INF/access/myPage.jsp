<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
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
 	width: 80%;
 	margin: 20px auto;
 	border: 3px solid #EAEAEA;
	border-radius: 8px;
	padding: 10px;
}
section #myPageContainer ul{
 	vertical-align: top;
 	margin: 0px auto;
}
section #myPageContainer .result_view{
 	width: 80%;
 	margin: 20px auto;
 	border: 3px solid #EAEAEA;
	border-radius: 8px;
	padding: 10px;
	padding-bottom: 10px;
	position: relative;
}
section #myPageContainer .result_view > :nth-child(2){
 	position: absolute;
 	top: 50%;
 	left: 2%;
 	cursor: pointer;
}
section #myPageContainer .result_view > :nth-child(8){
 	position: absolute;
 	top: 50%;
 	right: 2%;
 	cursor: pointer;
}

section #myPageContainer .result_view .listItem{
 	margin: 0 auto; 
}
section #myPageContainer .result_view .listItem ul{
 	padding-left: 10px;
 	padding-right: 10px;
 	position: relative;
}
section #myPageContainer .result_view .listItem ul :nth-child(3){
	position:absolute;
	color: gold;
	font-size: 1.3em;
	bottom: 5px;
	left: 10px;
}
section #myPageContainer .result_view .listItem ul :nth-child(4){
 	position:relative;
 	display: inline-block;
 	vertical-align: top;
 	line-height: 30px;
 	left: -20px;
}
section #myPageContainer .result_view .listItem ul :nth-child(5){
	position:absolute;
	color: gray;
	font-size: 1.3em;
	bottom: 5px;
	right: 10px;
}
section #myPageContainer .result_view .listItem ul li a img{
 	border-radius: 8px;
}
section .profileImg{
	width: 160px;
 	height:160px;
 	border-radius: 50%; 
 	object-fit: cover; 
 	overflow: hidden;
 	line-height: 30px;
 }
section h2{
	text-align: left;
	margin-left: 20px;
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
				<li>
					<span class="name">가입경로</span>&nbsp;
					<c:choose>
					<c:when test="${loginUser.u_joinPath eq 1}">모두의 웹툰</c:when>
					<c:when test="${loginUser.u_joinPath eq 2}">카카오</c:when>
					<c:when test="${loginUser.u_joinPath eq 3}">네이버</c:when>
					<c:when test="${loginUser.u_joinPath eq 4}">구글</c:when>
					</c:choose>
				</li>
			</ul>
			</div>
			<div class="result_view">
				<h2>${loginUser.u_name}님의 평가한 웹툰</h2>
				<c:if test="${fn:length(list) > 0}">
					<span class="material-icons" onclick="selCmtMinus()">keyboard_arrow_left</span>
					<c:forEach var="i" begin="0" end="4">
						<div class="listItem">
							<ul>
								<li><a href="/webtoon/detail?w_no=${list[i].w_no}"><img src="${list[i].w_thumbnail}" title="${list[i].w_title}"></a></li>
								<li>${list[i].w_title}</li>
								<span class="material-icons">grade</span>
								<li>${list[i].c_rating}</li>
								<span class="material-icons">insert_comment</span>
							</ul>
						</div>
					</c:forEach>
					<span class="material-icons" onclick="selCmtPlus()">keyboard_arrow_right</span>
				</c:if>
			</div>
		</div>
	</section>
	<jsp:include page="../template/footer.jsp"/>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">
let cmtIdx = 5;
function selCmtMinus() {
	if(cmtIdx - 5 == 0){
		alert('처음입니다')
	}else if(cmtIdx - 5 >= 1){
		console.log('minus : '+cmtIdx - 5)
		axios.get('/myPage',{
			params :{
				page : cmtIdx - 5
			}
		}).then(function(res){
			console.log('ajax 결과 : '+res.data)
			var result_view = document.querySelector('.result_view')
			//listItem 만들기 
			var listItem = document.createElement('div')
			listItem.classList.add('listItem')
			//ul 만들기 
			var ulList = document.createElement('ul')
			listItem.append(ulList)
			//li 만들기 
			var liImg = document.createElement('li')
			ulList.append(liImg)
			//a태그 만들기 
			var aImg = document.createElement('a')
			aImg.href = '/webtoon/detail?w_no='+res.data.w_no
			liImg.append(aImg)
			//이미지 태그 만들기 
			var addImg = document.createElement('img')
			addImg.src = res.data.w_thumbnail
			addImg.title = res.data.w_title
			aImg.append(addImg)
			//타이틀 만들기 
			var liTitle = document.createElement('li')
			liTitle.innerText = res.data.w_title
			ulList.append(liTitle)
			//starIcon 만들기 
			var starIcon = document.createElement('span')
			starIcon.classList.add('material-icons')
			starIcon.innerHTML = 'grade'
			ulList.append(starIcon)
			//평점 li 만들
			var grade = document.createElement('li')
			grade.innerHTML = res.data.c_rating
			ulList.append(grade)
			//cmtIcon 만들기
			var cmtIcon = document.createElement('span')
			cmtIcon.classList.add('material-icons')
			cmtIcon.innerHTML = 'insert_comment'
			ulList.append(cmtIcon)
			result_view.insertBefore(listItem,result_view.children[2])
			result_view.children[7].remove();
			cmtIdx--;
			console.log(cmtIdx)
		})
		
	}
}
function selCmtPlus() {
	axios.get('/myPage',{
		params : {
			page : cmtIdx + 1
		}
	}).then(function(res) {
		console.log(res.data)
		if(res.data == 0){
			alert('마지막입니다')
		}else{
			var result_view = document.querySelector('.result_view')
			//listItem 만들기 
			var listItem = document.createElement('div')
			listItem.classList.add('listItem')
			//ul 만들기 
			var ulList = document.createElement('ul')
			listItem.append(ulList)
			//li 만들기 
			var liImg = document.createElement('li')
			ulList.append(liImg)
			//a태그 만들기 
			var aImg = document.createElement('a')
			aImg.href = '/webtoon/detail?w_no='+res.data.w_no
			liImg.append(aImg)
			//이미지 태그 만들기 
			var addImg = document.createElement('img')
			addImg.src = res.data.w_thumbnail
			addImg.title = res.data.w_title
			aImg.append(addImg)
			//타이틀 만들기 
			var liTitle = document.createElement('li')
			liTitle.innerText = res.data.w_title
			ulList.append(liTitle)
			//starIcon 만들기 
			var starIcon = document.createElement('span')
			starIcon.classList.add('material-icons')
			starIcon.innerHTML = 'grade'
			ulList.append(starIcon)
			//평점 li 만들
			var grade = document.createElement('li')
			grade.innerHTML = res.data.c_rating
			ulList.append(grade)
			//cmtIcon 만들기
			var cmtIcon = document.createElement('span')
			cmtIcon.classList.add('material-icons')
			cmtIcon.innerHTML = 'insert_comment'
			ulList.append(cmtIcon)
			
			result_view.insertBefore(listItem,result_view.children[7])
			result_view.children[2].remove();
			cmtIdx++;
		}
	})
}

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