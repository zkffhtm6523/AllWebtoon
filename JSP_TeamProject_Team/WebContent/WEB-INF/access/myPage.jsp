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
section #myPageContainer .result_view > #prevArrIcon{
 	position: absolute;
 	top: 50%;
 	left: 2%;
 	cursor: pointer;
}
section #myPageContainer .result_view > #nextArrIcon{
 	position: absolute;
 	top: 50%;
 	right: 2%;
 	cursor: pointer;
}
section #myPageContainer .result_view .listItem{
 	position: relative;
 	display: inline-block;
 	vertical-align : top;
 	top: 0px;
 	margin: 0 auto;
}
section #myPageContainer .result_view .nonListItem{
	width: 96%;
	margin: 0 auto;
}
section #myPageContainer .result_view .nonListItem h2{
	text-align: center;
	font-size: 1.3em;	
}
section #myPageContainer .result_view .listItem ul{
 	padding-left: 10px;
 	padding-right: 10px;
 	position: relative;
 	list-style-type:none;
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
 	width: 125px; height: 100px;
}
section .profileImg{
	width: 160px;
 	height:160px;
 	border-radius: 50%; 
 	object-fit: cover; 
 	overflow: hidden;
 	line-height: 30px;
 }
section >  #myPageContainer > .result_view > h2{
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
#loginUser{color: gray; font-weight: bold; font-size: 1.1em;}
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
			<!-- 조회한 웹툰 -->
			<div class="result_view" id="view_list">
				<h2><span id="loginUser">${loginUser.u_name}님</span> 최근 조회 웹툰</h2>
				<c:choose>
					<c:when test="${recentWebtoon != null}">
						<c:forEach var="i" begin="0" end="${fn:length(recentWebtoon) <= 5 ? fn:length(recentWebtoon)-1 : 4 }">
							<div class="listItem">
								<ul>
									<li><a href="/webtoon/detail?w_no=${recentWebtoon[i].w_no}"><img src="${recentWebtoon[i].w_thumbnail}" title="${recentWebtoon[i].w_title}"></a></li>
									<li>${recentWebtoon[i].w_title}</li>
									<c:if test="${recentWebtoon[i].c_rating != 0}">
										<span class="material-icons">grade</span>
										<li>${recentWebtoon[i].c_rating}</li>
									</c:if>
									<c:if test="${recentWebtoon[i].c_com != null}">
										<span class="material-icons">insert_comment</span>
									</c:if>
								</ul>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>조회한 웹툰이 없습니다.</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			
			<div class="result_view" id="cmt_list">
				<h2><span id="loginUser">${loginUser.u_name}님</span> 평가 웹툰</h2>
				<c:choose>
					<c:when test="${list != null}">
					<c:if test="${fn:length(list) > 5}">
							<span class="material-icons" id="prevArrIcon" onclick="selCmtMinus('cmt')">keyboard_arrow_left</span>
						</c:if>
							
					
						<c:forEach var="i" begin="0" end="${fn:length(list) <= 5 ? fn:length(list)-1 : 4 }">
							<div class="listItem">
								<ul>
									<li><a href="/webtoon/detail?w_no=${list[i].w_no}"><img src="${list[i].w_thumbnail}" title="${list[i].w_title}"></a></li>
									<li>${list[i].w_title}</li>
									<c:if test="${list[i].c_rating != 0 && list[i].c_rating != null}">
										<span class="material-icons">grade</span>
										<li>${list[i].c_rating}</li>
									</c:if>
									<c:if test="${list[i].c_com != null && list[i].c_com != '' && list[i].c_com != ' '}">
										<span class="material-icons">insert_comment</span>
									</c:if>
								</ul>
							</div>
						</c:forEach>
						<c:if test="${fn:length(list) > 5}">
							<span class="material-icons" id="nextArrIcon" onclick="selCmtPlus('cmt')">keyboard_arrow_right</span>
						</c:if>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>평가한 웹툰이 없습니다.</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			
			<!--  찜한 웹툰 -->
			<div class="result_view" id="favoritelist">
				<h2><span id="loginUser">${loginUser.u_name}님</span> 찜한 웹툰</h2>
				<c:choose>
					<c:when test="${favoritelist != null}">
					<c:if test="${fn:length(favoritelist) > 5}">
							<span class="material-icons" id="prevArrIcon" onclick="selCmtMinus('favorite')">keyboard_arrow_left</span>
					</c:if>
						<c:forEach var="i" begin="0" end="${fn:length(favoritelist) <= 5 ? fn:length(favoritelist)-1 : 4 }">
							<div class="listItem">
								<ul>
									<li><a href="/webtoon/detail?w_no=${favoritelist[i].w_no}"><img src="${favoritelist[i].w_thumbnail}" title="${favoritelist[i].w_title}"></a></li>
									<li>${favoritelist[i].w_title}</li>
									<c:if test="${favoritelist[i].c_rating != 0 && list[i].c_rating != null}">
										<span class="material-icons">grade</span>
										<li>${favoritelist[i].c_rating}</li>
									</c:if>
									<c:if test="${favoritelist[i].c_com != null && favoritelist[i].c_com != '' && favoritelist[i].c_com != ' '}">
										<span class="material-icons">insert_comment</span>
									</c:if>
								</ul>
							</div>
						</c:forEach>
						<c:if test="${fn:length(favoritelist) > 5}">
							<span class="material-icons" id="nextArrIcon" onclick="selCmtPlus('favorite')">keyboard_arrow_right</span>
						</c:if>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>찜한 웹툰이 없습니다.</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
		</div>
	</section>
	<jsp:include page="../template/footer.jsp"/>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">
let cmtIdx = 5;
let favoriteIdx= 5;
function selCmtMinus(type) {
	if((type=='cmt'? cmtIdx-5 : favoriteIdx-5) == 0){
		alert('처음입니다')
	}else if((type=='cmt'? cmtIdx-5 : favoriteIdx-5) >= 1){
		console.log('minus : '+(type=='cmt'? cmtIdx-5 : favoriteIdx-5))
		axios.get('/myPage',{
			params :{
				type : type,
				page : (type=='cmt'? cmtIdx - 5 :favoriteIdx-5)
			}
		}).then(function(res){
			//각 웹툰별 아이템 박스 만들기(json, delNum, addNum)
			if(type=='cmt'){
				makeListItem(cmt_list, res, 7, 2)
				cmtIdx--;
			} else{
				makeListItem(favoritelist, res, 7, 2)
				favoriteIdx--;
			}
			
		})
	}
}
function selCmtPlus(type) {
	axios.get('/myPage',{
		params : {
			type : type,
			page : (type=='cmt'? cmtIdx +1 :favoriteIdx +1 )
		}
	}).then(function(res) {
		if(res.data == 0){
			alert('마지막입니다')
		}else{
			//각 웹툰별 아이템 박스 만들기(넣을 div 박스, json, delNum, addNum)
			if(type=='cmt'){
				makeListItem(cmt_list, res, 2, 7)
				cmtIdx++;
			}else{
				makeListItem(favoritelist, res, 2, 7)
				favoriteIdx++;
			}
			
		}
	})
}
function makeListItem(result_view, res, delNum, addNum) {
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
	if(res.data.c_rating.toString().indexOf(".") == -1){
		grade.innerHTML = res.data.c_rating + ".0"
	}else {
		grade.innerHTML = res.data.c_rating
	}
	ulList.append(grade)
	//cmtIcon 만들기
	if(res.data.c_com != null){
		var cmtIcon = document.createElement('span')
		cmtIcon.classList.add('material-icons')
		cmtIcon.innerHTML = 'insert_comment'
		ulList.append(cmtIcon)
	}
	
	result_view.insertBefore(listItem,result_view.children[addNum])
	result_view.children[delNum].remove();
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