<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="org.wordbuster.domain.VBUser"%>
<%@ page import="org.wordbuster.service.VBUserService"%>
<%@ page import="org.wordbuster.domain.VBWord"%>
<%@ page import="org.wordbuster.service.VBWordService"%><html>
<link type="text/css" href="${pageContext.request.contextPath}/jquery/css/redmond-theme/jquery-ui-1.8rc1.custom.css" rel="stylesheet" />	
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.4.1.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-ui-1.8rc1.custom.min.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/css/vb_main.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/vb_main.js"></script>
<style type="text/css">
	body{ font: 72.5% "Trebuchet MS", sans-serif; width:800px;}
	.demoHeaders { margin-top: 2em; }
	#dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
	#dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
	ul#icons {margin: 0; padding: 0;}
	ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
	ul#icons span.ui-icon {float: left; margin: 0 4px;}
	.ui-button-text { font-size:12px;}
	.ui-progressbar-value { background-image: url(${pageContext.request.contextPath}/images/pbar-ani.gif); }
	table { font-size:12px; }
	div#word-list table { margin: 1em 0; border-collapse: collapse; width: 100%; }
	div#word-list table td, div#word-list table th { font-size:11px; border: 1px solid #eee; padding: .6em 10px; text-align: left; }
	.vb-page-header { padding-bottom:3px;padding-top:3px;padding-left:5px; margin-top:0px; margin-bottom:0px;}
	.vb-page-header-div { width:750px; }
	.vb-page-body-div { width:750px; }
	.vb-page-footer-div { width:750px; }
	.vb-body { margin-top:0px; margin-left:5px; }
</style>	
  
<% 
	String remoteHost = request.getRemoteHost();
	String userid = (String)session.getAttribute("userid");
	String isLogin = (String)session.getAttribute("islogin");
	String externalId = remoteHost;
	if("true".equals(isLogin)){
	    externalId = (String)session.getAttribute("externalid");
	}
%>
  <script type="text/javascript"> 
		$(function(){
			jQuery.fx.off = true;
			$("#radio1").buttonset();

			$("#loginBtn").button({
	            icons: {
	                primary: 'ui-icon-locked'
	            }
	        });

			$("#registerBtn").button({
	            icons: {
	                primary: 'ui-icon-star'
	            }
	        });

			$("#logoutBtn").button({
	            icons: {
	                primary: 'ui-icon-unlocked'
	            }
	        });
			
		});

		function move(index){
			if(index == '1')
				document.location = '${pageContext.request.contextPath}/index.do';
			else if(index == '2')
				document.location = '${pageContext.request.contextPath}/addWords.do';
			else if(index == '3')
				document.location = '${pageContext.request.contextPath}/startQuiz.do';
			else if(index == '4')
				document.location = '${pageContext.request.contextPath}/myWordListForm.do';
			else if(index == '5')
				document.location = '${pageContext.request.contextPath}/wordListForm.do';
			else if(index == '6')
				document.location = '${pageContext.request.contextPath}/adminForm.do';
		}
</script>
  <table>
  	<tr>
  		<td><a href="#"><img style="width:250px;height:100px;" src="${pageContext.request.contextPath}/images/title1.jpg"></img></a></td>
  		<td>
  			<% 
  			if (userid != null) {
  			%>
 				<%if(!"true".equals(isLogin)){ %>
 				<button onclick="showLogin();" id="loginBtn" >LOGIN</button>
 				<button onclick="showRegister();" id="registerBtn" >REGISTER</button>
				<%}else{%>
				<button onclick="document.location='/logout.do'" id="logoutBtn" >LOGOUT</button>
				안녕! <%=externalId%>(<%=userid%>)
				<%} %>
  			
			<br>
			총 단어 갯수 <%=session.getAttribute("wordcount") %> &nbsp;&nbsp;
			내 단어 갯수 <%=session.getAttribute("userwordcount") %><br>
			<br>
			<!-- 네비게이터 시작 -->
			<div id="radio1">
			<form:form name="navigationForm" commandName="vBWordSearchVO" method="post" target="_self">

			<form:radiobutton path="pageName" id="radio1" value="home" onclick="move('1')"/><label for="radio1">Home</label>
			<form:radiobutton path="pageName" id="radio2" value="addwords" onclick="move('2')"/><label for="radio2">Add Words</label>
			<form:radiobutton path="pageName" id="radio3" value="quiz" onclick="move('3')"/><label for="radio3">Quiz</label>
			<form:radiobutton path="pageName" id="radio4" value="mywords" onclick="move('4')"/><label for="radio4">My words</label>
			<form:radiobutton path="pageName" id="radio5" value="wordlist" onclick="move('5')"/><label for="radio5">Word list</label>
			<form:radiobutton path="pageName" id="radio6" value="admin" onclick="move('6')"/><label for="radio6">Admin</label>
											
			</form:form>
			</div>
			<!-- 네비게이터 끝 -->
			
  			<%
  			}else {
 				if(userid == null){
 					%>
 					
 					<script type="text/javascript">
 					document.location = '${pageContext.request.contextPath}/index.do';
 					</script> 
 			<%
 				}
 			}
  			%>
  		</td>
  	</tr>
</table>
<jsp:include page="/loginUI.jsp"></jsp:include>