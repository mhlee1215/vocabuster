<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
	<a href="javascript:fncBlank();" id="${vBWordQuizVO.questionCount}_${selection}_btn" class="ui-state-default ui-corner-all quiz_button">
		<span class="ui-icon ui-icon-circle-triangle-e"></span>${selection}
	</a>
	</td>
<c:if test="${status.count%maxWidth == 0 || maxWidth == 1}">
</tr>
</c:if>
</c:forEach>
</table>
<hr></hr>
<a href="#" onclick="startQuiz();">next</a>
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
	$('#quizProgressbar').progressbar({
		value: 0 
	});
});
</script>
<!-- 프로그래스 바 태그 -->
<h2 class="demoHeaders">Progressbar</h2>	
<div id="quizProgressbar" style="height:5px;"></div>
<script type="text/javascript">
//프로그래스바 증가..
var maxTic = 10;
var curTic = 0;
function progress(){
	curTic++;
	$('#quizProgressbar').progressbar('option', 'value', curTic*100/maxTic);
	if(curTic == maxTic){
		clearInterval(timeoutID);
		//answer....
		answerTimeUp();
	}
		
}
var timeoutID = setInterval(progress, eval(50));
</script>
</html>