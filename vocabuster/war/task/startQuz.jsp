<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="leftWordQuizPanel">
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
<div id="wordQuizAnswerPanel">
</div>
<div id="rightWordQuizPanel">
</div>
<script language="text/javascript" >
//다음 퀴즈..
var currentQuiz = 0;
function getQuiz(div, selectionCount, questionCount, callback){
	var data = { 
		selectionCount: selectionCount,
		questionCount: questionCount
	};
	$('#'+div).load('/getWordQuestion.do', data, callback);	
}

function startQuiz(){
	getQuiz('leftWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz++, null);
	getQuiz('rightWordQuizPanel', ${vBWordQuizVO.selectionCount}, currentQuiz++, null);
	$('#leftWordQuizPanel').show('slow');
	$('#wordQuizAnswerPanel').hide('slow');
	$('#rightWordQuizPanel').show('slow');
} 

function showAnswer(){
	$('#wordQuizPanel').hide('slow');
	$('#wordQuizAnswerPanel').html('');
} 

function answerTimeUp(){
	
}
</script>




