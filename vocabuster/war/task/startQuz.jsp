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
<a href="#" onclick="startQuiz();">start</a>
</div>
<div id="leftWordQuizPanel" style="display:none">
</div>
<div id="answerWordQuizPanel" style="display:none">
answer...

<a href="#" onclick="nextQuiz();">next</a>
<a href="#" onclick="showAnswer();">answer</a>
</div>
<div id="rightWordQuizPanel" style="display:none">
</div>
<script type="text/javascript" >
var selectionCount = 4;
var currentQuiz = -1;

function startQuiz(){
	selectionCount = $('#selectionCount').val();
	$('#startWordQuizPanel').hide('slow');
	getNextQuiz(nextQuiz);
	//delay
	//nextQuiz();
} 
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
	//alert('hi'); 
	//getQuiz('leftWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz, null);
	//getQuiz('rightWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz++, null);
	if(currentQuiz%2 == 0){
		$('#leftWordQuizPanel').show('slow');
		$('#answerWordQuizPanel').hide('slow');
		$('#rightWordQuizPanel').hide('slow', removeRight);
		getAnswer();
	}else{
		$('#leftWordQuizPanel').hide('slow', removeLeft);
		$('#answerWordQuizPanel').hide('slow');
		$('#rightWordQuizPanel').show('slow');
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
	$('#leftWordQuizPanel').hide('slow');
	$('#answerWordQuizPanel').show('slow');
	$('#rightWordQuizPanel').hide('slow');
	getNextQuiz();
	
} 

function answerTimeUp(){
	showAnswer();
	//alert('timeup!');
}
</script>





