<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
	/*section 시작*/
section{
	background-color: #F8F8F8;
	border-top: 1px solid #EAEAEA;
	text-align: center;
	width:100%;
}
section #sec_container{
	width: 100%;
	margin: 0 auto;
}
section .indexBlock {
	position: relative;
	width:85%;
	margin:20px auto;
}
section .indexBlock h1{
	position: relative;
	top:10%;
	left: -35%;
	display: inline-block;
}
section .indexBlock hr{
 	width: 85%;
 	margin-right: 8%;
}
section .indexBlock #sel_gerne{
	position: absolute;
	width: 120px;
	padding: 5px;
	border-radius: 5px;
	border:none;
	right:10%;
	background-color: #4FA2C7;
	color: white;
	margin: 25px auto;
}
section img{width: 100%; height:80%; border-radius: 5%;}
section .imgBlock{display: inline-block; width: 15%; text-align: center;
	    height: 180px;
	    vertical-align: top;
	    margin: 10px auto;
	    margin-left: 1%;
	    margin-right: 1%;}
section .imgBlock:hover{cursor: pointer;}
section .listBlock{vertical-align: top;}
section .indexBlock .material-icons{width: 50px; height: 30px; position: absolute; top: 58%;}
section  #prevArrIcon{left: 2%;}
section  #nextArrIcon{right: 2%;}
section .material-icons:hover{cursor: pointer;}

</style>
<title>All 웹툰</title>
</head>
<body>
<div id="container">
		<jsp:include page="../template/header.jsp"/>
		<section>
			<div id="sec_container"></div>
		</section>
		<jsp:include page="../template/footer.jsp"/>
</div>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script>
		var genrelist = []
		
		<c:forEach items="${genreList}" var="item">
			var obj = '${item}'
		
			genrelist.push(obj)
		</c:forEach>
	
		function ToonVO(w_no, w_title, w_thumbnail, w_plat_no){
			this.w_no = w_no;
			this.w_title = w_title;
			this.w_thumbnail = w_thumbnail;
			this.w_plat_no = w_plat_no;
		}
		//HomeSer에서 EL식으로 값을 받아와 자바스크립트 각 배열에 넣어주기
		var genreList = new Array();
    	var naverList = new Array();
    	var kakaoList = new Array();
    	var lezhinList = new Array();
    	var daumList = new Array();
    	var toptoonList = new Array();
    	
    	<c:forEach items="${gList}" var="item">
    		var toonVO = new ToonVO("${item.w_no}","${item.w_title}","${item.w_thumbnail}","${item.w_plat_no}")
    		genreList.push(toonVO)
    	</c:forEach>

		//배열에 넣어주기 위한 JSTL For-Each문  
		<c:forEach items="${list}" var="item">
 			var toonVO = new ToonVO("${item.w_no}","${item.w_title}","${item.w_thumbnail}","${item.w_plat_no}")
			<c:choose>
				<c:when test="${item.w_plat_no == 1}">
    				naverList.push(toonVO)
    			</c:when>
				<c:when test="${item.w_plat_no == 2}">
    				daumList.push(toonVO)
    			</c:when>
				<c:when test="${item.w_plat_no == 3}">
    				kakaoList.push(toonVO)
    			</c:when>
				<c:when test="${item.w_plat_no == 4}">
    				lezhinList.push(toonVO)
    			</c:when>
				<c:when test="${item.w_plat_no == 5}">
    				toptoonList.push(toonVO)
    			</c:when>
 			</c:choose>
    	</c:forEach>
    	
    	//함수 사용으로 인한 간단한 호출...이거만 있으면 됨!!!
    	makeImage(genreList, "장르별 웹툰 보기",'','genre')
	  	makeImage(naverList, "네이버 웹툰 추천",'네이버')
	  	makeImage(daumList, "다음 웹툰 추천",'다음')
	  	makeImage(kakaoList, "카카오페이지 추천",'카카오')
	  	makeImage(lezhinList, "레진코믹스 추천",'레진')
	  	makeImage(toptoonList, "탑툰 추천",'탑툰')
	  	makeArrowUpward()
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
	  	
	  	
	  	function selectGenre(){
    		var selectText = sel_genre.options[sel_genre.selectedIndex].text;
    	}
	    
	  	function makeImage(list, title, result, genre){
		    //추천별 블록 만들기
		    let indexBlock = document.createElement('div')
		    indexBlock.classList.add('indexBlock')
		    sec_container.append(indexBlock)
	    	//추천별 블록->타이틀+구분선
		    let secTitle = document.createElement('h1')
		    secTitle.append(title)
		    indexBlock.append(secTitle) // 타이틀 이름 넣기  
		    if(genre != null){
		    	let select = document.createElement('select')
		    	select.setAttribute('id','sel_gerne')
				select.addEventListener('change',function(){

					var data = { 
						genre : select.options[select.selectedIndex].text
					}
						
					console.log("장르: " + data.genre)
					axios.post('/home', data).then(function(res) {
						console.log(res)
						result = data.genre
						list = []
						
						listBlock.innerHTML = ""
						
						res.data.forEach(function (item){
							console.log(item.w_title)
							
							list.push(item)
						})
						
	    				makeItem(listBlock, list, result,'y')
					})
					
				})
		    	let option = document.createElement('option')
		    	option.setAttribute('value','all')
		    	option.append('전체')
		    	select.append(option)
		    	for(var i=0; i<genrelist.length; i++){
		    		let option = document.createElement('option')
		    		option.setAttribute('value',genrelist[i])
		    		option.append(genrelist[i])
		    		select.append(option)
		    	}
		    	
		    	indexBlock.append(select)
		    }
		    indexBlock.append(document.createElement('hr'))
		
		    //배열이 담길 전체 박스
		    let listBlock = document.createElement('div')
		    
		    indexBlock.append(listBlock)
		    listBlock.classList.add('listBlock')
		    
	    	makeItem(listBlock, list, result, 'n')
	    	
	  	}
		    
		    //좌측 화살표 아이콘 집어넣기
		    
			function makeItem(listBlock, list, result, yn_genre){
		    	
			//배열 인덱스 및 반복문 체크용
		    let index = 0;
	    	let chk = 0;
	    	
		    var icons = document.createElement('span')
		    icons.classList.add('material-icons')
		    icons.innerHTML = 'keyboard_arrow_left'
		    icons.title = '이전 목록'
		    icons.id = 'prevArrIcon'
	    	icons.addEventListener('click',function(){
	    		if(index - 5 > 0){
	    		var imgBlock = document.createElement('div')
		         imgBlock.classList.add('imgBlock')
		         imgBlock.setAttribute('onclick','moveToDetail('+list[index-6].w_no+')')
		         var img = document.createElement('img')
		         img.src = `\${list[index-6].w_thumbnail}`
		         imgBlock.append(img)
		         imgBlock.append(document.createElement('br'))
		         imgBlock.append(list[index-6].w_title)
		         icons.after(imgBlock)
		         index--;
		    	 listBlock.removeChild(listBlock.childNodes[6]); 
	    		}else{
	    			alert('처음입니다.')
	    		}
		    })	
		    listBlock.append(icons)
		    
		    //배열 담길 전체 박스에 이미지 박스 추가
	    	var chkChk = true;
		    while(chkChk){
		    	 if(chk >= 4){chkChk = false}
		         var imgBlock = document.createElement('div')
		         imgBlock.classList.add('imgBlock')
		         imgBlock.setAttribute('onclick','moveToDetail('+list[index].w_no+')')
		         var img = document.createElement('img')
		         img.src = `\${list[index].w_thumbnail}`
		         img.title = `\${list[index].w_title}`
		         
		         listBlock.append(imgBlock)
		         
		         imgBlock.append(img)
		         imgBlock.append(document.createElement('br'))
		         imgBlock.append(list[index].w_title)
		         
		         chk++;
		         index++;
		    }
		    console.log('index : '+index)
		    //우측 화살표 아이콘 만들기
		    var icons2 = document.createElement('span')
		    icons2.classList.add('material-icons')
		    icons2.innerHTML = 'keyboard_arrow_right'
		    icons2.title = '다음 목록'
		    icons2.id = 'nextArrIcon'
		    icons2.addEventListener('click',function(){
		    	 if(list.length > index){
			    	 var imgBlock = document.createElement('div')
			         imgBlock.classList.add('imgBlock')
			         imgBlock.setAttribute('onclick','moveToDetail('+list[index].w_no+')')
			         var img = document.createElement('img')
			         img.src = `\${list[index].w_thumbnail}`
			         imgBlock.append(img)
			         imgBlock.append(document.createElement('br'))
			         imgBlock.append(list[index].w_title)
			         icons2.before(imgBlock)
			         index++;
			    	 listBlock.removeChild(listBlock.childNodes[1]); 
		    	 }else{
		    		 if(confirm('추가 목록을 확인하시겠습니까?')){
		    			 if(yn_genre == 'n'){
		    			 	location.href = '/searchResult?result='+result
		    			 }else if(yn_genre == 'y'){
		    			 	location.href = '/searchResult?genre=y&result='+result
		    			 }
		    		 }
		    	 }
		    })		   
		    listBlock.append(icons2)
		    console.log('마지막')
	    }
    	//웹툰 상세페이지 가기
	  	function moveToDetail(w_no) {
	  		location.href = '/webtoon/detail?w_no='+w_no
	  	}
	  	
    </script>
</body>
</html>