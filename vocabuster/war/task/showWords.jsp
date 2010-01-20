<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="wordListPanel">
<select id="searchType">
<option value="name">단어명</option>
<option value="meaning">의미</option>
</select>
<input type="text" id="keyword"></input>
<a href="#" onclick="searchWords();">search</a>

</div> 
<script language="text/javascript" >

function searchWords(){
	var data = { keyword : $("#keyword").val() };
	$('#wordListPanel').load('/wordList.do', data); 
} 

</script>