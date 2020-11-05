<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
	<link href="/css/view/starRating.css" rel="stylesheet" type="text/css" media="all" />
	<title>평가하기</title>
</head>
<body>
<div id="container">
<jsp:include page="../template/header.jsp"/>
<section>
	<div id="listBlock">
	<c:forEach items="${list}" var="item" begin="0" end="${list.size() -1 }" varStatus="status">
		<div class="itembox" id="itembox_${status.index }">
			<dl>
				<dt title="${list[status.index].w_title }">${list[status.index].w_title }</dt>
				<dd class="img"><img src="${list[status.index].w_thumbnail }" title="${list[status.index].w_title}"></dd>
				<dd class="writer" title="${list[status.index].w_writer}">${list[status.index].w_writer}</dd>
				<dd>
				   <div class="startRadio">
		               <c:forEach begin="1" end="10" step="1" var="rating_item">
	                   <label class="startRadio__box">
	                    	<input type="radio" name="star_${status.index}" id="star_${status.index}_${rating_item}" onclick="score(${rating_item},${status.index },${list[status.index].w_no })" 
	                    	
	                    	<c:forEach items="${cmt_list }" var="cmtlist">
		                    	<c:if test="${cmtlist.w_no == item.w_no}">
			                    	${(cmtlist.c_rating*2 == rating_item) ? 'checked' : ''}
		                    	</c:if>
		                    </c:forEach>
	                    		> 
	                    	<span class="startRadio__img"><span class="blind"></span></span>
	                    </label>
		                </c:forEach>
		                
		             	<input type="hidden" name="c_rating" id="c_rating_${status.index}" class="point" 
		             	<c:forEach items="${cmt_list }" var="cmtlist">
			             	<c:if test="${cmtlist.w_no == item.w_no}">
			             		value="${cmtlist.c_rating}"
			             	</c:if>
		             	</c:forEach>required>
		         	</div>
		      	</dd>
			</dl>
		</div>
	</c:forEach>
	</div>
</section>
<jsp:include page="../template/footer.jsp"/>
</div>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
	//최상단 가기 버튼
	makeArrowUpward()
	//최하단 가기 버튼
	makeArrowDownward()
	
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
	function score(star,index,w_no) { // 별점주기
		
		console.log('star: '+star)
		console.log('index: '+index)
		console.log('w_no: '+w_no)
		console.log('c_rating: ' + document.getElementById("c_rating_"+index).value)
		
		var cmtChk = 0;
		var c_rating =0;
	   	if(document.getElementById("c_rating_"+index).value != ''){
	   		cmtChk=2;
	   		document.getElementById("c_rating_"+index).value = ''
	   		document.getElementById('star_' + index + '_' + star).checked = false;
		}else {
		   	c_rating = parseFloat(star)/2
		   	document.getElementById("c_rating_"+index).setAttribute('value',c_rating)
		}
	   	

		console.log('star2: '+star)
		console.log('index2: '+index)
		console.log('w_no2: '+w_no)
		console.log('c_rating2: ' + document.getElementById("c_rating_"+index).value)
	   	console.log('cmtChk2: ' + cmtChk)
		
	   	var data = { 
			w_no : w_no,
			c_rating : c_rating,
			ratingPage : '1',
			cmtChk : cmtChk
		}
		
		axios.post('/webtoon/cmt', data).then(function(res) {
			console.log(res)
		})
	}

	var cmtlist = []

	<c:forEach items="${cmt_list}" var="item">
		var obj = {
				w_no: '${item.w_no}',
				u_no: '${item.u_no}',
				c_rating: '${item.c_rating}'
		}
		
		cmtlist.push(obj)
	</c:forEach>

	var idx = ${list.size()}
	
	//스크롤 바닥 감지
	window.onscroll = function() {
	    //window height + window scrollY 값이 document height보다 클 경우,
	    if((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
	       	
	    	axios.get('/webtoon/cmt', {
	    		params : {
	    			page : idx
	    		}
	    	}).then(function(res) {
		    	res.data.forEach(function (item){
	    			makecontent(item)
			        idx++
	    		})
	    	})
	    }
	}
	
	function makecontent(item){
		var itembox = document.createElement('div')
        itembox.setAttribute('class','itembox')
        itembox.setAttribute('id','itembox_'+idx)
        var dl = document.createElement('dl')
        var title = document.createElement('dt')
        title.setAttribute('title',item.w_title)
        var imgbox = document.createElement('dd')
        var img = document.createElement('img')
        img.title = item.w_title
        img.setAttribute('src',item.w_thumbnail)
        imgbox.append(img)
        var writer = document.createElement('dd')
        writer.setAttribute('class','writer')
        writer.setAttribute('title',item.w_writer)
        title.append(item.w_title)
        writer.append(item.w_writer)
        
        var star = document.createElement('dd')
        var startRadio = document.createElement('startRadio')
        startRadio.setAttribute('class','startRadio')
        
       for(var rating_item=1; rating_item<=10; rating_item++){
			var label = document.createElement('label')
        	label.setAttribute('class','startRadio__box')
        	var input = document.createElement('input')
        	input.setAttribute('type','radio')
        	input.setAttribute('name','start'+idx)
        	input.setAttribute('onclick','score('+rating_item+','+idx+','+ item.w_no + ')')
        	input.setAttribute('id','star_'+idx+'_'+rating_item)
        	
        	
       	for(var i=0; i<cmtlist.length; i++){
       		//t_comment에 저장된 내 평점이 있고 라디오버튼 위치가 점수와 같으면  
	         	 if(cmtlist[i].w_no == item.w_no && cmtlist[i].c_rating * 2 == rating_item ){		
	         		//해당 라디오버튼 체크  
	         		input.checked = true;
	         	}
        	}
        	
        	var startRadio__img = document.createElement('span')
        	startRadio__img.setAttribute('class','startRadio__img')
        	var blind = document.createElement('span')
        	blind.setAttribute('class','blind')
        	startRadio__img.append(blind)
        	label.append(input)
        	label.append(startRadio__img)
        	startRadio.append(label)
        
        }
       
        var c_rating = document.createElement('input')
        c_rating.setAttribute('type','hidden')
        c_rating.setAttribute('name','c_rating')
        c_rating.setAttribute('class','point')
        c_rating.setAttribute('id','c_rating_'+idx)
        
        for(var i=0; i<cmtlist.length; i++){
       	 //평점테이블에 기록이 있다면  
        	 if(cmtlist[i].w_no == item.w_no){
        		c_rating.setAttribute('value',cmtlist[i].c_rating)
        	}
    	}
        
        startRadio.append(c_rating)
        
        star.append(startRadio)
        
        dl.append(title)
        dl.append(imgbox)
        dl.append(writer)
        dl.append(star)
        
        itembox.append(dl)
      
       listBlock.append(itembox)
       
	}
</script>
</body>
</html>