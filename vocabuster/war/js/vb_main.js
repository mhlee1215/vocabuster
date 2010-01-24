
$(function(){
	$('.quiz_button')
	.hover(	
		function(){ $(this).addClass("ui-state-hover");},
		function(){ $(this).removeClass("ui-state-hover");},
		function(){ $(this).css("cursor", "hand");}
	)
	.mousedown(function(){ $(this).addClass("ui-state-active");})
	.mouseup(function(){ $(this).removeClass("ui-state-active");})
	.mouseout(function(){$(this).removeClass("ui-state-active");})
	.focus(function(){$(this).blur();});
	
});

function fncBlank(){
	
}