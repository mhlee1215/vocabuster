<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="adminPanel">
크로울링 됬슴..

<a href="#" onclick="Recrowling();" >reCrowling word</a>
</div>
<script language="text/javascript" >
function Recrowling(){
	var data = { 
	};
	$('#adminPanel').load('/adminRecrowling.do', data); 
} 
</script>