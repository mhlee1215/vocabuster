<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Buster your vocabulary!</title>
</head>
<%@ include file="/header.jsp" %>
<body class="vb-body">
<div id="adminPanel" class="ui-widget ui-widget-content vb-page-body-div">
<h1 id="" class="ui-widget-header vb-page-header">Admin</h1>
<form:form cssStyle="margin:0px;padding:0px;" id="vBWordSearchVO" commandName="vBWordSearchVO">
<form:input id="wordListSearchKeyword" path="searchKeyword"></form:input>
</form:form>
<a href="#" onclick="Recrowling();" >reCrowling word</a>

<div id="adminWordMapValidationPanel">
<a href="#" onclick="wordMapValidation();" >wordMapValidation</a>
</div>
<div id="adminDeleteWordMapPanel">
</div>
<a href="#" onclick="deleteWordMapAll();" >DeleteWordMapAll</a>

<div id="adminDeleteWordPanel">
</div>
<a href="#" onclick="deleteWordAll();" >DeleteWordAll</a>
</div>
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
	$.post('/adminDeleteWordAll.do', '', deleteWordAllFinish);
}

function deleteWordAllFinish(){
	$('#adminDeleteWordPanel').html('finish');
}
</script>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>