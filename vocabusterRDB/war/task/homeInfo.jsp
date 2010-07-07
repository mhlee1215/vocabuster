<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Buster your vocabulary!</title>
</head>
<%@ include file="/header.jsp" %>
<body class="vb-body">
<div class="ui-widget ui-widget-content vb-page-body-div" >
<h1 id="" class="ui-widget-header vb-page-header">Home</h1>

<% if (userid != null) { %>
로그인 해주셔서 감사합니다.. 단어 빨리 외워 보아요<br></br>
<% } %>

<% if (userid == null) { %>
로그인 해주시면 어디서나 이용 가능합니다.(비로그인 시 아이피로 사용자 인식)<br></br>
<% } %>

본 사이트는 단어 빨리 외우기 위한 용도로 만들어 졌습니다.

<h3>기능</h3> 
<ul>
	<li>내 단어장 관리</li>
	<li>단어 퀴즈 (학습률 확인 ; 학습률은 모든 단어의 스코어가 2 인 경우 100 으로 봄)</li>
	<li>내 단어 리스트 보기</li>
</ul>

원하는 단어를 넣고 내가 약한 맞춤형 문제를 생성해주는게 장점이라 할 수 있습니다..<br>

많이 맞는건 안나오고, 틀린것만 나옵니다.
<br>
문의사항 연락 주세요..mhlee1215@gmail.com<br></br>
버그 많은건 알고 있으니 지적 자비좀.. ㅠㅠ
</div>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>