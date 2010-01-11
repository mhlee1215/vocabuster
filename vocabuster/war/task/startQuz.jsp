sart Quz...

<a href="#">start</a>

 <script type="text/javascript"> 
			$(function(){
				// Tabs
				$('#progressbar').progressbar({
					value: 0 
				});
			});
  </script>
<h2 class="demoHeaders">Progressbar</h2>	
<div id="progressbar"></div>

<script type="text/javascript">
var maxTic = 10;
var curTic = 0;
function progress(){
	
	curTic++;
	$('#progressbar').progressbar('option', 'value', curTic*100/maxTic);
	if(curTic == maxTic)
		clearInterval(timeoutID);
		
}
var timeoutID = setInterval(progress, eval(50));
</script>