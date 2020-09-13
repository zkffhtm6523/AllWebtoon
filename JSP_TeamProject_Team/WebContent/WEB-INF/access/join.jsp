<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<style>
	section {
		width:100%;
		margin: 0px auto;
		background-color: #F8F8F8;
 		border-top: 1px solid #EAEAEA;
 		text-align: center;
	}
    section > h1 {
        text-align: center;
        margin-top: 40px;
        font-size: 2em;
    }
    section > .user_info {
        text-align: center;
        margin: 50px auto;
    }
    section #frm{
    	width:400px;
        margin: 0 auto;
    }
    section #id, #name, #pw, #pw2, #email {
        width: 300px; height: 30px; margin: 10px;
    }
    section button {
        margin-bottom: 20px; margin-top: 10px; width: 310px; padding: 5px;
        font-size: 1.1em; border: none; background-color: #ccb2e5;
        color: white;
    }
    section #birth {
        width: 220px; margin: 10px;
    }
    section #selected_genre a {color: red;}
    section #selected_genre a:hover {focus:pointer;}
</style>
</head>
<body>
	<div id="container">
   		<jsp:include page="../template/header.jsp"></jsp:include>
     	<section>
        <h1>모두의 웹툰 일원 되기</h1>
	        <div class="user_info">
		        <div class="err">${msg}</div>
	            <form id="frm" action="/join" method="post" onsubmit="return chk()">
	            	<div id="genre_arr"></div>
	            	<input type="text" name="u_id" id="id" placeholder="아이디를 입력해주세요" autofocus><br>
	                <input type="password" name="u_pw" id="pw" placeholder="비밀번호"><br>
	                <input type="password" name="u_pw2" id="pw2" placeholder="비밀번호 확인"><br>
	                <input type="text" name="name" id="name" placeholder="이름" ><br>
	                <input type="email" name="email" id="email" placeholder="메일"><br>
	              	  생년월일 <input type="date" name="birth" id="birth"><br>
	                <label><input type="radio" class="gender" name="gender" value="male">남자</label>
	                <label><input type="radio" class="gender" name="gender" value="female">여자</label><br>
	                <div><input type="submit" value="회원가입"></div>
	            </form>
	        </div>
        </section>
        <jsp:include page="../template/footer.jsp"/>
	</div>
    <script>
    	var genres_arr = new Array();
    
    	function sel_genre(){
			var sel_text = genres.options[genres.selectedIndex].text;
			if(genres_arr.length <3 && genres_arr.indexOf(sel_text) == -1){
				genres_arr.push(sel_text);
				showlist();
			}
		//	genre_arr.innerHTML = "<input type='hidden' name='genre_arr' value="+ genres_arr +">";
			inputhtml(genres_arr);
    	}
    	function cancleSel(i){
    		genres_arr.splice(i,1);
    		showlist();
    	}
    	function showlist(){
    		selected_genre.innerHTML = ""
			for(var i=0; i<genres_arr.length; i++){
				selected_genre.innerHTML += genres_arr[i] + "<a onclick='cancleSel("+i+")' >x</a>"
			}
    	}
    	function inputhtml(arr){
    		genre_arr.innerHTML = "";
    		for(var i=0; i<arr.length; i++){
    			genre_arr.innerHTML += "<input type='hidden' name='genre_arr' value="+ arr[i] +">";
    		}
    	}
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
	</script>
</body>
</html>
