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
<h1 id="" class="ui-widget-header vb-page-header">Add word(s)</h1>
<div id="addWordPanel">

<br>
 <form name="wordForm" action="${pageContext.request.contextPath}/addWords.do" method="post">
   <div>
   카테고리: 
   <select id="wordCategory" name="wordCategory">
   		<option value="">없음</option>
   		<c:forEach items="${categories }" var="category">
   			<option value="${category.id }">${category.name }</option>
   		</c:forEach>
   		
   </select>
   </div>	
   <div><textarea id="wordContent" name="content" rows="3" cols="60"></textarea></div>
   <a href="#" onclick="submitData();">add</a>
 </form>
</div>

<div id="addWordsStatus">
</div>
</div>
<script type="text/javascript" >
var words;
var maxProgress;
var curProgress;
var isProcessing = true;
var theradCount = 5;

var currentAddIndex = 0;
function submitData(){
	if($("#wordContent").val() == ''){
		alert('입력할 단어를 넣고 추가하는것이 좋을 것이야..');
		return;
	}
	var data = { content : $("#wordContent").val() };
	var htmlContent = ''
	words = $("#wordContent").val().split('\n');
	words = truncateArray(words);
	maxProgress = words.length;
	curProgress = 0;

	htmlContent+='전체 입력수.. '+words.length+'개...<a href="#">멈춤</a>&nbsp;<a href="#">계속</a>';
	htmlContent+='<br><br>';
	htmlContent+='<div id="progressbar" style="height:5px;"></div><br>';
	htmlContent+='<div style="height:150px; overflow:auto">';
	htmlContent+='<table class="ui-widget ui-widget-content">';
	htmlContent+='<thead>';
	htmlContent+='<tr class="ui-widget-header ">';
	htmlContent+='<th>단어명</th>';
	htmlContent+='<th>상태</th>';
	htmlContent+='<th>상태명</th>';
	htmlContent+='<th>단어DB</th>';
	htmlContent+='<th>유저DB</th>';
	htmlContent+='<th>의미(1개만)</th>';
	htmlContent+='</tr>';
	htmlContent+='</thead>';
	htmlContent+='<tbody>';
	for(var i = 0 ; i < words.length ; i++){
		htmlContent+='<tr>';
		htmlContent+='<td id="name_'+i+'" align="left">';
		htmlContent+=words[i];
		htmlContent+='</td>';
		htmlContent+='<td id="status_'+i+'" width="50" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="detail_'+i+'" width="50" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="wordResult_'+i+'" width="90" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="wordMapResult_'+i+'" width="90" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="wordSampleMeaning_'+i+'" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='</tr>';
	}
	htmlContent+='</tbody>';
	htmlContent+='</table>';
	htmlContent+='</div>';

	$('#addWordPanel').hide('slow');
	$('#addWordsStatus').html(htmlContent);
	$('#progressbar').progressbar({
		value: 0 
	});

	for(var i = 0 ; i < theradCount && i < words.length ; i++){
		addWord(currentAddIndex, words[currentAddIndex]);
		currentAddIndex++;
	}
} 


function addWord(index, word){
	addWordMark(index);
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/addOneWord.do",
		data: {word:word, category:$("#wordCategory :selected").val()},
		success: function(msg){
			var Result = msg;
			var resultPart = Result.split('_/');
			var isSuccess;
			if(eval(resultPart[0]) == 1)
				isSuccess = true;
			else
				isSuccess = false;
			
			addFinishMark(index, isSuccess, resultPart[1], resultPart[2], resultPart[3]);
			progress();

			if(words.length > currentAddIndex && isProcessing){
				addWord(currentAddIndex, words[currentAddIndex]);
				currentAddIndex++;
			}
		}
	});
}
function addWordMark(index){
	$("#status_"+index).html('<img src="${pageContext.request.contextPath}/images/vb-word-loader.gif" />');
	$("#detail_"+index).html('저장중');
}
function addFinishMark(index, isSuccess, addWordResult, addWordMapResult, addWordSampleMeaning){
	if(isSuccess){
		$("#status_"+index).html('<img src="${pageContext.request.contextPath}/images/vb-word-complete.png" />');
		$("#detail_"+index).html('성공');
	}else { 
		$("#status_"+index).html('<img src="${pageContext.request.contextPath}/images/vb-word-warn.png" />');
		$("#detail_"+index).html('실패');
	}
	$("#wordResult_"+index).html(addWordResult);
	$("#wordMapResult_"+index).html(addWordMapResult);
$("#wordSampleMeaning_"+index).html(addWordSampleMeaning);
}


function progress(){
	
	curProgress++;
	$('#progressbar').progressbar('option', 'value', curProgress*100/maxProgress);
}

function addMore(){
	$('#addWordPanel').show('slow'); 
}

function truncateArray(array){
	var truncatedArray = new Array();
	var insertCount = 0;
	for(var i = 0 ; i < array.length ; i++){
		if(trim(array[i]) != ''){
			truncatedArray[insertCount] = trim(array[i]);
			insertCount++;
		}
	}
	return truncatedArray;
}
</script>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>