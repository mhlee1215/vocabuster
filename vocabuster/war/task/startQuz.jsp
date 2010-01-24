<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="wordQuizPanel">
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
<script language="text/javascript" >
function startQuiz(){
	var data = { 
		selectionCount: $("#selectionCount").val(),
		questionCount: 0
	};
	$('#wordQuizPanel').load('/getWordQuestion.do', data); 
} 
</script>