<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<style>
section{
	width:100%;
	background-color: #F8F8F8;
	margin: 0 auto;
	border-top: 1px solid #EAEAEA;
	text-align: center;
}
section #frmContainer{
	margin: 30px auto;
	text-align: center;
}
section > h1 {
    text-align: center;
    margin-top: 40px;
    font-size: 2em;
}
section .name{
	color: gray; 
	font-weight: gray;
	font-weight: bold;
	width: 100px;
	display: inline-block;
	text-align: left;
}
section .joinList{
	width: 250px;
	height: 41px;
	background: #FFFFFF;
	padding-left: 30px; 
	padding-right:20px;
	border: 1px solid #4FA2C7;
	box-sizing: border-box;
	border-radius: 10px;
	margin: 5px auto;
}
section .genderBox{
}
section .genderGroup{
	display: inline-block;
	line-height:50px;
	width: 250px;
	height: 41px;
}
section input[type="submit"]{
	font-family: 'GmarketSansMedium', serif;
	border: none;
	border-radius: 10px;
	color: black;
	padding: 15px;
	padding-left: 30px;
	padding-right: 30px;
	background-color: lightgray;
	margin: 20px auto;
}
section input[type="submit"]:hover {
	cursor: pointer;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="container">
   		<jsp:include page="../template/header.jsp"></jsp:include>
     	<section>
	        <div id="frmContainer">
        		<h1>모두의 웹툰 일원 되기</h1>
		        <div class="err">${msg}</div>
	            <form id="frm" action="/join" method="post" onsubmit="return chk()">
	            	<input type="hidden" name="u_profile" value="${userInfo.u_profile == '' ? '' : userInfo.u_profile}">
	            	<input type="hidden" name="chkProfile" value="${userInfo.chkProfile == '' ? '' : userInfo.chkProfile}">
	            	<input type="hidden" name="u_joinPath" value="${userInfo.u_joinPath >= 2 ? userInfo.u_joinPath : 1}">
	            	<div>
		            	<span class="name">아이디</span>&nbsp;&nbsp;
		            	<input type="text" class="joinList" name="u_id" id="id" placeholder="아이디를 입력해주세요" value="${userInfo.u_id}" autofocus required>
	            	</div>
	            	<div>
	            		<span class="name">비밀번호</span>&nbsp;&nbsp;
	                	<input type="password" class="joinList" name="u_pw" id="pw" placeholder="비밀번호" value="${userInfo.u_password}" required>
					</div>
					<div>
						<span class="name">비밀번호 확인</span>&nbsp;&nbsp;
	                	<input type="password" class="joinList" name="u_pw2" id="pw2" placeholder="비밀번호 확인" value="${userInfo.u_password}" required>
					</div>
					<div>
						<span class="name">이름</span>&nbsp;&nbsp;
		                <input type="text" class="joinList" name="name" id="name" placeholder="이름" value="${userInfo.u_name}" required>
	                </div>
	                <div>
						<span class="name">이메일</span>&nbsp;&nbsp;
		                <input type="email" class="joinList" name="email" id="email" placeholder="메일" value="${userInfo.u_email}" required>
	                </div>
	                <div>
	                	<span class="name">생년월일</span>&nbsp;&nbsp;
	              	    <input type="date" class="joinList" name="birth" id="birth" required>
	                </div>
	                <div class="genderBox">
	                	<span class="name">성별</span>&nbsp;&nbsp;
		                <div class="genderGroup">
			                <label for="gender_male">남자</label>
			                <input type="radio" class="gender" name="gender" value="male" id="gender_male" required>
			                &nbsp;&nbsp;&nbsp;&nbsp;
			                <label for="gender_female">여자</label>
			                <input type="radio" class="gender" name="gender" value="female" id="gender_female">
		                </div>
	                </div>
	                <div id="frmBtn">
	                	<input type="submit" value="회원가입">
	                </div>
	            </form>
	        </div>
        </section>
        <jsp:include page="../template/footer.jsp"/>
	</div>
    <script>
    var check_count = document.getElementsByName('gender').length;
		function chk(){
			const korean = /[^가-힣]/	;				//한글 정규식 : /[가-힣]/ : 한글이 들어가있으면 true반환. ^(not)붙여서 한글만 있는경우 false 반환
			const email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
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
		//웹툰 상세페이지 가기
	  	function moveToDetail(w_no) {
	  		location.href = '/webtoon/detail?w_no='+w_no
	  	}
	  	//로그인으로 넘어가기
    	function moveToLogin() {
			location.href = '/login'
		}
    	//회원가입으로 넘어가기
    	function moveToJoin() {
			location.href = '/join'
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
