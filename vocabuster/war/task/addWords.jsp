<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="addWordPanel">
<h2>단어 추가</h2>
 <form name="wordForm" action="/addWords.do" method="post">
   <div>
   <select id="wordCategory" name="wordCategory">
   		<option value="etc">기타</option>
   </select>
   </div>	
   <div><textarea id="wordContent" name="content" rows="3" cols="60">test</textarea></div>
   <a href="#" onclick="submitData();">add</a>
 </form>
</div>
<div id="addWordsStatus">
</div> 
<table><tr><td align="left"></td></tr></table>
<script language="text/javascript" >
var words;
var maxProgress;
var curProgress;
var isProcessing = true;

function submitData(){
	if($("#wordContent").val() == ''){
		alert('입력할 단어를 넣고 추가하는것이 좋을 것이야..');
		return;
	}
	var data = { content : $("#wordContent").val() };
	var htmlContent = ''
	words = $("#wordContent").val().split('\n');

	maxProgress = words.length;
	curProgress = 0;

	htmlContent+='전체 입력수.. '+words.length+'개...<a href="#">멈춤</a>&nbsp;<a href="#">계속</a>';
	htmlContent+='<br><br>';
	htmlContent+='<div id="progressbar" style="height:5px;"></div><br>';
	htmlContent+='<table border="0">';
	for(var i = 0 ; i < words.length ; i++){
		htmlContent+='<tr>';
		htmlContent+='<td id="name_'+i+'" width="100" align="left">';
		htmlContent+=words[i];
		htmlContent+='</td>';
		htmlContent+='<td id="status_'+i+'" width="50" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="detail_'+i+'" width="50" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="wordResult_'+i+'" width="50" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='<td id="wordMapResult_'+i+'" width="50" align="center">';
		htmlContent+='..';
		htmlContent+='</td>';
		htmlContent+='</tr>';
	}
	htmlContent+='</table>';

	$('#addWordPanel').hide('slow');
	$('#addWordsStatus').html(htmlContent);
	$('#progressbar').progressbar({
		value: 0 
	});

	addWord(0, words[0]);
	//addWordMark(0);
	//addFinishMark(0, false);
	//$('#addWordPanel').load('/addWords.do', data); 
} 


function addWord(index, word){
	addWordMark(index);
	$.ajax({
		type: "POST",
		url: "/addOneWord.do",
		data: 'word='+word,
		success: function(msg){
			var Result = msg;
			var resultPart = Result.split('_/');
			var isSuccess;
			if(resultPart[0] == '1')
				isSuccess = true;
			else
				isSuccess = false;

			
			addFinishMark(index, true, resultPart[1], resultPart[2]);
			progress();

			if(words.length >= index+1 && isProcessing)
				addWord(index+1, words[index+1]);
		}
	});
}
function addWordMark(index){
	$("#status_"+index).html('<img src="/images/vb-word-loader.gif" />');
	$("#detail_"+index).html('저장중');
}
function addFinishMark(index, isSuccess, addWordResult, addWordMapResult){
	if(isSuccess){
		$("#status_"+index).html('<img src="/images/vb-word-complete.png" />');
		$("#detail_"+index).html('성공');
	}else { 
		$("#status_"+index).html('<img src="/images/vb-word-warn.png" />');
		$("#detail_"+index).html('실패');
	}
	$("#wordResult_"+index).html(addWordResult);
	$("#wordMapResult_"+index).html(addWordMapResult);
}


function progress(){
	
	curProgress++;
	$('#progressbar').progressbar('option', 'value', curProgress*100/maxProgress);
}

function addMore(){
	$('#addWordPanel').show('slow'); 
}
</script>