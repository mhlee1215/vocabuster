<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="wordListPanel">
<form:form cssStyle="margin:0px;padding:0px;" id="vBWordSearchVO" commandName="vBWordSearchVO">
<form:select id="wordListSearchType" path="searchType">
<form:option value="name">단어명</form:option>
<form:option value="meaning">의미</form:option>
</form:select>
<form:select id="wordListOrderType" path="searchOrder">
<form:option value="wordName asc">알파벳순</form:option>
<form:option value="wrongOrder desc">입력횟수순</form:option>
<form:option value="wrongOrder">오답순</form:option>
<form:option value="correctOrder">정답순</form:option>
<form:option value="correctRateOrder">정답률순</form:option>
<form:option value="wrongRateOrder">오답률순</form:option>
<form:option value="admindateOrder">등록일순</form:option>
</form:select>

<form:input id="wordListSearchKeyword" path="searchKeyword"></form:input>
<form:select id="wordListSearchResultType" path="searchResultType">
<form:option value="table">테이블</form:option>
<form:option value="list">리스트</form:option>
</form:select>
<a href="#" onclick="searchWords();">search</a>
</form:form>
</div> 

<script language="text/javascript" >
function searchWords(){
	var data = { 
		searchType : $("#wordListSearchType").val(),
		searchKeyword : $("#wordListSearchKeyword").val(),
		searchResultType : $("#wordListSearchResultType").val(),
		searchOrder :  $("#wordListOrderType").val()
	};
	$('#wordListPanel').load('/wordList.do', data); 
} 
</script>