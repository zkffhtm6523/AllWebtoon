selCntRating()

function selCntRating(){
	axios.get('/template').then(function(res) {
		makeCntRating(res.data)
	})		
}
function makeCntRating(cnt) {
	//cnt를 감싸줄 전체 div 만들기
	var div_cnt_rating = document.createElement('div')
	div_cnt_rating.id = 'footer_cnt_rating'
	div_cnt_rating.innerText = '현재까지'
	
	//별표 감싸줄 span 만들기
	var span_star = document.createElement('span')
	span_star.id = 'footer_star'
	span_star.innerText = '★'
	div_cnt_rating.append(span_star)
	
	//평점 감싸줄 span 만들기
	var span_cmt = document.createElement('span')
	span_cmt.id = 'footer_cnt_rating_span'
	var trans_ctn = numberWithCommas(cnt)
	span_cmt.innerText = trans_ctn + '개의 평가'
	div_cnt_rating.append(span_cmt)
	
	//마지막 텍스트 넣어주기
	div_cnt_rating.append('가 쌓였어요.')
	
	footer_container.prepend(div_cnt_rating)
}
//숫자 콤마 찍어주기
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}