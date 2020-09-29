<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Cute+Font&family=Noto+Sans+KR&family=Noto+Serif+KR:wght@600&display=swap" rel="stylesheet">
<title>웹툰 상세 페이지</title>
<style>
   #btn_login {text-decoration: none; color: black;}
   section{width:100%;background-color: #F8F8F8;margin: 0 auto;border-top: 1px solid #EAEAEA;}
   .detail {margin: 0 auto; width: 1200px; padding: 10px; display: flex;}
   .detail a {text-decoration: none;}
   .detail > ul {list-style-type: none;}
   .detail > ul > li {float:left;}
   #thumbnail img {width: 230px;height: 210px; border-radius: 5%;margin: 20px;}
   #platform{width: 60%; margin-top: 15px;}
   #writer {width: 60%; margin-top: 10px;}
   #title{width: 60%; margin-top: 10px;}
   #genre {width: 60%; margin-top: 10px;}
   .startRadio {width: 100%; margin-top: 10px; display: inline-block;}
   #story {width: 80%; margin-top: 10px;}
   #title {font-style: normal;font-weight: bold;font-size: 40px;line-height: 52px;}
   #title a {color: black;}
   #writer {font-style: normal;font-weight: bold;font-size: 25px;line-height: 34px;
  	width:500px;
   	text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
   }
   #writer a {color: black; }
   #platform {font-style: normal;font-weight: bold;font-size: 25px;line-height: 34px;}
   #platform a {color:#65B832;}
   #genre {font-style: normal;font-weight: 300;font-size: 18px;line-height: 21px;}
   #genre a {color: black;}
   #story {font-style: normal;font-weight: 500;font-size: 23px;line-height: 27px;}
   .comment {margin: 50px auto; padding: 10px; display:inline;}
   #cmtFrm {margin: 10px;}
   .startRadio__box, #comment, #submit{text-align: center;}
   #cmt {border-radius: 20px; border: 2px solid #4FA2C7; width: 923px; height: 117px; padding-left: 30px; font-size: 1.2em;} 
   #cmt:focus {outline:none;}
   #cmt_btn {margin: 10px; width: 118px; border: none; height: 50px; background-color: #4FA2C7; color: white;font-size: 1.1em; border-radius:20px}
    @font-face {font-family: 'GmarketSansMedium';src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');font-weight: normal;font-style: normal;}
   .blind { position: absolute; overflow: hidden; margin: -1px;padding: 0;width: 1px;height: 1px;border: none;clip: rect(0, 0, 0, 0);}
   .startRadio {display: inline-block; overflow: hidden; height: 40px;}
   .startRadio:after { content: ""; display: block; position: relative; height: 40px;background: url("/images/star_Radio.png") repeat-x 0 0; background-size: contain; pointer-events: none;}
   .startRadio__box { position: relative; z-index: 0; float: left; width: 20px; height: 40px;cursor: pointer;}
   .startRadio input { opacity: 0 !important; height: 0 !important;width: 0 !important;position: absolute !important;}
   .startRadio input:checked + .startRadio__img { background-color: #ffd700;}
   .startRadio__img { display: block; position: absolute;right: 0; width: 500px;height: 40px;pointer-events: none; z-index:-1}
   hr {width: 95%; margin-top: 20px;margin-bottom: 20px;}
   .cmt_list {margin: 10px;}
	.cmt_list .cmtItem {display: inline-block;width: 355px;height:255px; background-color:rgba(131,165,180,0.42);border-radius: 20px; position: relative;}
	.cmt_list .cmtItem ul {list-style-type: none;}
	.cmt_list .cmtItem:not(:first-child) {	margin-left: 4px;}
	.cmt_list .cmtItem img {width: 53px;height: 49px; border-radius: 50%;}
	.cmt_list .cmtItem .moreCmt {width: 100%;height:100%;display:flex;justify-content:center;align-items:center;}
	.swiper-container {height: 100%;}
    .swiper-slide {display: flex !important;justify-content: center;align-items: center;font-size: 3rem;} 
</style>
<link rel="stylesheet" href="/css/modal.css" />
<link rel="stylesheet" href="/css/swiper-bundle.min.css">
</head>
<body>
   <div id="container">
	<jsp:include page="../template/header.jsp"></jsp:include>
	   	<section>
	      <div class="detail">
            <ul>
               <li id="thumbnail"><img src="${data.w_thumbnail }"></li>
               <li id="platform"><a href="/searchResult?result=${data.w_plat_name }">${data.w_plat_name }</a></li>
               <li id="writer">작가 : 
               <c:forEach items="${writers}" var="item">
               	<a href="/searchResult?result=${item}&writer=y">${item}</a>
               </c:forEach>
               </li>
               <li id="title"><a href="/searchResult?result=${data.w_title }">${data.w_title }</a></li>
               <li id="genre"><a href="/searchResult?result=${data.genre_name }">${data.genre_name }</a>
					<c:if test="${loginUser != null}">
					<span id="favorite" class="material-icons" onclick="toggleFavorite()" style="color: red">
					<c:if test="${data.is_favorite == 1 }">favorite</c:if>
					<c:if test="${data.is_favorite == 0 }">favorite_border</c:if>
					</span>
					</c:if>
				</li>
               <li>
               	<div class="startRadio">
	               	<c:forEach begin="1" end="10" step="1" var="item">
	                   <label class="startRadio__box">
	                      <input type="radio" name="star" id="" onclick="score(${item})" ${loginUser == null ? 'disabled':'' }>
	                      <span class="startRadio__img"><span class="blind"></span></span>
	                   </label>
                	</c:forEach>
                  </div>
               </li>
               <li id="story">${data.w_story}</li>
	         <li><div id="tosite"><a href="${data.w_link }" target="_blank">보러가기</a></div></li>
            </ul>
	      </div>
	      <!-- 댓글 부분 -->
	      <div class="comment">
	         <form action="/webtoon/cmt" method="post" id="cmtFrm" name="cmtFrm" onsubmit="return chk()">
	            <input type="hidden" id="point" name="c_rating" value="${cmtFrm.u_no.value == '' ? '0.0' : myCmt.c_rating }" required>
	            <input type="hidden" id="cmtChk" name="cmtChk" value="0">
	               <!-- 댓글 남기기 -->
	            <div id="comment">
	            	<input type="text" id="cmt" name="c_com" placeholder="댓글을 남겨주세요" value="${myCmt.c_com }" onclick="login_chk()" ${loginUser.u_no==null? 'readonly' : '' }>
		            <!-- 완료 후 보내기 -->
		            <input type="submit" id="cmt_btn" value="${myCmt.c_rating == '' || loginUser == null ? '등록하기' : '수정하기' }">
	            </div>
	            <div><input type="hidden" name="w_no" value="${data.w_no }"></div>
	            <div><input type="hidden" name="genre_name" value="${data.genre_name }"></div>
	            <input type="hidden" id="u_no" name="u_no" value="${loginUser.u_name }">
	         </form>
	      </div>
	      <!-- 다른 사람들의 댓글 -->
	      <div class="cmt_list">
	      	<c:if test="${fn:length(cmtList) > 0}">
	      	<hr>
				<c:forEach var="i" begin="0" end="${fn:length(cmtList) > 3 ? 2 : fn:length(cmtList) - 1}">
					<div class="cmtItem">
						<ul id="cmt_list">
							<li id="cmt_list_rating">${cmtList[i].c_rating}</li>
							<li id="cmt_list_com">${cmtList[i].c_com}</li>
							<li id="cmt_list_profile">
								<c:choose>
									<c:when test="${cmtList[i].u_profile eq null}">
										<img class="pImg" src="/images/u_profile/default_image.jpg">
									</c:when>
									<c:when test="${cmtList[i].u_profile.substring(0,4) eq 'http'}">
										<img class="pImg" src="${cmtList[i].u_profile}">
									</c:when>
									<c:otherwise>
										<img class="pImg" src="/images/u_profile/user/${cmtList[i].u_no}/${cmtList[i].u_profile}">
									</c:otherwise>
								</c:choose>
							</li>
							<li id="cmt_list_name">${cmtList[i].u_name}</li>
						</ul>
					</div>
				</c:forEach>
				<c:if test="${fn:length(cmtList) > 3}">
					<button id="open">+${fn:length(cmtList) - 3}</button>
					<div class="modal hidden ">
				       <div class="modal__overlay"></div>
				       <div class="modal__content">
						   <!-- Swiper -->
						  <div class="swiper-container">
						    <div class="swiper-wrapper">
						       <div class="swiper-slide">
							      <ul id="cmt_list">
									<li id="cmt_list_rating">${cmtList[3].c_rating}</li>
									<li id="cmt_list_com">${cmtList[3].c_com}</li>
									<li id="cmt_list_profile">
									<c:choose>
										<c:when test="${cmtList[3].u_profile eq null}">
											<img class="pImg" src="/images/u_profile/default_image.jpg">
										</c:when>
										<c:when test="${cmtList[3].u_profile.substring(0,4) eq 'http'}">
											<img class="pImg" src="${cmtList[3].u_profile}">
										</c:when>
										<c:otherwise>
											<img class="pImg" src="/images/u_profile/user/${cmtList[3].u_no}/${cmtList[3].u_profile}">
										</c:otherwise>
									</c:choose>
								</li>
								<li id="cmt_list_name">${cmtList[3].u_name}</li>
							</ul>
						</div>
						      <div class="swiper-slide">Slide 2</div>
						      <div class="swiper-slide">Slide 3</div>
						      <div class="swiper-slide">Slide 4</div>
						      <div class="swiper-slide">Slide 5</div>
						      <div class="swiper-slide">Slide 6</div>
						      <div class="swiper-slide">Slide 7</div>
						      <div class="swiper-slide">Slide 8</div>
						      <div class="swiper-slide">Slide 9</div>
						      <div class="swiper-slide">Slide 10</div>
						    </div>
						    <!-- Add Arrows -->
						    <div class="swiper-button-next"></div>
						    <div class="swiper-button-prev"></div>
						  </div>
				          <button>❌</button>
				       </div>
				    </div>  
				</c:if>
			</c:if>
	      </div>
      </section>
      <jsp:include page="../template/footer.jsp"/>
   </div>
   <!-- Scripts -->
	<script src="/js/modal.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
	<script>	
		var mySwiper = new Swiper('.swiper-container', {
		  // Optional parameters
		  loop: true,
		  
		  // Navigation arrows
		  navigation: {
		    nextEl: '.swiper-button-next',
		    prevEl: '.swiper-button-prev',
		  },
		})
		
      function star_score() { // 남긴 별점 표시하기
    	  var score = document.querySelector("#point").value
    	  if(score != '0') {
    	  	document.getElementsByName('star')[cmtFrm.point.value*2-1].checked = true
    	  }
      }
      star_score()
      
      if(cmtFrm.cmt_btn.value == '수정하기'){
         console.log('누르기 전 ' + cmtFrm.cmtChk.value)
         cmtFrm.cmtChk.value = 1
         console.log('누른 후 ' + cmtFrm.cmtChk.value)
      }
      
      function score(star) { // 별점주기
    	 if(${loginUser.u_no==null}){
    		 alert('로그인')
    		 
    	 }else{
         console.log('star : ' + star)
         console.log('star type' + typeof star)
         point.value = parseFloat(star)/2
         console.log('point.value : ' + point.value)
    	 }
      }
   
      function chk() {
         if(cmtFrm.u_no.value == '') {
            alert('먼저 로그인 해주세요') // 로그인 체크
            return false
         }else if (point.value <= 0) { // 별점 체크
            alert('별점을 입력해 주세요')
            return false
         } 
         alert('댓글이 등록되었습니다')
      }
      
      function login_chk(){
    	  if(${loginUser.u_no == null}){
    		  alert('로그인해주세요')
    	  }
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
     
  	function toggleFavorite() {
		console.log('favorite : ' + (favorite.innerText.trim() == 'favorite'))
		
		let parameter = {
			params:{
				w_no: ${data.w_no}
			}
		} 
		
		var icon = favorite.innerText.trim()
		
		switch(favorite.innerText.trim()){
		case 'favorite':
			parameter.params.proc_type = 'del'
			break;
		case 'favorite_border':
			parameter.params.proc_type = 'ins'
			break;
		}
		
		axios.get('/webtoon/favorite', parameter).then(function(res) {
			console.log(res)
			if(res.data == 1) {
				favorite.innerText = icon == 'favorite' ? 'favorite_border' : 'favorite'
			}
		})
	}
   </script>
</body>
</html>