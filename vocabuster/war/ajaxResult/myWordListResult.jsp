<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/task/showMyWords.jsp"%>
${ fn:length(wordList) }개 찾아쑴... 

<ul>
	<c:forEach items="${wordList}" var="word">
	<li><h2>${word.wordName}, ${word.insertedCount }</h2>
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