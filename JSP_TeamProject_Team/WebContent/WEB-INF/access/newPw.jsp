<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="/css/access/newPw.css" rel="stylesheet" type="text/css" media="all" />
	<title>비밀번호 재설정</title>
</head>
<body>
	<div id="container">
   		<jsp:include page="../template/header.jsp"></jsp:include>
     	<section>
     		<div id="sec_container">
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
	</script>
</body>
</html>
