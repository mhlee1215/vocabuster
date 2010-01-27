<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="startWordQuizPanel">
시작해볼까? ㄷㄷㄷ<br></br>
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
<a href="#" onclick="quizMainStart();">start</a>
</div>
<div id="introWordQuizPanel" style="display:none">
<h1 id="introCount"></h1>
</div>
<div id="leftWordQuizPanel">
</div>
<div id="answerWordQuizPanel" style="display:block">
answer...
<h1 id="answerCount"></h1>
<a href="#" onclick="nextQuiz();">next</a>
<a href="#" onclick="showAnswer();">answer</a>
</div>
<div id="rightWordQuizPanel">
</div>
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
		questionCount: questionCount
	};
	$('#'+div).load('/getWordQuestion.do', data, callback);	
}

function getNextQuiz(callback){
	if(currentQuiz < 0 || currentQuiz%2 == 0){
		getQuiz('leftWordQuizPanel', $('#selectionCount').val(), ++currentQuiz, callback);
	}
	else{ 
		getQuiz('rightWordQuizPanel', $('#selectionCount').val(), ++currentQuiz, callback);
	}
}

function nextQuiz(){ 
	//getQuiz('leftWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz, null);
	//getQuiz('rightWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz++, null);
	if(currentQuiz%2 == 0){
		removeRight();
		$('#leftWordQuizPanel').show('slow', quizProgressStart);
		//$('#answerWordQuizPanel').hide('slow');
		$('#rightWordQuizPanel').hide('slow');
		getAnswer();
	}else{
		removeLeft();
		$('#leftWordQuizPanel').hide('slow');
		//$('#answerWordQuizPanel').hide('slow');
		$('#rightWordQuizPanel').show('slow', quizProgressStart);
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

function showAnswer(){
	answerCount = answerCountInit;
	answerTimeoutID = setInterval(progressAnswer, eval(1000));	
	$('#leftWordQuizPanel').hide('slow');
	//$('#answerWordQuizPanel').show('slow');
	$('#rightWordQuizPanel').hide('slow');
	getNextQuiz();
} 

var answerCountInit = 3;
var answerCount = answerCountInit;
var answerTimeoutID;
function progressAnswer(){
	if(answerCount == 0){
		alert('showAnserTimeup');
		clearInterval(answerTimeoutID);
		nextQuiz();
	}else{
		answerCount--;
		$('#answerCount').html(answerCount);
	}
}

//문제푸는 시간 종료
function answerTimeUp(){
	showAnswer();
	alert('timeup!');
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





