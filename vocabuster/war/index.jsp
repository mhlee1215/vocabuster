<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=utf-8"%>
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
  </head>
  <body>
    <h1>Hello App Engine!!!</h1>
	<p>Greetings, it is now <c:out value="${now}"/></p>
    <table>
      <tr>
        <td colspan="2" style="font-weight:bold;">Available Servlets:</td>        
      </tr>
      <tr>
        <td><a href="/testapp2">TestApp</a></td>
      </tr>
    </table>
    <% 
       out.println(request.getAttribute("now"));
  %>
  <h1>out with escapeXml=false</h1>
<c:out value="${test}" escapeXml="true" /><br>
<h3>out with escapeXml=false</h3>
<c:out value="${test}" escapeXml="false" />
  </body>
</html>
