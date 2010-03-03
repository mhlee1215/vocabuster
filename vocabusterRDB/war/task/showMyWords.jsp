<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Buster your vocabulary!</title>
</head>
<%@ include file="/header.jsp" %>
<body class="vb-body"> 
<div id="myWordListPanel" class="ui-widget ui-widget-content vb-page-body-div">
<h1 id="" class="ui-widget-header vb-page-header">My word</h1>
<form:form cssStyle="margin:0px;padding:0px;" action="${pageContext.request.contextPath}/myWordList.do" commandName="vBWordSearchVO" name="vBWordSearchVO">
	<form:hidden path="pageIndex"/>
	<form:select id="myWordListSearchIsvalid" path="searchIsvalid">
	<form:option value="Y">일반</form:option>
	<form:option value="N">미스</form:option>
	</form:select>
	<form:select id="myWordListSearchType" path="searchType">
	<form:option value="name">단어명</form:option>
	<form:option value="meaning">의미</form:option>
	</form:select>
	<form:select id="myWordListOrderType" path="searchOrderString">
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
	<button>submit</button>
</form:form>

<c:if test="${wordMapList != NULL}" >
<jsp:include page="/paging.jsp">
     <jsp:param name="formName" value="vBWordSearchVO"/>
     <jsp:param name="totalCount" value="${wordMapListCount}"/>
     <jsp:param name="countPerPage" value="${vBWordSearchVO.pageSize}"/>
     <jsp:param name="blockCount" value="${vBWordSearchVO.blockSize}"/>
     <jsp:param name="nowPage" value="${vBWordSearchVO.pageIndex}"/>
 </jsp:include>
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
	<caption>내가 등록한 단어 목록(총 ${ wordMapListCount }개)</caption>
	<thead>
    	<tr class="ui-widget-header ">
      		<th>단어명</th>
      		<th>뜻</th>
      		<th>입력수</th>
      		<th>맞춘수</th>
      		<th>틀린수</th>
      		<th>점수</th>
      		<th></th>
    	</tr>
  	</thead>
  	<tbody>
	<c:forEach items="${wordMapList}" var="wordMap">
		<tr>
			<td>
			${wordMap.wordName}
			</td>
			<td>
			${wordMap.meaningbundle }
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
			<td>
			<a href="javascript:deleteWordMap('${wordMap.wordName}')" >삭제</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</c:if>

<jsp:include page="/paging.jsp">
     <jsp:param name="formName" value="vBWordSearchVO"/>
     <jsp:param name="totalCount" value="${wordMapListCount}"/>
     <jsp:param name="countPerPage" value="${vBWordSearchVO.pageSize}"/>
     <jsp:param name="blockCount" value="${vBWordSearchVO.blockSize}"/>
     <jsp:param name="nowPage" value="${vBWordSearchVO.pageIndex}"/>
 </jsp:include>
</c:if>
</div> 


<script type="text/javascript" >

function searchWords(){
	var data = {
		searchType : $("#myWordListSearchType").val(),
		searchKeyword : $("#myWordListSearchKeyword").val(),
		searchResultType : $("#myWordListSearchResultType").val(),
		searchOrder : $("#myWordListOrderType").val() 
	};
	$('#myWordListPanel').load('${pageContext.request.contextPath}/myWordList.do', data); 
} 

function deleteWordMap(wordName){
	if(confirm(wordName+' 을 사용자 단어 목록에서 삭제 하시겠습니까?')){
		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/deleteMyword.do",
			data: 'searchKeyword='+wordName,
			success: function(msg){
				//refresh
				//alert('삭제 됬음');
				document.vBWordSearchVO.submit();
			}
		});
	}
}

</script>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>