<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<style type="text/css">
	section{
		width:100%;
		background-color: #F8F8F8;
		margin: 0 auto;
 		border-top: 1px solid #EAEAEA;
 		text-align: center;
 	}
 	section .frmContainer{margin: 15px auto; text-align: center;}
 	section .profileImg{width: 240px; height:220px; border-radius: 50%; object-fit: cover; overflow: hidden;}
 	section .name{
 		color: gray; 
 		font-weight: gray;
 		font-weight: bold;
 		width: 75px;
 		display: inline-block;
 		text-align: left;
 	}
 	section .updList{
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
	section .updList[type="search"]{
		font-family: 'GmarketSansMedium', serif;
		line-height: normal;
		padding-top: 6px;
	}
	section .updList:hover{cursor: pointer;}
	section .imgFile[type="file"]{
		font-family: 'GmarketSansMedium', serif ;
		width:254px;
		height:30px;
		text-align: center;
		margin: 5px auto;
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
	}
	section .box-file-input label{
		font-family: 'GmarketSansMedium', serif ;
	    display:inline-block;
	    background: #4FA2C7
	}
	section #btnBox{
		margin: 30px auto;
		margin-top: 15px;
	}
	section #frmBtn{
		font-size: 1.05em;
	}
	section #frmBtn:hover {
		cursor: pointer;
	}
</style>
</head>
<body>
<div id	="container">
	<jsp:include page="../template/header.jsp"/>
	<section>
		<h1>프로필 변경</h1>
		<div class="printImage">
			<c:choose>
				<c:when test="${loginUser.u_profile eq ''}">
					<img class="profileImg" src="/images/u_profile/default_image.jpg" alt="프로필 설정 가기">
				</c:when>
				<c:when test="${loginUser.chkProfile eq 'http'}">
					<img class="profileImg" src="${loginUser.u_profile}" alt="프로필 설정 가기">
				</c:when>
				<c:otherwise>
					<img class="profileImg" src="/images/u_profile/user/${loginUser.u_no}/${loginUser.u_profile}" alt="프로필 설정 가기">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="frmContainer">
			<form id="frm" action="/profile" method="post" enctype="multipart/form-data" onsubmit="return chk()">
				<div>
					<span class="name">사진 변경</span>&nbsp;&nbsp;
					<input type="file" name="profile_img" accept="image/*" value="이미지 선택" class="imgFile">
				</div>
				<c:if test="${loginUser.u_joinPath ==1 }">
				<div>
					<span class="name">아이디</span>&nbsp;&nbsp;
					<input type="search" name="updId" value="${loginUser.u_id}" class="updList" readonly>
				</div>
				</c:if>
			
				<div>
					<span class="name">이름</span>&nbsp;&nbsp;
					<input type="search" name="updName" value="${loginUser.u_name}" class="updList">
				</div>
				<c:if test="${loginUser.u_joinPath ==1 }">
					<div>
						<span class="name">비밀번호 변경</span>&nbsp;&nbsp;
						<input type="password" name="password" value="" class="updList" placeholder="변경하시려면 입력해주세요">
					</div>
					<div>
						<span class="name">비밀번호 확인</span>&nbsp;&nbsp;
						<input type="password" name="password2" value="" class="updList">
					</div>
				</c:if>
				<div>
					<span class="name">이메일</span>&nbsp;&nbsp;
					<input type="search" name="updEmail" value="${loginUser.u_email}" class="updList" ${loginUser.u_joinPath != 1 ? 'readonly' : ''}>
				</div>
				<div>
					<span class="name">성별</span>&nbsp;&nbsp;
					<input type="search" name="updGender" value="${loginUser.gender_name}" class="updList" readonly>
				</div>
				<div>
					<span class="name">생년월일</span>&nbsp;&nbsp;
					<input type="date" name="updBrith" value="${loginUser.u_birth}" class="updList">
				</div>
			<!-- 	<div>
					<span class="name">가입일자</span>&nbsp;&nbsp;
					<input type="search" name="updR_dt" value="${loginUser.r_dt}" class="updList" readonly>
				</div>
				<div>
					<span class="name">수정일자</span>&nbsp;&nbsp;

					<input type="search" name="updM_dt" value="${loginUser.m_dt}" class="updList" readonly>
				</div> -->
				<div id="btnBox">
					<input type="submit" value="업데이트" id="frmBtn">
				</div>
			</form>
		</div>
	</section>
	<jsp:include page="../template/footer.jsp"/>
</div>
</body>
<script type="text/javascript">

function chk(){
	const email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

	if(frm.password.value.length >= 1 && frm.password.value.length < 5){
		alert('비밀번호는 5글자 이상이어야합니다.');
		frm.pw.focus();
		return false;
	} 
	if(frm.password.value != frm.password2.value){
		alert('비밀번호를 확인해주세요'); 
		frm.pw.focus();
		return false;
	} 
	if(!email.test(frm.updEmail.value)){			//이메일 정규식을 만족하지 않을 경우.
		alert('이메일을 확인해주세요');
		frm.email.focus();
		return false;
	}
	if(frm.updBirth.value == '' || frm.updBirth.value == null){
		alert('생년월일을 확인해주세요');
		frm.birth.focus();
		return false;
	}
}

</script>
</html>
