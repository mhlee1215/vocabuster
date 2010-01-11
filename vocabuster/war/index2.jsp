<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="header.jsp"%>
<body>
<script type="text/javascript"> 
			$(function(){
				// Tabs
				$('#tabs').tabs();
			});
</script>
<h1>Welcome vocabulary buster.</h1>
<p>Greetings, it is now <c:out value="${now}" /></p>
<p>한글 사용 테스트 입니다.</p>
한글 왜 안나옴?
<table>
	<tr>
		<td colspan="2" style="font-weight: bold;">Available Servlets:</td>
	</tr>
	<tr>
		<td><a href="/testapp2">TestApp</a></td>
	</tr>
</table>
<% 
       out.println(request.getAttribute("now"));
  %>
<h1>out with escapeXml=false</h1>
<c:out value="${test}" escapeXml="true" />
<br>
<h3>out with escapeXml=false</h3>
<c:out value="${test}" escapeXml="false" />

<div id="tabs"> 
	<ul> 
		<li><a href="#tabs-1">First</a></li> 
		<li><a href="#tabs-2">Second</a></li> 
		<li><a href="#tabs-3">Third</a></li> 
		 <li><a href="/task/addWords.jsp"><span>Content 1</span></a></li>
	</ul> 
	<div id="tabs-1">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</div> 
	<div id="tabs-2">Phasellus mattis tincidunt nibh. Cras orci urna, blandit id, pretium vel, aliquet ornare, felis. Maecenas scelerisque sem non nisl. Fusce sed lorem in enim dictum bibendum.</div> 
	<div id="tabs-3">Nam dui erat, auctor a, dignissim quis, sollicitudin eu, felis. Pellentesque nisi urna, interdum eget, sagittis et, consequat vestibulum, lacus. Mauris porttitor ullamcorper augue.</div> 
</div>
</body>
</html>
