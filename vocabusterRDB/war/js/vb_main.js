
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

function trim(str) {
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

String.prototype.trim = function() {
    return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}
