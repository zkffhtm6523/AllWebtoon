<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
	<link href="/css/view/searchResult.css" rel="stylesheet" type="text/css" media="all" />
	<title>All 웹툰(검색 결과)</title>
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

	
	
	//최상단 가기 버튼
	makeArrowUpward()
	//최하단 가기 버튼
	makeArrowDownward()
	
	//초기 list.size()를 가져올 첫 index로 설정
	var idx = ${result.size()}
	
	//스크롤 바닥 감지
	window.onscroll = function() {
	    //window height + window scrollY 값이 document height보다 클 경우,
	    if((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
	    	axios.get('/searchResult?result=${keyword}&writer='
	    			+'${param.writer}&genre=${param.genre}&page=' +idx
	    		).then(function(res) {
	    
		    		res.data.forEach(function (item){
		    			makecontent(item)
	    				//idx++
	    		})    
	    	})
	    	
	    	idx += ${count}
	    }
	};
	
	function makecontent(item){
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
	}
	
	
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