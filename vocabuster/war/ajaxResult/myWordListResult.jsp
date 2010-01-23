<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/task/showMyWords.jsp"%>
${ fn:length(wordList) }개 찾아쑴... 

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