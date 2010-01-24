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
<form:option value="wrongOrder">오답순</form:option>
<form:option value="correctOrder">정답순</form:option>
<form:option value="correctRateOrder">정답률순</form:option>
<form:option value="wrongRateOrder">오답률순</form:option>
<form:option value="admindateOrder">등록일순</form:option>
</form:select>
<a href="#" onclick="searchWords();">search</a>
</form:form>
</div> 
<script language="text/javascript" >

function searchWords(){
	var data = {
		searchType : $("#myWordListSearchType").val(),
		searchOrder : $("#myWordListOrderType").val() 
	};
	$('#myWordListPanel').load('/myWordList.do', data); 
} 

</script>