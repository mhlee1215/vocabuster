<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 id="" class="ui-widget-header vb-page-header">My word</h1>
<form:form cssStyle="margin:0px;padding:0px;" commandName="vBWordSearchVO">
<form:select id="myWordListSearchType" path="searchType">
<form:option value="name">단어명</form:option>
<form:option value="meaning">의미</form:option>
</form:select>
<form:select id="myWordListOrderType" path="searchOrder">
<form:option value="wordName asc">알파벳순</form:option>
<form:option value="insertCount desc">입력횟수순</form:option>
<form:option value="score asc">점수순</form:option>
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
${ fn:length(wordMapList) }개 찾아쑴... 
<c:if test="${vBWordSearchVO.searchResultType == 'list'}">
<ul>
	<c:forEach items="${wordMapList}" var="wordMap">
	<li><h2>${wordMap.word.wordName}, ${wordMap.word.insertedCount }</h2>
		사전용
		<ul>
		<c:forEach items="${wordMap.word.wordInfoList}" var="wordInfo">
		<li>${wordInfo.meaning}</li>
		</c:forEach>
		</ul>
		테스트용
		<ul>
		<c:forEach items="${wordMap.word.wordInfoList}" var="wordInfo">
		<li>${wordInfo.shortMeaning}</li>
		</c:forEach>
		</ul>
		<hr></hr>
	</c:forEach>
	</li>
</ul>
</c:if>
<c:if test="${vBWordSearchVO.searchResultType == 'table'}">
<div id="word-list">
<table class="ui-widget ui-widget-content">
	<caption>내가 등록한 단어 목록</caption>
	<thead>
    	<tr class="ui-widget-header ">
      		<th>단어명</th>
      		<th>뜻</th>
      		<th>입력수</th>
      		<th>맞춘수</th>
      		<th>틀린수</th>
      		<th>점수</th>
    	</tr>
  	</thead>
  	<tbody>
	<c:forEach items="${wordMapList}" var="wordMap">
		<tr>
			<td>
			${wordMap.word.wordName}
			</td>
			<td>
			<c:forEach items="${wordMap.word.wordInfoList}" var="wordInfo" varStatus="status">
			<c:if test="${status.count == 1}">
			${wordInfo.shortMeaning}
			</c:if>
			</c:forEach>
			</td>
			<td>
			${wordMap.insertCount}
			</td>
			<td>
			${wordMap.answerCount}
			</td>
			<td>
			${wordMap.wrongCount}
			</td>
			<td>
			${wordMap.score}
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</c:if>