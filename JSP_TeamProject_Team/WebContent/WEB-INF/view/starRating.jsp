<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
section{
		width:100%;
		background-color: #F8F8F8;
		margin: 0 auto;
 		border-top: 1px solid #EAEAEA;
 		text-align: center;
 	}
section #listBlock {width:90%; margin:0 auto;}  
section .itembox {width:45%; margin: 15px; display: inline-block;}
section .imgBox{display: inline-block; width: 200px; height: 180px;  margin-right: 30px;}
section .imgBox dd{margin: 0 auto; width: 100%; height: 100%;}
section .imgBox dd img{width: 100%; height: 100%; border-radius: 8px;}
section .textBox{display: inline-block; width: 280px; height: 180px; vertical-align: top; 
position: relative;}
section .textBox dd{margin: 15px auto; text-align: left;}
section .textBox dt{margin: 25px auto; text-align: left;}
section .textBox .frmDd{position:absolute; height: 50px;width: 100%; bottom: 0px;}
section .startRadio {display: inline-block; overflow: hidden; height: 40px;}
section .startRadio:after { content: ""; display: block; position: relative; z-index: 10; height: 40px;
        background: url("data:image/png;base64,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")
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
	<c:forEach items="${list}" var="item" begin="0" end="11" varStatus="status">
		<div class="itembox">
			<dl class="imgBox">
				<dd><img src="${item.w_thumbnail }"></dd>
			</dl>
			<dl class="textBox">
				<dt>타이틀 : ${item.w_title }</dt>
				<dd>작가 : ${item.w_writer}</dd>
				<dd class="frmDd">
					<form class="frm" action="/webtoon/cmt" method="post" >
					   <div class="startRadio">
			               <c:forEach begin="1" end="10" step="1" var="rating_item">
		                   <label class="startRadio__box">
		                     <!-- <input type="radio" name="star" onclick="score(${rating_item},${status.index })">  -->
		                    	<input type="radio" name="star" onclick="score(${rating_item},${status.index },${item.w_no })"> 
		                    	<span class="startRadio__img"><span class="blind"></span></span>
		                    </label>
			                </c:forEach>
			             	<input type="hidden" name="c_rating" class="point" value="0" required>
			             	<input type="hidden" name="w_no" value="${item.w_no }">
			             	<input type="hidden" name="cmtChk" value="0">
			             	<input type="hidden" name="ratingPage" value="1">
			         	</div>
			      	</form>
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