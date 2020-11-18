<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="/css/access/join.css" rel="stylesheet" type="text/css" media="all" />
	<title>회원가입</title>
</head>
<body>
	<div id="container">
   		<jsp:include page="../template/header.jsp"></jsp:include>
     	<section>
     		<div id="sec_container">
	        <div id="frmContainer">
        		<h1>All 웹툰 일원 되기</h1>
		        <div class="err">${msg}</div>
	            <form id="frm" action="/join" method="post" onsubmit="return chk()">
	            	<input type="hidden" name="u_profile" value="${userInfo.u_profile == '' ? '' : userInfo.u_profile}">
	            	<input type="hidden" name="chkProfile" value="${userInfo.chkProfile == '' ? '' : userInfo.chkProfile}">
	            	<input type="hidden" name="u_joinPath" value="${userInfo.u_joinPath >= 2 ? userInfo.u_joinPath : 1}">
	           
	            	<div>
	            		<c:if test = "${userInfo.u_id == '' || userInfo == null}">
		            	<span class="name">아이디</span>&nbsp;&nbsp;
		            	</c:if>
		            	<input type=${userInfo.u_id == '' || userInfo == null ? "text" : "hidden"} class="joinList" name="u_id" id="id" placeholder="아이디를 입력해주세요" value="${userInfo.u_id == null ? data.u_id : userInfo.u_id}" ${userInfo.u_id != '' && userInfo != null? 'readonly' : ''} autofocus required >
	            	</div>
	            	<div>
	            		<c:if test = "${userInfo.u_password == '' || userInfo == null}">
	            		<span class="name">비밀번호</span>&nbsp;&nbsp;
	            		</c:if>
	                	<input type=${userInfo.u_password == '' || userInfo == null? "password" : "hidden"} class="joinList" name="u_pw" id="pw" placeholder="비밀번호" value="${userInfo.u_password == null ? data.u_password : userInfo.u_password}" required>
					</div>
					<div>
						<c:if test = "${userInfo.u_password == '' || userInfo == null}">
						<span class="name">비밀번호 확인</span>&nbsp;&nbsp;
						</c:if>
	                	<input type=${userInfo.u_password == '' || userInfo == null ? "password" : "hidden"} class="joinList" name="u_pw2" id="pw2" placeholder="비밀번호 확인" value="${userInfo.u_password == null ? data.u_password : userInfo.u_password}" required>
					</div>
					
					<div>
						<span class="name">이름</span>&nbsp;&nbsp;
		                <input type="text" class="joinList" name="name" id="name" placeholder="이름" value="${userInfo.u_name == null ? data.u_name : userInfo.u_name}" required>
	                </div>
	                <div>
						<span class="name">이메일</span>&nbsp;&nbsp;
		                <input type="email" class="joinList" name="email" id="email" placeholder="메일" value="${userInfo.u_email == null ? data.u_email : userInfo.u_email}" ${userInfo.u_email != '' && userInfo != null ? 'readonly' : ''} required>
	                </div>
	                <div>
	                	<span class="name">생년월일</span>&nbsp;&nbsp;
	              	    <input type="date" class="joinList" name="birth" id="birth" required>
	                </div>
	                <div class="genderBox">
	                	<span class="name">성별</span>&nbsp;&nbsp;
		                <div class="genderGroup">
			                <label for="gender_male">남자</label>
			                <input type="radio" class="gender" name="gender" value="male" id="gender_male" ${userInfo.gender_name eq '남성' ? 'checked':''} required>
			                &nbsp;&nbsp;&nbsp;&nbsp;
			                <label for="gender_female">여자</label>
			                <input type="radio" class="gender" name="gender" value="female" id="gender_female" ${userInfo.gender_name eq '여성' ? 'checked':''}>
		                </div>
	                </div>
	                <div id="frmBtn">
	                	<input type="submit" value="회원가입">
	                </div>
	                <div class="snsTitle">
	                	<h3>SNS 회원가입</h3>
	                </div>
	                <div class="snsbtn">
		                <img class="snsimg" src="/images/login_logo/kakao_smallBtn.png" id="kakao" title="카카오 회원가입" onclick="goKakao()">
	            		<img class="snsimg" src="/images/login_logo/naver_smallBtn.png" id="naver" title="네이버 회원가입" onclick="goNaver()">
	            		<img class="snsimg" src="/images/login_logo/google_smallBtn.png" id="google" title="구글 회원가입" onclick="goGoogle()">
            		</div>  	
	            </form>
	        </div>
	        </div>
        </section>
        <jsp:include page="../template/footer.jsp"/>
	</div>
<script>
	var check_count = document.getElementsByName('gender').length;
	function chk(){
		const korean = /[^가-힣]/	;				//한글 정규식 : /[가-힣]/ : 한글이 들어가있으면 true반환. ^(not)붙여서 한글만 있는경우 false 반환
		const email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		if(${userInfo.u_joinPath == 1}){
			if(frm.id.value.length < 5) {
				alert('아이디는 5글자 이상이어야합니다.');
				frm.id.focus();
				return false;
			} 
			if(frm.pw.value.length < 5){
				alert('비밀번호는 5글자 이상이어야합니다.');
				frm.pw.focus();
				return false;
			} 	
			if(frm.pw.value != frm.pw2.value){
				alert('비밀번호를 확인해주세요'); 
				frm.pw.focus();
				return false;
			} 
		}
		if(korean.test(frm.nm.value)){				//한글 정규식을 만족하지 않을 경우.(이름에 한글이 아닌 문자가 있을 경우)
			alert('이름을 다시 입력해주세요');
			frm.nm.focus();
			return false;
		} 
		if(!email.test(frm.email.value)){			//이메일 정규식을 만족하지 않을 경우.
			alert('이메일을 확인해주세요');
			frm.email.focus();
			return false;
		}
		if(frm.birth.value == '' || frm.birth.value == null){
			alert('생년월일을 확인해주세요');
			frm.birth.focus();
			return false;
		}
	}
	
	function goKakao() {
		location.href = "/SNSController?snsPlatform=kakao"
	}
	function goNaver() {
		location.href = "/SNSController?snsPlatform=naver"
	}
	function goGoogle() {
		location.href = "/SNSController?snsPlatform=google"
	}
</script>
</body>
</html>
