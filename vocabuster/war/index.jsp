<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Buster your vocabulary!</title>
  </head>
  <%@include file="header.jsp"%>
  <body>
  <script type="text/javascript"> 
			$(function(){
				// Tabs
				$('#tabs').tabs({ spinner: 'Retrieving data...' });
			});
  </script>
    <h1>Welcome vocabulary buster.</h1>
	<p>한글 사용 테스트 입니다.</p>
	 <p>Greetings, it is now ${now}</p>
    <table>
      <tr>
        <td colspan="2" style="font-weight:bold;">Available Servlets:</td>        
      </tr>
      <tr>
        <td><a href="/testapp2">TestApp</a></td>
      </tr>
    </table>

<div id="tabs"> 
	<ul> 
		<li><a href="/task/homeInfo.jsp"><span>Home</span></a></li>
		<li><a href="/task/addWords.jsp"><span>Add words</span></a></li> 
		<li><a href="/task/startQuz.jsp"><span>Start quiz</span></a></li> 
		<li><a href="/task/showWords.jsp"><span>Word list</span></a></li> 
	</ul> 
</div>

  </body>
</html>
