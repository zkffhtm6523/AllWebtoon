<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
<title>모두의 웹툰(검색 결과)</title>
<style type="text/css">
    section{
	width:100%;
	background-color: #F8F8F8;
	margin: 0 auto;
	background-color: #F8F8F8;
	border-top: 1px solid #EAEAEA;
}
    section .content{width: 70%; margin: 30px auto;}
    section img{width: 180px; border-radius: 5%;}
    section .content hr{width: 100%;}
    section .aboveContainer h2{margin-top: 0px; width: 100%; margin-left: 20px;}
    section .webtoonContainer {margin: 50px auto; padding: 5px;}
    section ul {list-style-type: none; 
    clear: both; margin: 0 auto;padding: 0;}
    section ul li:nth-child(1) {float: left; padding: 30px; padding-top: 0px;}
    section ul li img{ 
    	border-radius: 10%;
	    transform: scale(1.1);
	    -webkit-transform: scale(1.1);
	    -moz-transform: scale(1.1);
	    -ms-transform: scale(1.1);
	    -o-transform: scale(1.1);
	    transition: all 0.2s ease-in-out;
	    margin-right: 20px;
	    margin-top: 5px;	
	}
    section ul li img:hover {cursor: pointer;
	    transform: scale(1.2);
	    -webkit-transform: scale(1.2);
	    -moz-transform: scale(1.2);
	    -ms-transform: scale(1.2);
	    -o-transform: scale(1.2);}
	section ul li a{color: #0c65c6; text-decoration: none;}
    section ul li:nth-child(2) {font-weight: bold; font-size: 1.2em; padding-top: 1px;}
    section ul li:nth-child(3) {line-height: 25px; }
    section ul li:not(:first-child){margin-top: 15px;}
    section ul li .list{color: gray; font-weight: gray; font-weight: bold; margin-right:10px;}
    section ul .thumbnail{width: 180px; height: 160px; margin-top: 10px;}
    section ul #writer {
    width:500px;
   	text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    }
</style>
</head>
<body>
<div id="container">
	<jsp:include page="../template/header.jsp"/>
	<section>
		<div class="content" id="webtoon_content">
			<div class="aboveContainer">
				<h2>상위 검색 결과</h2>
			</div>
			<hr>
			<c:forEach items="${result}" var="item">
			<div class="webtoonContainer">
				<ul class="itemRow">
		           <li><img class="thumbnail" src=" ${item.w_thumbnail}" onclick="moveToDetail(${item.w_no	})"></li>
		           <li><a href="/webtoon/detail?w_no=${item.w_no}">${item.w_title }</a></li>
		           <li><span class="list" id="ctnt">내용</span>${item.w_story }</li>
		           <li id="writer"><span class="list">작가</span>${item.w_writer}</li>
		           <li><span class="list">장르</span>${item.genre_name}</li>
				</ul>
			</div>
			</c:forEach>
		</div>
	</section>
	<jsp:include page="../template/footer.jsp"/>
</div>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">

	var data = []
		
	var idx = ${count}+1
	
	console.log(idx)
	//최상단 가기 버튼
	makeArrowUpward()
	//최하단 가기 버튼
	makeArrowDownward()
	//스크롤 바닥 감지
	window.onscroll = function() {
	    //window height + window scrollY 값이 document height보다 클 경우,
	    if((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
	    	//실행할 로직 (콘텐츠 추가)
	       	
	    	console.log('index: '+ idx)
	    	
	    	axios.get('/searchResult?result=${keyword}&writer=${param.writer}&genre=${param.genre}&page=' +idx
	    			/*{
	    		params : {
	    			page : idx
	    		}		
	    	}*/).then(function(res) {
	    		console.log(res.data)
	    
	    	res.data.forEach(function (item){
	    		
	    		var div = document.createElement('div')
	    		div.setAttribute('class','webtoonContainer')
	    		var ul = document.createElement('ul')
	    		ul.setAttribute('class','itemRow')
	    		var img_li = document.createElement('li')
	    		var img = document.createElement('img')
	    		img.setAttribute('class','thumbnail')
	    		img.setAttribute('src',item.w_thumbnail)
	    		img.setAttribute('onclick','moveToDetail('+item.w_no+')')
	    		img_li.append(img)
	    		var a_li = document.createElement('li')
	    		var a = document.createElement('a')
	    		a.setAttribute('href','/webtoon/detail?w_no='+item.w_no)
	    		a_li.append(a)
	    		a.append(item.w_title)
	    		var story_li = document.createElement('li')
	    		var story_span = document.createElement('span')
	    		story_span.setAttribute('class','list')
	    		story_span.setAttribute('id','ctnt')
	    		story_span.append('내용')
	    		story_li.append(story_span)
	    		story_li.append(item.w_story)
	    		var writer_li = document.createElement('li')
	    		var writer_span = document.createElement('span')
	    		writer_span.setAttribute('class','list')
	    		writer_span.append('작가')
	    		writer_li.append(writer_span)
	    		writer_li.append(item.w_writer)
	    		
	    		var	genre_li = document.createElement('li')
	    		var genre_span = document.createElement('span')
	    		genre_span.setAttribute('class','list')
	    		genre_span.append('장르')
	    		genre_li.append(genre_span)
	    		
	    		genre_li.append(item.genre_name)
	    		
	    		ul.append(img_li)
	    		ul.append(a_li)
	    		ul.append(story_li)
	    		ul.append(writer_li)
	    		ul.append(genre_li)
	    		
	    		div.append(ul)
	    		
	    		webtoon_content.append(div)
	    		
	    		idx++
	    		
	    	})
		        
	    	})
	    }
	};
	function makeArrowUpward() {
		var arrowUpward = document.createElement('span')
		arrowUpward.classList.add('material-icons')
		arrowUpward.innerText = 'arrow_upward'
		arrowUpward.id = 'arrow_upward'
		arrowUpward.title = '상단으로 가기'
		
		var a = document.createElement('a')
		a.href = 'javascript:window.scrollTo(0,0);'
		a.append(arrowUpward)
		
		let section = document.querySelector('section')
		section.append(a)
	}
	function makeArrowDownward() {
		var arrowDownward = document.createElement('span')
		arrowDownward.classList.add('material-icons')
		arrowDownward.innerText = 'arrow_downward'
		arrowDownward.id = 'arrow_downward'
		arrowDownward.title = '하단으로 가기'
		
		var a = document.createElement('a')
		a.href = 'javascript:window.scrollTo(0,document.body.scrollHeight);'
		a.append(arrowDownward)
		
		let section = document.querySelector('section')
		section.append(a)
	}
   function moveToDetail(w_no) {
   	location.href = '/webtoon/detail?w_no='+w_no
   }
</script>
</body>
</html>