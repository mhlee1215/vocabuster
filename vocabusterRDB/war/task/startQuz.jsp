<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Buster your vocabulary!</title>
</head>
<%@ include file="/header.jsp" %>
<body class="vb-body">
<div class="ui-widget ui-widget-content vb-page-body-div">
<h1 id="" class="ui-widget-header vb-page-header">Word quiz</h1>
<div id="quizValid">
Validation...<img src="${pageContext.request.contextPath}/images/vb-word-loader.gif" />
</div>
<div id="startWordQuizPanel" style="display:none">
퀴즈에 필요한 정보를 입력 해주세요! 귀찮으면 바로 스타트! <a href="#" onclick="quizMainStart();"><span style="font-size:20px;">시작하기</span></a><br></br>

  카테고리: 
   <select id="wordCategory" name="wordCategory">
   		<option value="">전체</option>
   		<c:forEach items="${categories }" var="category">
   			<option value="${category.id }">${category.name }</option>
   		</c:forEach>
   		
   </select>
   <br></br>
스테이지( 시작할 스테이지 / 최대 스테이지, ex) 1/10 : 전체를 10개로 나눈것중 첫번째 스테이지를 진행)<br>
<span style="font-size:11px; color:#ff0000">생략하면 전체</span><br>
	<input id="stageIndex" type="text" style="width:40px;"/> /	<input id="stageMaxIndex" type="text" style="width:40px;"/>
	<br></br>
보기 수: 
<select id="selectionCount">
	<option value="2">2개</option>
	<option value="3">3개</option>
	<option selected="selected" value="4">4개</option>
	<option value="5">5개</option>
	<option value="6">6개</option>
	<option value="7">7개</option>
	<option value="8">8개</option>
</select>
<br></br>
<a href="#" onclick="quizMainStart();"><span style="font-size:20px;">시작하기</span></a>
</div>
<div id="introWordQuizPanel" style="display:none; text-align: center">
준비하세열... ㄷㄷㄷ 갑니다
<h1 id="introCount"></h1>
</div>
<div id="leftWordQuizPanel" style="display:none">
</div>
<div id="answerWordQuizPanel" style="display:none">
<table>
	<tr>
		<td>
			<div id="isCorrectMark" style="width:50px"></div>
		</td>
		<td>
			<div id="answerDetail" style="">123</div>
		</td>
	</tr>
</table>
<h1 id="answerCount"></h1>
<a href="#" onclick="nextQuiz();">next</a>
<a href="#" onclick="showAnswer();">answer</a>
</div>
<div id="rightWordQuizPanel" style="display:none">
</div>
</div>
<script type="text/javascript">
$(function(){
	//$.get("${pageContext.request.contextPath}/getWordQuestion.do", '', valid);
	valid();
});
function valid(){
	$('#quizValid').hide('slow');
	$('#startWordQuizPanel').show('slow');
}
</script>
<script type="text/javascript" >
var selectionCount = 4;
var currentQuiz = -1;

function quizMainStart(){
	showIntro();
}

/*
function startQuiz(){
	selectionCount = $('#selectionCount').val();
	$('#startWordQuizPanel').hide('slow');
	getNextQuiz(nextQuiz);
	//delay
	//nextQuiz();
}
*/
//다음 퀴즈..
function getQuiz(div, selectionCount, questionCount, callback){
	var data = { 
		selectionCount: selectionCount,
		questionCount: questionCount,
		stageIndex: $("#stageIndex").val(),
		stageMaxIndex: $("#stageMaxIndex").val(),
		questionCategory: $("#wordCategory").val()
	};
	$('#'+div).load('${pageContext.request.contextPath}/getWordQuestion.do', data, callback);	
}

function getNextQuiz(callback){
	currentQuiz++;
	if(currentQuiz < 0 || currentQuiz%2 == 0){
		//alert('getleft! '+currentQuiz);
		removeLeft();
		getQuiz('leftWordQuizPanel', $('#selectionCount').val(), currentQuiz, callback);
	}
	else{
		removeRight(); 
		//alert('getright!'+currentQuiz);
		getQuiz('rightWordQuizPanel', $('#selectionCount').val(), currentQuiz, callback);
	}
}

function nextQuiz(){ 
	//getQuiz('leftWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz, null);
	//getQuiz('rightWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz++, null);
	
	if(currentQuiz%2 == 0){
		
		//getNextQuiz();
		getNextQuiz();		
		$('#leftWordQuizPanel').show('slow', quizProgressStart0);
		$('#answerWordQuizPanel').hide('slow');
		$('#rightWordQuizPanel').hide('slow');
		getAnswer();
	}else{
		//removeLeft();
		getNextQuiz();
		$('#leftWordQuizPanel').hide('slow');
		$('#answerWordQuizPanel').hide('slow');
		$('#rightWordQuizPanel').show('slow', quizProgressStart1);
		getAnswer();
	}
}

function removeLeft(){
	$('#leftWordQuizPanel').empty();
}
function removeRight(){
	$('#rightWordQuizPanel').empty();
}


function getAnswer(){
	
}

function showAnswer(wordName, isAnswer){
	if(isAnswer){
		nextQuiz();
	}else{
		answerCount = answerCountInit;
		answerTimeoutID = setInterval(progressAnswer, eval(1000));	
		if(isAnswer){
			$('#isCorrectMark').html('<h1 style="font-color:green;font-size:40;">O</h1>');
		}
		else{
			$('#isCorrectMark').html('<h1 style="font-size:40;">X</h1>');
		}
		$('#answerDetail').html(wordName);
		$('#leftWordQuizPanel').hide('slow');
		$('#answerWordQuizPanel').show('slow');
		$('#rightWordQuizPanel').hide('slow');
	}
} 

var answerCountInit = 3;
var answerCount = answerCountInit;
var answerTimeoutID;
function progressAnswer(){
	if(answerCount == 0){
		//alert('showAnserTimeup');
		clearInterval(answerTimeoutID);
		nextQuiz();
	}else{
		answerCount--;
		$('#answerCount').html(answerCount);
	}
}

//문제푸는 시간 종료
function answerTimeUp(wordName, isAnswer){
	showAnswer(wordName, isAnswer);
	//alert('timeup!');
}

var introCount = 3;
var introTimeoutID;
function showIntro(){
	$('#startWordQuizPanel').hide('slow');
	$('#introWordQuizPanel').show('slow');
	$('#introCount').html(introCount);
	//인트로가 나오는 동안 첫번째 문제를 가져온다.
	getNextQuiz();
	introTimeoutID = setInterval(progressIntro, eval(1000));	
}

function progressIntro(){
	if(introCount == 0){
		//타임아웃이 되면, 문제를 보여준다.
		clearInterval(introTimeoutID);
		$('#introWordQuizPanel').hide('slow', nextQuiz);
	}else{
		introCount--;
		$('#introCount').html(introCount);
	}
}

</script>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>