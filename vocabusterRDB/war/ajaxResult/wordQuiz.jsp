<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="maxQuizPanel" value="2" />
<title>Insert title here</title>
</head>
<body>
${vBWordQuizVO.questionCount} 번째 문제
<h1 style="font-size:40;">${targetWord.wordName}</h1>
answer : ${answerNumber }

<br></br>

<c:set var="maxWidth" value="2" />
<hr></hr>
<table>
<c:forEach items="${selectionList}" var="selection" varStatus="status">
<c:if test="${status.count%maxWidth == 1 || maxWidth == 1}">
<tr height="40">	
</c:if>
	<td>
	<a href="javascript:fncBlank();" onclick="submitAnswer${vBWordQuizVO.questionCount%maxQuizPanel}(${status.count-1 == answerNumber ? 'true' : 'false'})" id="${vBWordQuizVO.questionCount}_${selection}_btn" class="ui-state-default ui-corner-all quiz_button">
		<span class="ui-icon ui-icon-circle-triangle-e"></span>${selection}
	</a>
	</td>
<c:if test="${status.count%maxWidth == 0 || maxWidth == 1}">
</tr>
</c:if>
</c:forEach>
</table>
<hr></hr>
<a href="#" onclick="nextQuiz();">next</a>
<a href="#" onclick="showAnswer();">answer</a>
</body>

<script type="text/javascript"> 
$(function(){
	$('.quiz_button')
	.hover(	
		function(){ $(this).addClass("ui-state-hover");},
		function(){ $(this).removeClass("ui-state-hover");},
		function(){ $(this).css("cursor", "hand");}
	)
	.mousedown(function(){ $(this).addClass("ui-state-active");})
	.mouseup(function(){ $(this).removeClass("ui-state-active");})
	.mouseout(function(){$(this).removeClass("ui-state-active");})
	.focus(function(){$(this).blur();});
	
	//프로그래스 바 생성
	$('#quizProgressbar${vBWordQuizVO.questionCount%maxQuizPanel}').progressbar({
		value: 0 
	});
});
</script>
<!-- 프로그래스 바 태그 -->
<div id="quizProgressbar${vBWordQuizVO.questionCount%maxQuizPanel}" style="height:15px;"></div>
<div id="quizWordAnswer${vBWordQuizVO.questionCount%maxQuizPanel}" style="display:none">
<table>
<tr>
	<td align="left">
		<h1 style="color:#2F3442;font-size:50; margin-bottom:-15px;">${targetWord.wordName}</h1>
	</td>
	<td align="left">
		<h2 style="color:#6F799B;margin-left:15px; margin-bottom:-3px;">${targetWord.soundSymbol}</h2>
	</td>
</tr>
<tr>
	<td colspan="2">
		<ul>
		<c:forEach items="${targetWord.wordInfoList}" var="meaning" varStatus="status">
			<li>${meaning.shortMeaning}</li>
		</c:forEach>
		</ul>
	</td>
</tr>
</table>

</div>
<script type="text/javascript">
//프로그래스바 증가..
var maxTic${vBWordQuizVO.questionCount%maxQuizPanel} = 50*2;
var curTic${vBWordQuizVO.questionCount%maxQuizPanel} = 0;
function progress${vBWordQuizVO.questionCount%maxQuizPanel}(){
	curTic${vBWordQuizVO.questionCount%maxQuizPanel}++;
	$('#quizProgressbar${vBWordQuizVO.questionCount%maxQuizPanel}').progressbar('option', 'value', curTic${vBWordQuizVO.questionCount%maxQuizPanel}*100/maxTic${vBWordQuizVO.questionCount%maxQuizPanel});
	if(curTic${vBWordQuizVO.questionCount%maxQuizPanel} == maxTic${vBWordQuizVO.questionCount%maxQuizPanel}){
		clearInterval(timeoutID${vBWordQuizVO.questionCount%maxQuizPanel});
		//answer....
		
		//answerTimeUp();
		//시간다되면 틀린거임
		submitAnswer${vBWordQuizVO.questionCount%maxQuizPanel}(false);
	}
}
var timeoutID${vBWordQuizVO.questionCount%maxQuizPanel};// = setInterval(progress, eval(50));
function quizProgressStart${vBWordQuizVO.questionCount%maxQuizPanel}(){
	timeoutID${vBWordQuizVO.questionCount%maxQuizPanel} = setInterval(progress${vBWordQuizVO.questionCount%maxQuizPanel}, eval(50));
}

function submitAnswer${vBWordQuizVO.questionCount%maxQuizPanel}(isCorrect){
	clearInterval(timeoutID${vBWordQuizVO.questionCount%maxQuizPanel});
	var data;
	var isAnswer = false;
	if(isCorrect){
		data = {
				quizWordName:'${targetWord.wordName}',
				isCorrect:'Y'
		};
		isAnswer = true;
	}
	else{
		data = {
				quizWordName:'${targetWord.wordName}',
				isCorrect:'N'
		};
		isAnswer = false;
	}

	$.post("/submitAnswer.do", data);
	answerTimeUp($('#quizWordAnswer${vBWordQuizVO.questionCount%maxQuizPanel}').html(), isAnswer);
}

</script>

</html>