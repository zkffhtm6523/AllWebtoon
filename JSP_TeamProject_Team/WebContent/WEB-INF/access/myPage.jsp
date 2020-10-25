<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
<title>나의 페이지</title>
<style type="text/css">
section{
	width:100%;
	background-color: #F8F8F8;
	margin: 0 auto;
	background-color: #F8F8F8;
	border-top: 1px solid #EAEAEA;
	text-align: center;
}
section #myPageContainer{
	width: 1300px;	
	margin: 30px auto;
}
section #myPageContainer .printImage{
 	width: 80%;
 	margin: 20px auto;
 	border: 3px solid #EAEAEA;
	border-radius: 8px;
	padding: 10px;
}
section #myPageContainer ul{
 	vertical-align: top;
 	margin: 0px auto;
}

section #myPageContainer .result_view .show_all {
	float:right;
	font-size: 15px;
	color:grey;
	cursor:pointer;
	margin-right:150px;
	margin-top:10px;
}

section #myPageContainer .result_view{
 	width: 80%;
 	margin: 20px auto;
 	border: 3px solid #EAEAEA;
	border-radius: 8px;
	padding: 10px;
	padding-bottom: 10px;
	position: relative;
}
section #myPageContainer .result_view > #prevArrIcon{
 	position: absolute;
 	top: 50%;
 	left: 2%;
 	cursor: pointer;
}
section #myPageContainer .result_view > #nextArrIcon{
 	position: absolute;
 	top: 50%;
 	right: 2%;
 	cursor: pointer;
}
section #myPageContainer .result_view .listItem ,.listItem{
 	position: relative;
 	display: inline-block;
 	vertical-align : top;
 	top: 0px;
 	margin: 0 auto;
 	display: inline-block;
}

section #myPageContainer .result_view .listItem #del_Icon, #del_Icon{
	text-align:right;
}

section #myPageContainer .result_view .listItem #del_Icon:hover ,#del_Icon:hover{
	cursor:pointer;
	color:red;
}
section #myPageContainer .result_view .nonListItem{
	width: 96%;
	margin: 0 auto;
}
section #myPageContainer .result_view .nonListItem h2{
	text-align: center;
	font-size: 1.3em;	
}
section #myPageContainer .result_view .listItem ul, .listItem ul{
 	padding-left: 10px;
 	padding-right: 10px;
 	position: relative;
 	list-style-type:none;
 	
}

section #myPageContainer .result_view .listItem ul :nth-child(3), .listItem ul :nth-child(3){
	position:absolute;
	color: gold;
	font-size: 1.3em;
	bottom: 5px;
	left: 10px;
}
section #myPageContainer .result_view .listItem ul :nth-child(4), .listItem ul :nth-child(4){
 	position:relative;
 	display: inline-block;
 	vertical-align: top;
 	line-height: 30px;

 	left: -20px;
}
section #myPageContainer .result_view .listItem ul :nth-child(5), .listItem ul :nth-child(5){
	position:absolute;
	color: gray;
	font-size: 1.3em;
	bottom: 5px;
	right: 10px;
}
section #myPageContainer .result_view .listItem ul li a img, .listItem ul li a img{
 	border-radius: 8px;
 	width: 125px; height: 100px;
}
section .profileImg{
	width: 160px;
 	height:160px;
 	border-radius: 50%; 
 	object-fit: cover; 
 	overflow: hidden;
 	line-height: 30px;
 }
section >  #myPageContainer > .result_view > h2{
	text-align: left;
	margin-left: 20px;
}
section .name{
	color: gray; 
	font-weight: gray;
	font-weight: bold;
	width: 75px;
	display: inline-block;
	text-align: left;
}
 section #myPageContainer .printImage ul{
 	display: inline-block;
 	vertical-align: top;
 	position:relative;
 	top: 8px;
 	list-style-type: none;
 	text-align: left;
 }
 section #myPageContainer .printImage ul li{
 	margin: 15px auto;
 }
 
#loginUser{color: gray; font-weight: bold; font-size: 1.1em;}

.title{
		width:125px;
   		text-overflow: ellipsis;
    	overflow: hidden;
    	white-space: nowrap;
}

       #modal_button {
            all:unset; 
            background-color: steelblue; 
            color: white; 
            padding: 5px 20px; 
            border-radius: 5px; 
            cursor: pointer;
        }
        .modal {
            position: fixed; 
            top: 0; 
            left: 0; 
            width: 100%; 
            height: 100%; 
            display: flex; 
            justify-content: center; 
            align-items: center;
            z-index:100;
        }
        .modal__overlay {
            background-color: rgba(0, 0, 0, 0.6);
            width: 100%; 
            height: 100%; 
            position: absolute;
        }
        .modal__content {
            background-color: #F8F8F8; 
            position: relative; 
            border-radius: 10px; 
            width: 80%; 
            height: 80%;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
           	overflow:auto;
           	padding:50px;
        	magin:50px;
        }
		.modal__content #modal_ul{
			margin:15px 10px;
		}
		.modal__content hr{
			width: 1000px;
			color: #EAEAEA;
			border-width: 2px;
		}
        .hidden {
            visibility:hidden;
        }
</style>

</head>
<body>
<div id="container">
	<jsp:include page="../template/header.jsp"/>
	<section>
		<div id="myPageContainer">
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
			<ul>
				<li>
					<span class="name">이름</span>&nbsp;
				 	${loginUser.u_name}
				 </li>
				<li>
					<span class="name">이메일</span>&nbsp;
					${loginUser.u_email}
				</li>
				<li>
					<span class="name">생년월일</span>&nbsp;
					${loginUser.u_birth}
				</li>
				<li>
					<span class="name">가입경로</span>&nbsp;
					<c:choose>
					<c:when test="${loginUser.u_joinPath eq 1}">모두의 웹툰</c:when>
					<c:when test="${loginUser.u_joinPath eq 2}">카카오</c:when>
					<c:when test="${loginUser.u_joinPath eq 3}">네이버</c:when>
					<c:when test="${loginUser.u_joinPath eq 4}">구글</c:when>
					</c:choose>
				</li>
			</ul>
			</div>
			<!-- 조회한 웹툰 -->
			<div class="result_view" id="view_list">
				<h2><span id="loginUser">${loginUser.u_name}님</span> 최근 조회 웹툰</h2>
				<c:choose>
					<c:when test="${recentWebtoon != null}">
						<c:forEach var="i" begin="0" end="${fn:length(recentWebtoon) <= 5 ? fn:length(recentWebtoon)-1 : 4 }">
							<div class="listItem">
								<ul>
									<li><a href="/webtoon/detail?w_no=${recentWebtoon[i].w_no}"><img src="${recentWebtoon[i].w_thumbnail}" title="${recentWebtoon[i].w_title}"></a></li>
									<li class="title">${recentWebtoon[i].w_title}</li>
									<c:if test="${recentWebtoon[i].c_rating != 0}">
										<span class="material-icons">grade</span>
										<li>${recentWebtoon[i].c_rating}</li>
									</c:if>
									<c:if test="${recentWebtoon[i].c_com != null}">
										<span class="material-icons">insert_comment</span>
									</c:if>
								</ul>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>조회한 웹툰이 없습니다.</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			
			<div class="result_view" id="cmt_list">
				<h2><span id="loginUser">${loginUser.u_name}님</span> 평가 웹툰 <span id="cmt_show_all" class="show_all" onclick="show_all('cmt')">전체보기</span></h2>
				<c:choose>
					<c:when test="${list != null}">
						<c:if test="${cmtlistSize > 5 }">
							<span class="material-icons" id="prevArrIcon" onclick="selCmtMinus('cmt')">keyboard_arrow_left</span>
						</c:if>
							
						<c:forEach var="i" begin="0" end="${fn:length(list) <= 5 ? fn:length(list)-1 : 4 }">
							<div class="listItem" id="item_${list[i].w_no}">
								<ul>
									
									<li><div id="del_Icon" onclick="delWebtoon(${list[i].w_no})">x</div><a href="/webtoon/detail?w_no=${list[i].w_no}"><img src="${list[i].w_thumbnail}" title="${list[i].w_title}"></a></li>
									<li><div class="title">${list[i].w_title}</div></li>
									<c:if test="${list[i].c_rating != 0 && list[i].c_rating != null}">
										<span class="material-icons">grade</span>
										<li>${list[i].c_rating}</li>
									</c:if>
									<c:if test="${list[i].c_com != null && list[i].c_com != '' && list[i].c_com != ' '}">
										<span class="material-icons">insert_comment</span>
									</c:if>
								</ul>
							</div>
						</c:forEach>
						<c:if test="${cmtlistSize > 5}">
							<span class="material-icons" id="nextArrIcon" onclick="selCmtPlus('cmt')">keyboard_arrow_right</span>
						</c:if>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>평가한 웹툰이 없습니다.</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<!--  찜한 웹툰 -->
			<div class="result_view" id="favoritelist">
				<h2><span id="loginUser">${loginUser.u_name}님</span> 찜한 웹툰 <span id="favorite_show_all" class="show_all" onclick="show_all('favorite')">전체보기</span></h2>
				<c:choose>
					<c:when test="${favoritelist != null}">
					<c:if test="${fn:length(favoritelist) > 5}">
							<span class="material-icons" id="prevArrIcon" onclick="selCmtMinus('favorite')">keyboard_arrow_left</span>
					</c:if>
						<c:forEach var="i" begin="0" end="${fn:length(favoritelist) <= 5 ? fn:length(favoritelist)-1 : 4 }">
							<div class="listItem">
								<ul>
									<li><a href="/webtoon/detail?w_no=${favoritelist[i].w_no}"><img src="${favoritelist[i].w_thumbnail}" title="${favoritelist[i].w_title}"></a></li>
									<li class="title">${favoritelist[i].w_title}</li>
									<c:if test="${favoritelist[i].c_rating != 0 && favoritelist[i].c_rating != null}">
										<span class="material-icons">grade</span>
										<li>${favoritelist[i].c_rating}</li>
									</c:if>
									<c:if test="${favoritelist[i].c_com != null && favoritelist[i].c_com != '' && favoritelist[i].c_com != ' '}">
										<span class="material-icons">insert_comment</span>
									</c:if>
								</ul>
							</div>
						</c:forEach>
						<c:if test="${fn:length(favoritelist) > 5}">
							<span class="material-icons" id="nextArrIcon" onclick="selCmtPlus('favorite')">keyboard_arrow_right</span>
						</c:if>
					</c:when>
					<c:otherwise>
						<div class="nonListItem">
							<h2>찜한 웹툰이 없습니다.</h2>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="modal hidden">
	       <div class="modal__overlay"></div>
	       <div class="modal__content"></div>
	    </div>  
	</section>
	<jsp:include page="../template/footer.jsp"/>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">

	const openButton_cmt = document.getElementById('cmt_show_all')
	const openButton_favorite = document.getElementById('favorite_show_all')
	const modal = document.querySelector(".modal")
	const overlay = modal.querySelector(".modal__overlay")
	const modal_content = document.querySelector('.modal__content')
	//const closeBtn = modal.querySelector("button")
	const openModal = () => {
	    modal.classList.remove("hidden")
	}
	const closeModal = () => {
	    modal.classList.add("hidden")
	    location.reload();
	}
	//closeBtn.addEventListener("click", closeModal)
	openButton_cmt.addEventListener("click", openModal)
	openButton_favorite.addEventListener("click", openModal)
	//openButton[1].addEventListener("click", openModal)
	overlay.addEventListener("click", closeModal)

function show_all(type){
	axios.get('/myPage',{
		params :{
			type : type,
			yn_modal : 'y'
		}
	}).then(function(res){
		console.log(res.data)
		modal_content.innerHTML = ""	
		var h1_user = document.createElement('h1')
		var loginUserTxt = loginUser[0].innerText
		var span_user = document.createElement('span')
		span_user.id = 'loginUser'
		span_user.innerText = loginUserTxt
		h1_user.append(span_user)
		h1_user.append(' 평가 웹툰')
		modal_content.append(h1_user)
		var hr = document.createElement('hr')
		modal_content.append(hr)
		
		res.data.forEach(function (item){
			
			var listItem = document.createElement('div')
			listItem.setAttribute('class','listItem')
			listItem.setAttribute('id','item_'+item.w_no)
			var ul = document.createElement('ul')
			ul.setAttribute('id','modal_ul')
			var li1 = document.createElement('li')
			var del_icon = document.createElement('div')
			del_icon.setAttribute('id','del_Icon')
			del_icon.setAttribute('onclick','deleteInmodal('+item.w_no+')')
			del_icon.innerText = 'x'
			if(type=='cmt'){
				li1.append(del_icon)
			}
			var a = document.createElement('a')
			a.setAttribute('href',"/webtoon/detail?w_no="+item.w_no)
			var img = document.createElement('img')
			img.setAttribute('src',item.w_thumbnail)
			img.setAttribute('title',item.w_title)
			a.append(img)
			li1.append(a)
			ul.append(li1)
			var li2 = document.createElement('li')
			var title = document.createElement('div')
			title.setAttribute('class','title')
			title.innerText = item.w_title
			li2.append(title)
			ul.append(li2)
			
			if(item.c_rating != 0 && item.c_rating != null){
				var rating_icons = document.createElement('span')
				rating_icons.setAttribute('class','material-icons')
				rating_icons.innerText='grade'
				var li3 = document.createElement('li')
				li3.innerText=item.c_rating
				ul.append(rating_icons)
				ul.append(li3)
			}
			
			if(item.c_com != null && item.c_com != '' && item.c_com != ' '){
				var cmt_icons = document.createElement('span')
				cmt_icons.setAttribute('class','material-icons')
				cmt_icons.innerText='insert_comment'
				ul.append(cmt_icons)
			}
			listItem.append(ul)
			
			modal_content.append(listItem)
			/*
			modal_content.innerHTML += 
				
				'<div class="listItem" id="item_'+item.w_no + '">'
				+'<ul>'
				+'<li>'
			if(type=='cmt'){
				modal_content.innerHTML += '<div id="del_Icon" onclick="deleteInmodal('+item.w_no+')">x</div>'
			}
				
			modal_content.innerHTML += 
				
				'<a href="/webtoon/detail?w_no='+item.w_no+'"><img src="'+item.w_thumbnail+'" title="'+item.w_title+'"></a></li>'
				+'<li><div class="title">'+item.w_title+'</div></li>'
				+'<c:if test="'+item.c_rating+' != 0 && '+item.c_rating +'!= null}">'
				+'	<span class="material-icons">grade</span>'
				+'	<li>'+item.c_rating+'</li>'
				+'</c:if>'
				+'<c:if test="'+item.c_com +'!= null && '+item.c_com +'!= '' && '+item.c_com +'!= ' '}">'
				+'	<span class="material-icons">insert_comment</span>'
				+'</c:if>'
				+'</ul>'
				+'</div>'*/
		})
		
		
	})
}
	
function deleteInmodal(w_no){
	delWebtoon(w_no,'modal')
	show_all('cmt')
}

let cmtIdx = 4;
let favoriteIdx= 4;

function selCmtMinus(type) {
	if((type=='cmt'? cmtIdx-5 : favoriteIdx-5) < 0){
		alert('처음입니다')
		if(type=='cmt'){
			cmtIdx=4;
		}else{
			favoriteIdx=4;
		}
	}else if((type=='cmt'? cmtIdx-5 : favoriteIdx-5) >= 0){
		console.log('minus : '+(type=='cmt'? cmtIdx-5 : favoriteIdx-5))
		axios.get('/myPage',{
			params :{
				type : type,
				page : (type=='cmt'? cmtIdx-5 :favoriteIdx-5)
			}
		}).then(function(res){
			//각 웹툰별 아이템 박스 만들기(json, delNum, addNum)
			if(type=='cmt'){
				makeListItem(cmt_list, res, 7, 2)
				cmtIdx--;
			} else{
				makeListItem(favoritelist, res, 7, 2)
				favoriteIdx--;
			}
		})
	}
}

function selCmtPlus(type) {
	console.log("plus : "+(type=='cmt'? cmtIdx+1 : favoriteIdx+1))
	axios.get('/myPage',{
		params : {
			type : type,
			page : (type=='cmt'? cmtIdx+1 :favoriteIdx+1 )
		}
	}).then(function(res) {
		console.log(res.data)
		if(res.data == 0){
			alert('마지막입니다')
		}else{
			//각 웹툰별 아이템 박스 만들기(넣을 div 박스, json, delNum, addNum)
			if(type=='cmt'){
				makeListItem(cmt_list, res, 2, 7)
				cmtIdx++;
			}else{
				makeListItem(favoritelist, res, 2, 7)
				favoriteIdx++;
			}
			
		}
	})
}

function makeListItem(result_view, res, delNum, addNum, yn_del) {
	
	if(res.data != 0){
	
		//listItem 만들기 
		var listItem = document.createElement('div')
		listItem.classList.add('listItem')
		if(result_view == cmt_list){
			listItem.setAttribute('id','item_'+res.data.w_no)
		}
		
		//ul 만들기 
		var ulList = document.createElement('ul')
		listItem.append(ulList)
		//li 만들기 
		var liImg = document.createElement('li')
		ulList.append(liImg)
		
		if(result_view == cmt_list){
			var del_div = document.createElement('div')
			del_div.innerHTML='x'
			del_div.setAttribute('onclick',"delWebtoon("+res.data.w_no+")")
			del_div.setAttribute('id',"del_Icon")
			liImg.append(del_div)
		}
		
		//a태그 만들기 
		var aImg = document.createElement('a')
		aImg.href = '/webtoon/detail?w_no='+res.data.w_no
		liImg.append(aImg)
		//이미지 태그 만들기 
		var addImg = document.createElement('img')
		addImg.src = res.data.w_thumbnail
		addImg.title = res.data.w_title
		aImg.append(addImg)
		//타이틀 만들기 
		var liTitle = document.createElement('li')
		liTitle.setAttribute('class','title')
		liTitle.innerText = res.data.w_title
		ulList.append(liTitle)
		
		if(res.data.c_rating != 0.0){
			
			//starIcon 만들기 
			var starIcon = document.createElement('span')
			starIcon.classList.add('material-icons')
			starIcon.innerHTML = 'grade'
			ulList.append(starIcon)
			//평점 li 만들
			var grade = document.createElement('li')
			if(res.data.c_rating.toString().indexOf(".") == -1){
				grade.innerHTML = res.data.c_rating + ".0"
			}else {
				grade.innerHTML = res.data.c_rating
			}
			ulList.append(grade)
		
		}
		//cmtIcon 만들기
		if(res.data.c_com != null){
			var cmtIcon = document.createElement('span')
			cmtIcon.classList.add('material-icons')
			cmtIcon.innerHTML = 'insert_comment'
			ulList.append(cmtIcon)
		}
	
		result_view.insertBefore(listItem,result_view.children[addNum])
	}
	
	if(yn_del != 'y'){
		result_view.children[delNum].remove();
		
	}
}


//평가 삭제

function delWebtoon(w_no,modal){
	
	if(confirm('삭제하시겠습니까?')){
		
		var data = { 
				w_no : w_no,
				idx : (cmtIdx-5 < 0 ? cmtIdx : cmtIdx-5)
		}
		
		axios.post('/myPage', data).then(function(res) {
			console.log(modal)
			if(!modal){
				if(cmtIdx-5 < 0 ){
					makeListItem(cmt_list, res, 2, 7, 'y')
				}else{
					makeListItem(cmt_list, res, 7, 2, 'y')
					cmtIdx--;
				}
				
			}
			
			var div_id = document.getElementById('item_'+w_no)
			div_id.remove()
			
	
				//console.log("length: " + cmt_list.children.length)
				//if(cmt_list.children.length <= 1){
				//	cmt_list.innerText = '평가한 웹툰이 없습니다'
				//}
			
		})
	}
}

</script>
</html>