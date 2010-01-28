<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="adminPanel">
<form:form cssStyle="margin:0px;padding:0px;" id="vBWordSearchVO" commandName="vBWordSearchVO">
<form:input id="wordListSearchKeyword" path="searchKeyword"></form:input>
</form:form>
<a href="#" onclick="Recrowling();" >reCrowling word</a>
</div>
<div id="adminWordMapValidationPanel">
<a href="#" onclick="wordMapValidation();" >wordMapValidation</a>
</div>
<div id="adminDeleteWordMapPanel">
</div>
<a href="#" onclick="deleteWordMapAll();" >DeleteWordMapAll</a>

<div id="adminDeleteWordPanel">
</div>
<a href="#" onclick="deleteWordAll();" >DeleteWordAll</a>

<script type="text/javascript" >
function Recrowling(){
	var data = { 
		searchKeyword : $("#wordListSearchKeyword").val(),
	};
	$('#adminPanel').load('/adminRecrowling.do', data); 
} 

function wordMapValidation(){
	var data = {
	};
	$('#adminWordMapValidationPanel').load('/adminWordMapValidation.do', data);
}

function deleteWordMapAll(){
	$.post('/adminDeleteWordMapAll.do', '', deleteWordMapAllFinish);
}
function deleteWordMapAllFinish(){
	$('#adminDeleteWordMapPanel').html('finish');
}

function deleteWordAll(){
	$.post('/adminDeleteWordAll.do', '', deleteWordMapAllFinish);
}

function deleteWordMapAllFinish(){
	$('#adminDeleteWordPanel').html('finish');
}
</script>