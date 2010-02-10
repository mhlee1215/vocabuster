<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 id="" class="ui-widget-header vb-page-header">Word list</h1>
<form:form cssStyle="margin:0px;padding:0px;" id="vBWordSearchVO" commandName="vBWordSearchVO">
<form:select id="wordListSearchType" path="searchType">
<form:option value="name">단어명</form:option>
<form:option value="meaning">의미</form:option>
</form:select>
<form:select id="wordListOrderType" path="searchOrderString">
<form:option value="wordName asc">알파벳순</form:option>
<form:option value="wrongOrder desc">입력횟수순</form:option>
<form:option value="score asc">점수순</form:option>
<!--<form:option value="wrongOrder">오답순</form:option>
<form:option value="correctOrder">정답순</form:option>
<form:option value="correctRateOrder">정답률순</form:option>
<form:option value="wrongRateOrder">오답률순</form:option>
<form:option value="admindateOrder">등록일순</form:option>
--></form:select>

<form:input id="wordListSearchKeyword" path="searchKeyword"></form:input>
<form:select id="wordListSearchResultType" path="searchResultType">
<form:option value="table">테이블</form:option>
<form:option value="list">리스트</form:option>
</form:select>
<a href="#" onclick="searchWords();">search</a>
</form:form>
${ fn:length(wordList) }개 찾아쑴... 
<c:if test="${vBWordSearchVO.searchResultType == 'list'}">
<ul>
	<c:forEach items="${wordList}" var="word">
	<li><h2>${word.wordName}<span>${word.soundHtml }</span>, ${word.insertedCount }</h2>
		사전용
		<ul>
		<c:forEach items="${word.wordInfoList}" var="wordInfo">
		<li>${wordInfo.fullmeaning}</li>
		</c:forEach>
		</ul>
		테스트용
		<ul>
		<c:forEach items="${word.wordInfoList}" var="wordInfo">
		<li>${wordInfo.shortmeaning}</li>
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
	<caption>VB 단어 목록 </caption>
	<thead>
    	<tr class="ui-widget-header ">
      		<th>단어명</th>
      		<th>뜻(1개)</th>
      		<th>입력수</th>
    	</tr>
  	</thead>
  	<tbody>
	<c:forEach items="${wordList}" var="word">
		<tr>
			<td>
			<b>${word.wordName}</b>&nbsp;${word.soundSymbol}&nbsp;<span>${word.soundHtml }</span>
			</td>
			<td>
			<c:forEach items="${word.wordInfoList}" var="wordInfo" varStatus="status">
			<c:if test="${status.count == 1}">
			${wordInfo.shortmeaning}
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
</div>
</c:if>
<jsp:include page="/paging.jsp">
     <jsp:param name="actionPath" value="boardList.jsp"/>
     <jsp:param name="totalCount" value="100"/>
     <jsp:param name="countPerPage" value="10"/>
     <jsp:param name="blockCount" value="5"/>
     <jsp:param name="nowPage" value="3"/>
 </jsp:include>