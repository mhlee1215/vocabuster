<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<div id="myWordListPanel">
<form:form cssStyle="margin:0px;padding:0px;" commandName="vBWordSearchVO">
<form:select id="myWordListSearchType" path="searchType">
<form:option value="name">단어명</form:option>
<form:option value="meaning">의미</form:option>
</form:select>
<form:select id="myWordListOrderType" path="searchOrder">
<form:option value="wordName asc">알파벳순</form:option>
<form:option value="insertCount desc">입력횟수순</form:option>
<form:option value="wrongCount desc">오답순</form:option>
<form:option value="answerCount desc">정답순</form:option>
<form:option value="answerRate desc">정답률순</form:option>
<form:option value="wrongRate desc">오답률순</form:option>
<!--<form:option value="admindateOrder">등록일순</form:option>
--></form:select>
<form:select id="myWordListSearchResultType" path="searchResultType">
<form:option value="table">테이블</form:option>
<form:option value="list">리스트</form:option>
</form:select>
<form:input id="myWordListSearchKeyword" path="searchKeyword"></form:input>
<a href="#" onclick="searchWords();">search</a>
</form:form>
</div> 
<script language="text/javascript" >

function searchWords(){
	var data = {
		searchType : $("#myWordListSearchType").val(),
		searchKeyword : $("#myWordListSearchKeyword").val(),
		searchResultType : $("#myWordListSearchResultType").val(),
		searchOrder : $("#myWordListOrderType").val() 
	};
	$('#myWordListPanel').load('/myWordList.do', data); 
} 

</script>