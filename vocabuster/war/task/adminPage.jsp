<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="adminPanel">
<form:form cssStyle="margin:0px;padding:0px;" id="vBWordSearchVO" commandName="vBWordSearchVO">
<form:input id="wordListSearchKeyword" path="searchKeyword"></form:input>
</form:form>
<a href="#" onclick="Recrowling();" >reCrowling word</a>
</div>
<script language="text/javascript" >
function Recrowling(){
	var data = { 
		searchKeyword : $("#wordListSearchKeyword").val(),
	};
	$('#adminPanel').load('/adminRecrowling.do', data); 
} 
</script>