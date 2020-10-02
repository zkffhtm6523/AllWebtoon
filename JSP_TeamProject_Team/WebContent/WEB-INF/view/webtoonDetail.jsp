<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Cute+Font&family=Noto+Sans+KR&family=Noto+Serif+KR:wght@600&display=swap" rel="stylesheet">
<title>웹툰 상세 페이지</title>
<style>
   @font-face {font-family: 'GmarketSansMedium';src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');font-weight: normal;font-style: normal;}
   section{
   		width:100%;
   		background-color: #F8F8F8;
   		margin: 0 auto;
   		border-top: 1px solid #EAEAEA;
   	}
   section a {text-decoration: none; color: black;}
   section ul {list-style-type: none;}
   section #detailContainer {
   		width: 80%;
		margin: 30px auto;
   }
   section #detailContainer #webtoonContainer #webtoonSummary{
   		width: 80%;
   		margin: 0 auto;
   }
   section #detailContainer #webtoonContainer #webtoonSummary ul{
   		list-style-type: none;
   		display: inline-block;
   		vertical-align: top;
   		margin: 0;
   		margin-top: 5px;
   		position: relative;
   }
   section #detailContainer #webtoonContainer #webtoonSummary ul #favorite_title{
   		position: absolute;
   		font-weight: bold;
   		font-size: 1.4em;
   		color: gray;
   		top: 43%;
   		margin-right: 2%;
   }
   section #detailContainer #webtoonContainer #webtoonSummary ul #favorite{
   		position: absolute;
   		font-size: 2em;
   		color: red;
   		top: 41%;
   		margin-left: 5%;
   		cursor:pointer;
   }
   section #detailContainer #webtoonContainer #webtoonSummary ul #title{
   		font-style: normal;
   		font-weight: bold;
   		font-size: 40px;
   		line-height: 52px;
   		width:60%;
   		display:inline-block;
   		text-overflow: ellipsis;
    	overflow: hidden;
    	white-space: nowrap;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary ul #title a{
   		color: black;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary ul #writer{
   		font-style: normal;
   		font-weight: bold;
   		font-size: 25px;
   		line-height:34px;
  		width:500px;
   		text-overflow: ellipsis;
    	overflow: hidden;
    	white-space: nowrap;
   }
   section #detailContainer #webtoonContainer #webtoonSummary ul #platform {
   		font-style: normal;
   		font-weight: bold;
   		font-size: 25px;
   		line-height: 34px;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary ul #platform a{
   		color:#65B832;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary ul #genre{
   		font-style: normal;
   		font-weight: 300;
   		font-size: 18px;
   		line-height: 21px;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary ul li{
   		margin: 5px;
   }
   section #detailContainer #webtoonContainer #webtoonSummary #thumbnail{
   		display: inline-block;
   		width: 210px;
   		height: 200px;
   		margin-top: 2%;	
   }
   section #detailContainer #webtoonContainer #webtoonSummary #thumbnail img {
   		width: 100%;
   		height: 100%;
   		border-radius: 5%;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary .startRadio {
   		display: inline-block;
   		overflow: hidden;
   		height: 40px;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary .startRadio:after {
   		content: "";
   		display: block;
   		position: relative;
   		height: 40px;
   		background: url("/images/star_Radio.png") repeat-x 0 0;
   		background-size: contain;
   		pointer-events: none;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary .startRadio__box {
   		position: relative;
   		z-index: 0;
   		float: left;
   		width: 20px;
   		height: 40px;
   		cursor: pointer;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary .startRadio input {
   		opacity: 0 !important;
   		height: 0 !important;
   		width: 0 !important;
   		position: absolute !important;
   	}
   section #detailContainer #webtoonContainer #webtoonSummary .startRadio input:checked + .startRadio__img {
   		background-color: #ffd700;
   }
   section #detailContainer #webtoonContainer #webtoonSummary .startRadio__img {
   		display: block;
   		position: absolute;
   		right: 0;
   		width: 500px;
   		height: 40px;
   		pointer-events: none;
   		z-index:-1
   	}
   section #detailContainer #webtoonContainer #story {
   		width: 80%;
   		margin: 20px auto;
   		font-size: 1.1em;
   		line-height: 2em;
   }
   section #detailContainer #webtoonContainer #story #tosite{
   		font-size: 0.9em;
   		
   }
   section #detailContainer #webtoonContainer #story #tosite a{
   		color: #0c65c6;
   		font-weight: bold;
   }
   section #detailContainer #comment{text-align: center;}
   section #detailContainer #comment #cmtFrm{
   		margin: 10px;
   		border-bottom: 1px solid darkgray;
   }
   section #detailContainer #comment #cmtFrm #cmt{
   		border-radius: 20px;
   		border: 2px solid #4FA2C7;
   		width: 80%;
   		height: 80px;
   		padding-left: 30px;
   		font-size: 1em;
   		display: block;
   		margin: 0 auto;
   }
   section #detailContainer #comment #cmtFrm #cmt:focus {outline:none;}
   section #detailContainer #comment #cmtFrm #submit{text-align: center;}
   section #detailContainer #comment #cmtFrm #cmt_btn {
   		margin: 10px;
   		width: 118px;
   		border: none;
   		height: 50px;
   		background-color: #4FA2C7;
   		color: white;
   		font-size: 1em;
   		border-radius:10px
   	}
   	section #detailContainer .cmt_list{
   		width: 90%;
   		margin: 0 auto;
   		vertical-align:top;
   		position: relative;
   	}
   	section #detailContainer .cmt_list .cmtItem{
   		background-color:rgba(131,165,180,0.42);
   		position: relative;
   		vertical-align:top;
   		display: inline-block;
   		width: 30%;
   		height: 80%;
   		border-radius: 20px;
   		margin: 2% auto;
   		padding-bottom: 2%;
   	}
   	section #detailContainer .cmt_list .cmtItem img {
   		width: 53px;
   		height: 49px;
   		border-radius: 50%;
   	}
   	section #detailContainer .cmt_list .cmtItem #cmt_list{
   		padding-left: 50px;
   	}
   	section #detailContainer .cmt_list .cmtItem ul li{
   		display: inline-block;
   		vertical-align: top;
   	}
   	section #detailContainer .cmt_list .cmtItem #cmt_list :nth-child(2){
	   	position:absolute;
		top : 18px;
		left: 44%;	
   	}
   	section #detailContainer .cmt_list .cmtItem #cmt_list :nth-child(3){
	   	position:absolute;
		color: gold;
		font-size: 1.5em;
		top : 40px;
		left: 40%;	
   	}
   	section #detailContainer .cmt_list .cmtItem #cmt_list :nth-child(4){
	   	position:absolute;
		top : 42px;
		left: 50%;	
   	}
   	section #detailContainer .cmt_list .cmtItem #cmt_list_com{
   		width: 80%;
   		height: 150px;
   		margin: 0 auto;
   		word-break: break-all;
   		overflow: hidden;
   	}
   	section #detailContainer .cmt_list .cmtItem #cmt_list_com li{
   		display: inline;
   	}
   	section #detailContainer .cmt_list .cmtItem:nth-child(4){
   		position:absolute;
   		background-color: steelblue;
   		width: 80px;
   		right: 0%;
   		cursor: pointer;
   	}
   	section #detailContainer .cmt_list .cmtItem:nth-child(4) #open{
   		position: absolute;
   		top: 40%;
   		right: 18%;
   	}
   	section #detailContainer .cmt_list .modal .modal__content .swiper-wrapper .cmt_list{
   		position: relative;
   		height: 90%;
   		padding-top: 3%;
   		padding-left: 10%;
   		padding-right: 10%;
   	}
   	section #detailContainer .cmt_list .modal .modal__content .swiper-wrapper .cmt_list li{
   		display: inline;
   	}	
   	section #detailContainer .cmt_list .modal .modal__content .swiper-wrapper .cmt_list .cmt_list_name{
   		position: absolute;
   		left: 27%;
   		top: 9%;
   		margin-right: 2%;
   	}
   	section #detailContainer .cmt_list .modal .modal__content .swiper-wrapper .cmt_list #modalStar{
   		position: absolute;
   		color: gold;
   		font-size : 1.2em;
   		right: 20%;
   		top: 8.5%;
   	}
   	section #detailContainer .cmt_list .modal .modal__content .swiper-wrapper .cmt_list .cmt_list_rating{
   		position: absolute;
   		right: 10%;
   		top: 9%;
   	} 
   	section #detailContainer .cmt_list .modal .modal__content .swiper-wrapper .cmt_list .cmt_list_com{
   		display: block;
   		width: 100%;
   		height:78%;
   		white-space: normal;
   		margin-top: 5%;
   		word-break: break-all;
   		overflow: auto;
	}
   	.blind { position: absolute; overflow: hidden; margin: -1px;padding: 0;width: 1px;height: 1px;border: none;clip: rect(0, 0, 0, 0);}
	.swiper-container {height: 100%;}
    .swiper-slide {display: flex !important;justify-content: center;align-items: center;font-size: 2rem;}
</style>
<link rel="stylesheet" href="/css/modal.css" />
<link rel="stylesheet" href="/css/swiper-bundle.min.css">
</head>
<body>
	<div id="container">
		<jsp:include page="../template/header.jsp"></jsp:include>
	   	<section>
	      <div id="detailContainer">
	      	<div id="webtoonContainer">
	      		<div id="webtoonSummary">
            	<div id="thumbnail"><img src="${data.w_thumbnail }"></div>
            	<ul>
               <li id="platform"><a href="/searchResult?result=${data.w_plat_name }">${data.w_plat_name }</a></li>
               <li id="writer">작가 : 
               <c:forEach items="${writers}" var="item">
               	<a href="/searchResult?result=${item}&writer=y">${item}</a>
               </c:forEach>
               </li>
               <li id="title"><a href="/searchResult?result=${data.w_title }">${data.w_title }</a></li>
					<c:if test="${loginUser != null}">
					<span id="favorite_title">찜</span>
					<span id="favorite" class="material-icons" onclick="toggleFavorite()" title="찜하기">
					<c:if test="${data.is_favorite == 1 }">favorite</c:if>
					<c:if test="${data.is_favorite == 0 }">favorite_border</c:if>
					</span>
					</c:if>
               <li id="genre"><a href="/searchResult?result=${data.genre_name }">${data.genre_name }</a>
				</li>
               <li>
               <li id="starGrade">
               	<div class="startRadio">
	               	<c:forEach begin="1" end="10" step="1" var="item">
	                   <label class="startRadio__box">
	                      <input type="radio" name="star" id="" onclick="score(${item})" ${loginUser == null ? 'disabled':'' }>
	                      <span class="startRadio__img"><span class="blind"></span></span>
	                   </label>
                	</c:forEach>
                  </div>
               </li>
            </ul>
            </div>
             <div id="story">${data.w_story}
		         <div id="tosite"><a href="${data.w_link }" target="_blank">보러가기</a></div>
             </div>
	      </div>
	      <!-- 댓글 부분 -->
	      <div id="comment">
	         <form action="/webtoon/cmt" method="post" id="cmtFrm" name="cmtFrm" onsubmit="return chk()">
	            <input type="hidden" id="point" name="c_rating" value="${cmtFrm.u_no.value == '' ? '0.0' : myCmt.c_rating }" required>
	            <input type="hidden" id="cmtChk" name="cmtChk" value="0">
	               <!-- 댓글 남기기 -->
            	<input type="text" id="cmt" name="c_com" placeholder="댓글을 남겨주세요" value="${myCmt.c_com }" onclick="login_chk()" ${loginUser.u_no==null? 'readonly' : '' }>
		            <!-- 완료 후 보내기 -->
	            <input type="submit" id="cmt_btn" value="${myCmt.c_rating == '' || loginUser == null ? '등록하기' : '수정하기' }">
	            <div><input type="hidden" name="w_no" value="${data.w_no}"></div>
	            <div><input type="hidden" name="genre_name" value="${data.genre_name }"></div>
	            <input type="hidden" id="u_no" name="u_no" value="${loginUser.u_name }">
	         </form>
	      </div>
	      <!-- 다른 사람들의 댓글 -->
	      <div class="cmt_list">
	      	<c:if test="${fn:length(cmtList) > 0}">
				<c:forEach var="i" begin="0" end="${fn:length(cmtList) > 3 ? 2 : fn:length(cmtList) - 1}">
					<div class="cmtItem">
						<ul id="cmt_list">
							<li id="cmt_list_profile"><img class="pImg" src="${cmtList[i].u_profile}"></li>
							<li id="cmt_list_name">${cmtList[i].u_name}</li>
							<span class="material-icons">grade</span>
							<li id="cmt_list_rating">${cmtList[i].c_rating}</li>
						</ul>
						<div id="cmt_list_com">${cmtList[i].c_com}</div>
					</div>
				</c:forEach>
				<c:if test="${fn:length(cmtList) > 3}">
					<div class="cmtItem" title="더보기" onclick="openModal(${data.w_no})"><button id="open" onclick="openModal(${data.w_no})">+${fn:length(cmtList) - 3}</button></div>
					<div class="modal hidden ">
				       <div class="modal__overlay" id="modalOverlay"></div>
				       <div class="modal__content">
						   <!-- Swiper -->
						  <div class="swiper-container">
						    <div class="swiper-wrapper" id="ajaxModalContainer"></div>
						    <!-- Add Arrows -->
							<div class="swiper-button-next"></div>
						  	<div class="swiper-button-prev"></div>
						  </div>
				       </div>
				    </div>  
				</c:if>
			</c:if>
	      </div>
	      </div>
      </section>
      <jsp:include page="../template/footer.jsp"/>
   </div>
   <!-- Scripts -->
	<script src="/js/modal.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
	<script>
		var openButton = document.getElementById("open")
		var modal = document.querySelector(".modal")
		const closeModal = () => {
		    modal.classList.add("hidden")
		}
		function openModal(w_no){
	    	modal.classList.remove("hidden")
	    	console.log('모달 열렸음')
	    	axios.get('/webtoon/detail',{
				params : {
					w_no : w_no,
					ajaxChk : 1
				}
			}).then(function(res) {
				console.log(res.data)
				for (var i in res.data) {
					console.log(res.data[i])
					//makeSwiper_slide 반복문 돌려서 만들기
					makeSwiper_slide(res.data[i], ajaxModalContainer)
				}
				//모달창 좌우 버튼 누를 시 반복,
				var mySwiper = new Swiper('.swiper-container', {
				 	 loop: true,
				  // 네비게이션 방향키
				  navigation: {
				    nextEl: '.swiper-button-next',
				    prevEl: '.swiper-button-prev'
				  }
				})
				//회색 배경 누를 시 모달 닫기
				modalOverlay.addEventListener("click", closeModal)
				})
		}
		function makeSwiper_slide(arr, container){
			//swiper_slide 만들고 클래스 추가
			var swiper_slide = document.createElement('div')
			swiper_slide.classList.add('swiper-slide')
			//cmt_list 만들고 클래스 추가
			var cmt_list = document.createElement('ul')
			cmt_list.classList.add('cmt_list')
			//cmt_list_rating 만들고 클래스 추가
			var cmt_list_rating = document.createElement('li')
			cmt_list_rating.classList.add('cmt_list_rating')
			cmt_list_rating.innerText = parseFloat(arr.c_rating).toFixed(1)
		
			//cmt_list_com 만들고 클래스 추가
			var cmt_list_com = document.createElement('li')
			cmt_list_com.classList.add('cmt_list_com')
			cmt_list_com.innerText = arr.c_com
			//cmt_list_profile 만들고 클래스 추가			
			var cmt_list_profile = document.createElement('li')
			cmt_list_profile.classList.add('cmt_list_profile')
			//pImg 만들고 클래스, src 추가
			var pImg = document.createElement('img')
			pImg.classList.add('pImg')
			pImg.src = arr.u_profile
			cmt_list_profile.append(pImg)
			//cmt_list_name 만들고 클래스 추가
			var cmt_list_name = document.createElement('li')
			cmt_list_name.classList.add('cmt_list_name')
			cmt_list_name.innerText = arr.u_name
			
			container.append(swiper_slide)
			swiper_slide.append(cmt_list)
			cmt_list.append(cmt_list_profile)
			cmt_list.append(cmt_list_name)
			if(arr.c_rating != 0 && arr.c_rating != null && arr.c_rating != ''){
				var star = document.createElement('span')
				star.classList.add('material-icons')
				star.id = 'modalStar'
				star.innerText = 'grade'
				cmt_list.append(star)
			}
			cmt_list.append(cmt_list_rating)
			cmt_list.append(cmt_list_com)
		}
		
		
		
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