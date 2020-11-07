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
	<link href="/css/view/webtoonDetail.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" href="/css/modal.css" />
	<link rel="stylesheet" href="/css/swiper-bundle.min.css">
	<title>웹툰 상세 페이지</title>
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
	                   <c:choose>
	                   <c:when test="${loginUser!=null }">
	                      <input type="radio" name="star" id="" onclick="score(${item})">
	                   </c:when>
	                   <c:otherwise>
	                      <input type="radio" name="star" id="star_${item}" onclick="alert_login(${item})" >
	                   </c:otherwise>
	                   </c:choose>
	                      <span class="startRadio__img"><span class="blind"></span></span>
	                   </label>
                	</c:forEach>
                  </div>
                  <c:if test="${loginUser != null }">평균 별점 : ${aveScore} (${ numScore}명)</c:if>
               </li>
            </ul>
            </div>
             <div id="story">${data.w_story}
		         <div id="tosite"><a href="${data.w_link }" target="_blank">보러가기</a></div>
             </div>
	      </div>
	      <c:if test="${loginUser != null}">
	      <div class="result_view" id="rec_list">
				<h2>이 웹툰과 비슷한 작품</h2>
				<c:choose>
					<c:when test="${rec_list != null && fn:length(rec_list) != 0}">
						<c:forEach var="i" begin="0" end="${fn:length(rec_list)-1 }">
							<div class="listItem">
								<ul>
									<li><a href="/webtoon/detail?w_no=${rec_list[i].w_no}"><img src="${rec_list[i].w_thumbnail}" title="${rec_list[i].w_title}"></a></li>
									<li class="title">${rec_list[i].w_title}</li>
								</ul>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>아직 충분한 평가가 없어 추천작을 찾을 수 없어요.ㅠㅠ</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			</c:if>
	      <!-- 댓글 부분 -->
	      <div id="comment">
	         <form action="/webtoon/cmt" method="post" id="cmtFrm" name="cmtFrm" onsubmit="return chk()">
	            <input type="hidden" id="point" name="c_rating" value="${cmtFrm.u_no.value == '' ? '0.0' : myCmt.c_rating }" required>
	            <input type="hidden" id="cmtChk" name="cmtChk" value=${myCmt.c_rating == 0.0 ? "0" : "1" }>
	               <!-- 댓글 남기기 -->
            	<input type="text" id="cmt" name="c_com" placeholder="댓글을 남겨주세요(100자 이내)" maxlength="100" value="${myCmt.c_com }" onclick="login_chk()" ${loginUser.u_no==null? 'readonly' : '' }>
		            <!-- 완료 후 보내기 -->
	            <input type="submit" id="cmt_btn" value="${myCmt.c_rating == '' || loginUser == null ? '등록하기' : '수정하기' }">
	            <div><input type="hidden" name="w_no" value="${data.w_no}"></div>
	            <div><input type="hidden" name="genre_name" value="${data.genre_name }"></div>
	            <input type="hidden" id="u_no" name="u_no" value="${loginUser.u_name }">
	         </form>
	      </div>
	      <!-- 다른 사람들의 댓글 -->
	      <c:if test="${fn:length(cmtList) > 0}">
	      <div class="cmt_list">
				<c:forEach var="i" begin="0" end="${fn:length(cmtList) > 3 ? 2 : fn:length(cmtList) - 1}">
					<div class="cmtItem">
						<ul id="cmt_list">
							<li id="cmt_list_profile"><img class="pImg" src="${cmtList[i].u_profile}"></li>
							<li id="cmt_list_name">${cmtList[i].u_name}</li>
							<span class="material-icons">grade</span>
							<li id="cmt_list_rating">${cmtList[i].c_rating}</li>
						</ul>
						<div><button id="cmt_list_com" onclick="openModal(${cmtList[i].w_no})"><xmp id="cmt_com">${cmtList[i].c_com}</xmp></button></div>
					</div>
				</c:forEach>
				<c:if test="${fn:length(cmtList) > 3}">
					<div class="cmtItem" title="더보기" onclick="openModal(${data.w_no})"><button id="open" onclick="openModal(${data.w_no})">+${fn:length(cmtList) - 3}</button></div>
				</c:if>
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
	      	</div>
			</c:if>
	      </div>
      </section>
      <jsp:include page="../template/footer.jsp"/>
   </div>
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
					if(res.data[i].c_com != null){
						console.log(res.data[i])
						//makeSwiper_slide 반복문 돌려서 만들기
						makeSwiper_slide(res.data[i], ajaxModalContainer)
					}
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
      
      
      
      function alert_login(star){
    	  alert('먼저 로그인 해주세요')
    	  document.getElementById('star_'+star).checked = false
      }
      
      function score(star) { // 별점주기
         console.log('star : ' + star)
         console.log('star type' + typeof star)
         point.value = parseFloat(star)/2
         console.log('point.value : ' + point.value)
      }
   
      function chk() {
         if(cmtFrm.u_no.value == '') {
            alert('먼저 로그인 해주세요') // 로그인 체크
            return false
         }else if (point.value <= 0) { // 별점 체크
            alert('별점을 입력해 주세요')
            return false
         } 
         alert('평가가 등록되었습니다')
      }
      
      function login_chk(){
    	  if(${loginUser.u_no == null}){
    		  alert('로그인해주세요')
    	  }
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
  	
  	
  	
  	
  	let recIdx = 5;
  	function selCmtMinus() {
  		if(recIdx-5 == 0){
  			alert('처음입니다')
  		}else if(redIdx-5 >= 1){
  			console.log('minus : '+ recIdx-5)
  			axios.get('/myPage',{
  				params :{
  					type : type,
  					page : recIdx - 5 
  				}
  			}).then(function(res){
  				//각 웹툰별 아이템 박스 만들기(json, delNum, addNum)
				makeListItem(rec_list, res, 7, 2)
				recIdx--;
  				
  			})
  		}
  	}
  	
  	function selCmtPlus(type) {
  		axios.get('/myPage',{
  			params : {
  				type : type,
  				page : recIdx +1
  			}
  		}).then(function(res) {
  			if(res.data == 0){
  				alert('마지막입니다')
  			}else{
  				//각 웹툰별 아이템 박스 만들기(넣을 div 박스, json, delNum, addNum)
  				makeListItem(rec_list, res, 2, 7)
  				recIdx++;
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
  		liTitle.setAttribute('class','title')
  		liTitle.innerText = res.data.w_title
  		ulList.append(liTitle)
  		
  		
  		result_view.insertBefore(listItem,result_view.children[addNum])
  		result_view.children[delNum].remove();
  	}
   </script>
</body>
</html>