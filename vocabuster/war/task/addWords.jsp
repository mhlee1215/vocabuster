<div id="addWordPanel">

add words....<br></br>

 <form name="wordForm" action="/addWords.do" method="post">
   <div><textarea id="wordContent" name="content" rows="3" cols="60">test</textarea></div>
   <a href="#" onclick="submitData();">add</a>
   <div><button>Submit</button></div>
 </form>

</div> 
<script language="text/javascript" >

function submitData(){
	var data = { content : $("#wordContent").val() };
	$('#addWordPanel').load('/addWords.do', data); 
	//alert($("#wordContent").val());

	//$("#tabs").tabs( 'url' , 1 , '/addWords.do?content=test' )
	
} 

</script>