<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <style>
        .container {
            width: 600px; margin: 30px auto;
            border: 1px solid black;
        }
        h1 {
            text-align: center;
        }
        .user_info {
            text-align: center;
        }
        #name, #pw, #pw2, #email {
            width: 300px; height: 30px; margin: 10px;
        }
        button {
            margin-bottom: 20px; margin-top: 10px; width: 310px; padding: 5px;
            font-size: 1.1em; border: none; background-color: #ccb2e5;
            color: white;
        }
        #birth {
            width: 220px; margin: 10px;
        }
        #selected_genre a {color: red;}
        #selected_genre a:hover {focus:pointer;}
    </style>
</head>
<body>
    <div class="container">
        <h1>모두의 웹툰 일원 되기</h1>
        <div class="user_info">
	        <div class="err">${msg}</div>
	 
            <form id="frm" action="/join" method="post" onsubmit="return chk()">
            	<div id="genre_arr"></div>
            	<input type="text" name="id" id="id" placeholder="id" autofocus><br>
                <input type="password" name="pw" id="pw" placeholder="비밀번호"><br>
                <input type="password" name="pw2" id="pw2" placeholder="비밀번호 확인"><br>
                <input type="text" name="name" id="name" placeholder="이름" ><br>
                <input type="email" name="email" id="email" placeholder="메일"><br>
              	  생년월일 <input type="date" name="birth" id="birth"><br>
                <label><input type="radio" class="gender" name="gender" value="man">남자</label>
                <label><input type="radio" class="gender" name="gender" value="girl">여자</label><br>
                <div><input type="submit" value="회원가입"></div>
            </form>
        </div>
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
	</script>
</body>
</html>
