<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

다음의 단어가 더해졌습니다.

<c:forEach items="${wordList}" var="word">
<ul>
	<h2>${word}</h2>
	사전의미
	<c:forEach items="${wordMeaning[word]}" var="wordInfo">
		<li>${wordInfo.meaning}</li>
	</c:forEach>
	테스트용
	<c:forEach items="${wordMeaning[word]}" var="wordInfo">
		<li>${wordInfo.shortMeaning}</li>
	</c:forEach>
</ul> 	
<br></br>
</c:forEach>
<br></br>

다음의 단어는 이미 존재하는 단어입니다.
<c:forEach items="${wordListAlreadyExisted}" var="word">
	<h2>${word}</h2>
</c:forEach>
<br></br>

다음의 단어는 오타 혹은 기타의 이유로 사전적 의미를 찾지못한 경우입니다.
<c:forEach items="${wordListTypo}" var="word">
	<h2>${word}</h2>
</c:forEach>