<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Buster your vocabulary!</title>
</head>
<%@ include file="/header.jsp" %>
<body class="vb-body">
<div id="wordListPanel" class="ui-widget ui-widget-content vb-page-body-div">
<h1 id="" class="ui-widget-header vb-page-header">Word list</h1>
<form:form cssStyle="margin:0px;padding:0px;" action="${pageContext.request.contextPath}/wordList.do" id="vBWordSearchVO" commandName="vBWordSearchVO" name="vBWordSearchVO">

<form:hidden path="pageIndex"/>

<form:select id="wordListSearchType" path="searchType">
<form:option value="name">단어명</form:option>
<form:option value="meaning">의미</form:option>
</form:select>
<form:select id="wordListOrderType" path="searchOrderString">
<form:option value="wordName asc">알파벳순</form:option>
<form:option value="insertcount desc">입력횟수순</form:option>
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
<button>submit</button>
</form:form>
<c:if test="${wordList != NULL}" >
<jsp:include page="/paging.jsp">
     <jsp:param name="formName" value="vBWordSearchVO"/>
     <jsp:param name="totalCount" value="${wordListCount}"/>
     <jsp:param name="countPerPage" value="${vBWordSearchVO.pageSize}"/>
     <jsp:param name="blockCount" value="${vBWordSearchVO.blockSize}"/>
     <jsp:param name="nowPage" value="${vBWordSearchVO.pageIndex}"/>
 </jsp:include>
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
	<caption>VB 단어 목록 (총 ${ wordListCount }개) </caption>
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
			${word.meaningbundle }
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
     <jsp:param name="formName" value="vBWordSearchVO"/>
     <jsp:param name="totalCount" value="${wordListCount}"/>
     <jsp:param name="countPerPage" value="${vBWordSearchVO.pageSize}"/>
     <jsp:param name="blockCount" value="${vBWordSearchVO.blockSize}"/>
     <jsp:param name="nowPage" value="${vBWordSearchVO.pageIndex}"/>
 </jsp:include>
 </c:if>
</div> 
<script type="text/javascript" >
function searchWords(){
	var data = { 
		searchType : $("#wordListSearchType").val(),
		searchKeyword : $("#wordListSearchKeyword").val(),
		searchResultType : $("#wordListSearchResultType").val(),
		searchOrder :  $("#wordListOrderType").val()
	};
	$('#wordListPanel').load('${pageContext.request.contextPath}/wordList.do', data); 
} 
</script>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>