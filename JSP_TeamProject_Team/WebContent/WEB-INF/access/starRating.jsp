<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/webtoon/cmt" method="post" id="cmtFrm" name="cmtFrm" onsubmit="return chk()">
		<div class="startRadio">
	       <c:forEach begin="1" end="10" step="1" var="item">
	          <label class="startRadio__box">
	             <input type="radio" name="star" id="" onclick="score(${item})">
	             <span class="startRadio__img"><span class="blind"></span></span>
	          </label>
	       </c:forEach>
	       <input type="hidden" id="point" name="c_rating" value="${cmtFrm.u_no.value == '' ? '0.0' : myCmt.c_rating }" required>
	       <input type="hidden" id="cmtChk" name="cmtChk" value="0">
	    </div>
    </form>
    
    <script>
    
    if(cmtFrm.point.value != '0.0'){
        document.cmtFrm.star[Number(cmtFrm.point.value*2-1)].checked = true
     } // 남긴 별점 표시하기
     
     if(cmtFrm.cmt_btn.value == '수정하기'){
        console.log('누르기 전 ' + cmtFrm.cmtChk.value)
        cmtFrm.cmtChk.value = 1
        console.log('누른 후 ' + cmtFrm.cmtChk.value)
     }
     
     
    function score(star) { // 별점주기
        console.log('star : ' + star)
        console.log('star type' + typeof star)
        point.value = parseFloat(star)/2
        console.log('point.value : ' + point.value)
     }
    
    </script>
</body>
</html>