<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

찾아쑴... 

<ul>
	<c:forEach items="${wordList}" var="word">
	<li>${word.wordName}, ${word.insertedCount }
		<ul>
		<c:forEach items="${word.wordInfoList}" var="wordInfo">
		
		<li>${wordInfo.meaning}</li>
			
		</c:forEach>
		</ul>
	</c:forEach>
	</li>
</ul>