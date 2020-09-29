<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>비밀번호 재설정</title>
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
	width: 120px;
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
section .btn{
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
section .btn:hover {
	cursor: pointer;
	font-weight: bold;
}

section .newPw {
	margin-top:20px;
}
section #newPassword{
	margin-left: 26px;
}
section #chknewPassword{
	margin-left: 26px;
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
	            <form id="frm">
	            	<div>
		            	<span class="name">아이디</span>&nbsp;&nbsp;
		            	<input type="text" class="joinList" name="u_id" id="id" placeholder="아이디를 입력해주세요" autofocus required>
	            	</div>
	                <div>
						<span class="name">이메일</span>&nbsp;&nbsp;
		                <input type="email" class="joinList" name="email" id="email" placeholder="메일" required>
	                </div>
	                <div>
	                	<span class="name">생년월일</span>&nbsp;&nbsp;
	              	    <input type="date" class="joinList" name="birth" id="birth" required>
	                </div>
	                <div class="genderBox">
	                	<span class="name">성별</span>&nbsp;&nbsp;
		                <div class="genderGroup" id="genderGroup">
			                <label for="gender_male">남자</label>
			                <input type="radio" class="gender" name="gender" value="male" id="gender_male" required>
			                &nbsp;&nbsp;&nbsp;&nbsp;
			                <label for="gender_female">여자</label>
			                <input type="radio" class="gender" name="gender" value="female" id="gender_female">
		                </div>
		            </div>
		            <div id="frmBtn">
	                	<input class="btn" type="button" value="비밀번호 재설정" onclick="return chk()">
	            	</div>
	                
	                <div class="newPw">
		                <div id="newPw"></div>  
		                <div id="chknewPw"></div>
		                <div id="newPwbtn"></div>
	                </div>
	            </form>
	        </div>
        </section>
        <jsp:include page="../template/footer.jsp"/>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script>
    
		function chk(){
			
			var data = { 
				id : id.value,
				email : email.value,
				birth : birth.value,
				gender : frm.gender.value
			}
		
			axios.post('/newPw', data).then(function(res) {
			
				if(res.data == "success"){
					frm.u_id.readOnly=true
					email.readOnly=true
					birth.readOnly=true
					
					
					frmBtn.innerHTML = ""
					var label = document.createElement('span')
					label.setAttribute('class','name')
					var input = document.createElement('input')
					input.setAttribute('type','password')
					input.setAttribute('id','newPassword')
					input.setAttribute('class','joinList')
					var label2 = document.createElement('span')
					label2.setAttribute('class','name')
					var input2 = document.createElement('input')
					input2.setAttribute('type','password')
					input2.setAttribute('id','chknewPassword')
					input2.setAttribute('class','joinList')
					
					newPw.append(label)
					newPw.append(input)
					chknewPw.append(label2)
					chknewPw.append(input2)
					
					label.innerHTML = "비밀번호 재설정"	
					label2.innerHTML = "비밀번호 확인"
					
					var button = document.createElement('input')
					button.setAttribute('type','button')
					button.setAttribute('value','비밀번호 재설정')
					button.setAttribute('onclick','new_Pw()')
					button.setAttribute('class','btn')
					
					newPwbtn.append(button)
					
				}			
				else{
					alert('일치하는 회원정보가 없습니다 ')
				}
			})
			
				
		}
		
		
		function new_Pw() {
			console.log(newPassword.value)
			console.log(chknewPassword.value)
			if(newPassword.value.length < 5){
				alert('비밀번호는 5글자 이상이어야합니다.');
				frm.pw.focus();
			} else if(newPassword.value == chknewPassword.value){
		
			var data = { 
					id : id.value,
					pw :newPassword.value
				}
			
				axios.post('/newPw', data).then(function(res) {
					console.log(res.data)
					if(res.data=='success'){
						alert('비밀번호가 재설정 되었습니다')
						location.href = '/login'
					} else{
						alert('에러가 발생했습니다. 다시 시도해 주세요 ')
					}
				})
			} else{
				alert('비밀번호를 확인해주세요 ')
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
