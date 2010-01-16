<div id="wordListPanel">



<input type="text" id="keyword"></input>
<a href="#" onclick="searchWords();">search</a>

</div> 
<script language="text/javascript" >

function searchWords(){
	var data = { keyword : $("#keyword").val() };
	$('#wordListPanel').load('/wordList.do', data); 
} 

</script>