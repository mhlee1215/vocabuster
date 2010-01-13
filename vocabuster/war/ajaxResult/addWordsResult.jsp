<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
${resultStr}
 <p>Greetings, it is now <c:out value="${resultStr}"/></p>
 
한글 깨지나??
<c:forEach items="${wordList}" var="word">
<ul>
	${word}
	<c:forEach items="${wordMeaning[word]}" var="wordInfo">
		<li>${wordInfo.meaning}</li>
	</c:forEach>
	
</ul> 	
</c:forEach>
