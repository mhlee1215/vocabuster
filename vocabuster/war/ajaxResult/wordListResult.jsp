<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/task/showWords.jsp"%>

${ fn:length(wordList) }개 찾아쑴... 
<c:if test="${vBWordSearchVO.searchResultType == 'list'}">
<ul>
	<c:forEach items="${wordList}" var="word">
	<li><h2>${word.wordName}<span>${word.soundHtml.value }</span>, ${word.insertedCount }</h2>
		사전용
		<ul>
		<c:forEach items="${word.wordInfoList}" var="wordInfo">
		<li>${wordInfo.meaning}</li>
		</c:forEach>
		</ul>
		테스트용
		<ul>
		<c:forEach items="${word.wordInfoList}" var="wordInfo">
		<li>${wordInfo.shortMeaning}</li>
		</c:forEach>
		</ul>
		<hr></hr>
	</c:forEach>
	</li>
</ul>
</c:if>
<c:if test="${vBWordSearchVO.searchResultType == 'table'}">
<table>
	<caption>VB 단어 목록 </caption>
	<thead>
    	<tr>
      		<th>단어명</th>
      		<th>뜻(1개)</th>
      		<th>입력수</th>
    	</tr>
  	</thead>
  	<tbody>
	<c:forEach items="${wordList}" var="word">
		<tr>
			<td>
			<b>${word.wordName}</b>&nbsp;${word.soundSymbol}&nbsp;<span>${word.soundHtml.value }</span>
			</td>
			<td>
			<c:forEach items="${word.wordInfoList}" var="wordInfo" varStatus="status">
			<c:if test="${status.count == 1}">
			${wordInfo.shortMeaning}
			</c:if>
			</c:forEach>
			</td>
			<td>
			${word.insertedCount}
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>