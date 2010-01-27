<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@page import="org.wordbuster.domain.VBUser"%>
<%@ page import="org.wordbuster.PMF" %>

<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->




<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="org.wordbuster.service.VBUserService"%>

<%@page import="javax.jdo.Query"%>
<%@page import="org.wordbuster.domain.VBWord"%>
<%@page import="org.wordbuster.service.VBWordService"%><html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Buster your vocabulary!</title>
  </head>
  <%@include file="header.jsp"%>
  <body>
    <a href="http://code.google.com/intl/ko-KR/appengine/docs/java/datastore/queriesandindexes.html">Queries and Indexes</a>
    <%
    PersistenceManager pm = PMF.get().getPersistenceManager();
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    VBUser vBUser = null;
    boolean isFindVBUser = false;
    
    if (user != null) {
    	vBUser = VBUserService.getVBUser(request);
    	if(vBUser != null) isFindVBUser = true;
    }
    
    if (user != null && isFindVBUser) {
	%>
	<h1>Welcome vocabulary buster.</h1>
	<p>안녕, <%=user.getNickname()%>! (로그아웃 하려면 ->
	<a href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign out</a>.)</p>
	총 단어 갯수 <%=VBWordService.getVBWordCount()%><br>
	내 단어 갯수 <%=VBWordService.getVBUserWordCount(request)%>
	<script type="text/javascript"> 
			$(function(){
				// Tabs
				$('#tabs').tabs({ spinner: 'Retrieving data...' });
				$('#tabs2').tabs({ spinner: 'Retrieving data...' });
			});
	  </script>
	<div id="tabs"> 
		<ul> 
			<li><a href="/task/homeInfo.jsp"><span>Home</span></a></li>
			<li><a href="/task/addWords.jsp"><span>Add words</span></a></li> 
			<li><a href="/task/startQuz.jsp"><span>Start quiz</span></a></li>
			<li><a href="/myWordListForm.do"><span>My Word</span></a></li> 
			<li><a href="/wordListForm.do"><span>Word list</span></a></li>
			<li><a href="/adminForm.do"><span>Admin</span></a></li> 
		</ul> 	
	</div>
	<%
		} else {
			if(user == null){
	%>
	<p>Hello!
	<a href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign in</a>
	로긴 자비좀 ㄷㄷㄷㄷ</p>
	<%
			}else if(!isFindVBUser){
	%>
		<a href="/addUser.do">가입 자비좀 ㄷㄷ</a>
	<% 
			}
		}
	%>
  
  </body>
</html>
