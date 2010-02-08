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
home...

단어 빨랑 외워서 끝내자규... ㄷㄷㄷ
<% } %>
</div>
<jsp:include page="/footer.jsp">
     <jsp:param name="" value=""/>
</jsp:include>
</body>
</html>