<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="wordListPanel">
<form:form cssStyle="margin:0px;padding:0px;" id="vBWordSearchVO" commandName="vBWordSearchVO">
<form:select id="wordListSearchType" path="searchType">
<form:option value="name">단어명</form:option>
<form:option value="meaning">의미</form:option>
</form:select>
<form:input id="wordListSearchKeyword" path="searchKeyword"></form:input>
<a href="#" onclick="searchWords();">search</a>
</form:form>
</div> 

<script language="text/javascript" >
function searchWords(){
	var data = { 
		searchType : $("#wordListSearchType").val(),
		searchKeyword : $("#wordListSearchKeyword").val() 
	};
	$('#wordListPanel').load('/wordList.do', data); 
} 
</script>