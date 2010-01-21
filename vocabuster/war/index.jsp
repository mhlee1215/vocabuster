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
<%@page import="com.google.appengine.api.datastore.KeyFactory"%><html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Buster your vocabulary!</title>
  </head>
  <%@include file="header.jsp"%>
  <body>
    <%
    PersistenceManager pm = PMF.get().getPersistenceManager();
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    VBUser vBUser = null;
    boolean isFindVBUser = false;
    
    if (user != null) {
    	String query = "select from " + VBUser.class.getName()+" "+
    					"where key == userKey " +
        				"parameters String userKey ";
    	Key userKey = KeyFactory.createKey(VBUser.class.getSimpleName(), user.getEmail());
    	List<VBUser> userList = (List<VBUser>) pm.newQuery(query).execute(userKey);
    	if(userList.size() > 0) isFindVBUser = true;
    }
    
    if (user != null && isFindVBUser) {
	%>
	<h1>Welcome vocabulary buster.</h1>
	<p>안녕, <%=user.getNickname()%>! (로그아웃 하려면 ->
	<a href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign out</a>.)</p>
	
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
			<li><a href="/task/showWords.jsp"><span>Word list</span></a></li> 
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
