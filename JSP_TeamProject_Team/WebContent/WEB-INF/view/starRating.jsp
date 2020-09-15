<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body, div, dl, dd, p, form, input {margin:0; padding:0;}
section{
		width:100%;
		background-color: #F8F8F8;
		margin: 0 auto;
 		border-top: 1px solid #EAEAEA;
 		text-align: center;
 	}
section #listBlock {width:90%; margin:0 auto;}  
section .itembox {margin:30px 80px; display: inline-block; position:relative;}
section dl {position:relative;}
section dt{position:absolute; bottom:60px; center:50%; left:50%; transform:translate(-50%,-50%); white-space:nowrap;}
section img {width:200px; margin-bottom:30px;}




section .startRadio {display: inline-block; overflow: hidden; height: 40px;}
section .startRadio:after { content: ""; display: block; position: relative; z-index: 10; height: 40px;
        background: url('/images/star_Radio.png');
        repeat-x 0 0; background-size: contain; pointer-events: none;}
        
section .startRadio__box { position: relative; z-index: 1; float: left; width: 20px; height: 40px;cursor: pointer;}
section .startRadio input { opacity: 0 !important; height: 0 !important;width: 0 !important;position: absolute !important;}
section .startRadio input:checked + .startRadio__img { background-color: #ffd700;}
section .startRadio__img { display: block; position: absolute;right: 0; width: 500px;height: 40px;pointer-events: none;} 
</style>
</head>
<body>
<div id="container">
<jsp:include page="../template/header.jsp"/>
<section>
	<div id="listBlock">
	<c:forEach items="${list}" var="item" begin="0" end="12" varStatus="status">
		<div class="itembox">
			<dl>
				<dt>${list[status.index].w_title }</dt>
				<dd><img src="${list[status.index].w_thumbnail }"></dd>
				<dd>${list[status.index].w_writer}</dd>
				<dd>
				   <div class="startRadio">
		               <c:forEach begin="1" end="10" step="1" var="rating_item">
	                   <label class="startRadio__box">
	                    	<input type="radio" name="star" onclick="score(${rating_item},${status.index },${list[status.index].w_no })"> 
	                    	<span class="startRadio__img"><span class="blind"></span></span>
	                    </label>
		                </c:forEach>
		             	<input type="hidden" name="c_rating" class="point" value="0" required>
		             	<input type="hidden" name="cmtChk" value="0">
		         	</div>
		      	</dd>
			</dl>
		</div>
	</c:forEach>
	</div>
</section>
<jsp:include page="../template/footer.jsp"/>
</div>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>


	function score(star,index,w_no) { // 별점주기
		var cmtChk = 0;
	   	if(document.getElementsByName("c_rating")[index].value != 0){
	   		 document.getElementsByName("cmtChk")[index].value = 1
		}
		cmtChk = document.getElementsByName("cmtChk")[index].value
	   	document.getElementsByName("c_rating")[index].value = parseFloat(star)/2
	   	var c_rating= parseFloat(star)/2
		var data = { 
			w_no : w_no,
			c_rating : c_rating,
			ratingPage : '1',
			cmtChk : cmtChk
		}
		
		axios.post('/webtoon/cmt', data).then(function(res) {
			console.log(res)
		})
	}
	
	
	//Javascript
	var count = 12;
	
	var index = 13;
	//스크롤 바닥 감지
	window.onscroll = function(e) {
	    //추가되는 임시 콘텐츠
	    //window height + window scrollY 값이 document height보다 클 경우,
	    if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
	    	//실행할 로직 (콘텐츠 추가)
	        //count++;
	        //var addContent = '<div class="block"><p>'+ count +'번째로 추가된 콘텐츠</p></div>';
	        //article에 추가되는 콘텐츠를 append
	      	//addContainer.innerHTML += addContent;
	       
	        
	  //      document.getElementsByClass("frm").end = 20;
	        
	        
	    }
	};
	
	
	
	//검색결과로 넘어가기
	function moveToResult() {
		if(event.keyCode == 13){
			var result = search.value
			location.href = '/searchResult?result='+result
		}
	}
	
  	//홈으로 가기
  	function goHome() {
  		location.href = '/home'
  	}
  	
  	//마이 페이지로 넘어가기
  	function moveToMyPage() {
		location.href = '/myPage?i_user=${loginUser.u_no}'
	}
  	//프로필로 넘어가기
  	function moveToProfile() {
		location.href = '/profile?i_user=${loginUser.u_no}'
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
</script>
</body>
</html>