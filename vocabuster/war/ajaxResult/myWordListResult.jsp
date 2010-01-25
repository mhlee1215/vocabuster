<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/task/showMyWords.jsp"%>
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
<table>
	<caption>내가 등록한 단어 목록</caption>
	<thead>
    	<tr>
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
</c:if>