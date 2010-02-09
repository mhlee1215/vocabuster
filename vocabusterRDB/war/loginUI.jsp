<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
.loginUI body { font-size: 62.5%; }
.loginUI input.text { margin-bottom:5px; width:95%; padding: .4em; }
.loginUI .login-text{ margin-bottom:5px; width:95%; padding: .4em; }
.loginUI fieldset { padding:0; border:0; margin-top:25px; }
.loginUI h1 { font-size: 1.2em; margin: .6em 0; }
.loginUI .ui-button { outline: 0; margin:0; padding: .4em 1em .5em; text-decoration:none;  !important; cursor:pointer; position: relative; text-align: center; }
.loginUI .ui-dialog .ui-state-highlight, .loginUI .ui-dialog .ui-state-error { padding: .3em;  }
</style>

<script type="text/javascript">
$(function() {
	
	var signinId = $("#signinId"),
		signinPassword = $("#signinPassword"),
		registerId = $("#registerId"),
		registerPassword = $("#registerPassword"),
		registerRePassword = $("#registerRePassword"),
		signinAllFields = $([]).add(signinId).add(signinPassword),
		registerAllFields = $([]).add(registerId).add(registerPassword),
		signinTips = $("#signinValidateTips"),
		registerTips = $("#registerValidateTips");

	function updateTips(tips, t) {
		tips.text(t).effect("highlight",{},1500);
	}

	function checkLength(o,n,min,max, tips) {

		if ( o.val().length > max || o.val().length < min ) {
			o.addClass('ui-state-error');
			updateTips(tips, "Length of " + n + " must be between "+min+" and "+max+".");
			return false;
		} else {
			return true;
		}
	}

	function checkPassword(o, o1, string1, string2, tips) {

		if (string1.val() != string2.val()) {
			o.addClass('ui-state-error');
			o1.addClass('ui-state-error');
			updateTips(tips, "Password and Re-password are mismatched");
			return false;
		} else {
			return true;
		}
	}

	function checkRegexp(o,regexp, n, tips) {

		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass('ui-state-error');
			updateTips(tips, n);
			return false;
		} else {
			return true;
		}

	}
	
	$("#registerDialog").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 250,
		modal: true,
		buttons: {
			'Create an account': function() {
				var bValid = true;
				registerAllFields.removeClass('ui-state-error');

				//bValid = bValid && checkLength(name,"username",3,16);
				bValid = bValid && checkLength(registerId,"email",6,80, registerTips);
				bValid = bValid && checkLength(registerPassword,"password",5,16, registerTips);
				bValid = bValid && checkPassword(registerPassword, registerRePassword, registerPassword,registerRePassword, registerTips);
				//alert(registerpassword+','+registerRePassword);
				//bValid = false;
				//bValid = bValid && checkRegexp(name,/^[a-z]([0-9a-z_])+$/i,"Username may consist of a-z, 0-9, underscores, begin with a letter.");
				// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
				bValid = bValid && checkRegexp(registerId,/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,"eg. user@coex.org", registerTips);
				bValid = bValid && checkRegexp(registerPassword,/^([0-9a-zA-Z])+$/,"Password field only allow : a-z 0-9", registerTips);

				if (bValid) {
					document.registerForm.submit();
				}
				
			},
			Cancel: function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			registerAllFields.val('').removeClass('ui-state-error');
		}
	});

	function goSubmitSignin() {
		var bValid = true;
		signinAllFields.removeClass('ui-state-error');

		//bValid = bValid && checkLength(name,"username",3,16);
		
		bValid = bValid && checkLength(signinId,"email",6,80, signinTips);
		//alert('1'+bValid);
		bValid = bValid && checkLength(signinPassword,"password",5,16, signinTips);

		//alert('2'+bValid);
		//bValid = bValid && checkRegexp(name,/^[a-z]([0-9a-z_])+$/i,"Username may consist of a-z, 0-9, underscores, begin with a letter.");
		// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
		bValid = bValid && checkRegexp(signinId,/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,"eg. ui@jquery.com", signinTips);
		//alert('3'+bValid);
		bValid = bValid && checkRegexp(signinPassword,/^([0-9a-zA-Z])+$/,"Password field only allow : a-z 0-9", signinTips);

		//alert('4'+bValid);
		if (bValid) {
			document.signinForm.submit();
		}

		
		
	}
	
	$("#signinDialog").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 220,
		modal: true,
		buttons: {
			'Submit': goSubmitSignin,
			Cancel: function() {
				$(this).dialog('close');
			}
			/*,
			'Register': function() {
				$(this).dialog('close');
				showRegister();
			}
			*/
		},
		close: function() {
			signinAllFields.val('').removeClass('ui-state-error');
		}
	});
	
	
	function showRegister(){
		$('#registerDialog').dialog('open');
	}
	this.showRegister = showRegister;

	function showLogin(){
		$('#signinDialog').dialog('open');
	}
	this.showLogin = showLogin;

	function showLoginCompleteMessage(){
	}
	this.showLoginCompleteMessage = showLoginCompleteMessage;

	function showLoginFailMessage(){
		$("#loginFailMessage").dialog({
			bgiframe: true,
			modal: true,
			buttons: {
				
				Ok: function() {
					$(this).dialog('close');
				},
				'Register': function() {
					$(this).dialog('close');
					showRegister();
				}
			}
		});
	}
	
	function showLogoutCompleteMessage(){
	}
	showLogoutCompleteMessage = showLogoutCompleteMessage;
	
	function showRegisterCompleteMessage(){
		$("#registerCompleteMessage").dialog({
			bgiframe: true,
			modal: true,
			buttons: {
				Ok: function() {
					$(this).dialog('close');
				}
			}
		});
	}
	this.showRegisterCompleteMessage = showRegisterCompleteMessage;

	function showRegisterFailMessage(){
		$("#registerFailMessage").dialog({
			bgiframe: true,
			modal: true,
			buttons: {
				Ok: function() {
					$(this).dialog('close');
				}
			}
		});
	} 
	this.showRegisterFailMessage = showRegisterFailMessage;

	//alert('${userId}');
	if('${loginComplete}' == 'true'){
		showLoginCompleteMessage();
	}
	else if('${loginFail}' == 'true'){
		showLoginFailMessage();
	}
	else if('${logoutComplete}' == 'true'){
		showLogoutCompleteMessage();
	}
	else if('${registerComplete}' == 'true'){
		showRegisterCompleteMessage();
	}
	else if('${registerFail}' == 'true'){
		showRegisterFailMessage();
	}

	$("#signinPassword").bind("keypress", function(event) {
		if(event.keyCode == 13){
			goSubmitSignin();
		}else{
			
		}
	});
	
});


</script>

<div class="loginInterface">

<div id="registerDialog" title="Create new user" style="display:none" class="loginUI">
	<p id="registerValidateTips">All form fields are required.</p>

	<form name="registerForm" action="${pageContext.request.contextPath}/register.do" method="post">
	<fieldset>
		<label for="id">Email</label>
		<input style="width:95%;margin-bottom:5px;" type="text" name="id" id="registerId" value="" class="text ui-widget-content ui-corner-all login-text" />
		<label for="password">Password</label>
		<input style="width:95%;margin-bottom:5px;" type="password" name="password" id="registerPassword" value="" class="text ui-widget-content ui-corner-all login-text" />
		<label for="password">Re-password</label>
		<input style="width:95%;margin-bottom:5px;" type="password" name="repassword" id="registerRePassword" value="" class="text ui-widget-content ui-corner-all login-text" />
	</fieldset>
	</form>
</div>

<div id="signinDialog" title="Sign in" style="display:none" class="loginUI">
	<p id="signinValidateTips">All form fields are required.</p>

	<form name="signinForm" action="${pageContext.request.contextPath}/login.do"  method="post">
	<fieldset>
		<label>Email</label>
		<input style="width:95%;margin-bottom:5px;" type="text" name="id" id="signinId" value="mhlee1215@gmail.com" class="text ui-widget-content ui-corner-all" />
		<br>
		<label for="password">Password</label>
		<input style="width:95%;margin-bottom:5px;" type="password" name="password" id="signinPassword" value="1124421" class="text ui-widget-content ui-corner-all" />
	</fieldset>
	</form>
</div>

<div id="loginFailMessage" style="display:none" title="Sign-in fail">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		We cannot find your information.<br>
		You need to register.
	</p>
</div>

<div id="registerCompleteMessage" style="display:none" title="Register complete">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		Email '${submittedUserId}' is registered completely.
	</p>
</div>

<div id="registerFailMessage" style="display:none" title="Register fail">
	<p>
		<span class="ui-icon ui-icon-circle-close" style="float:left; margin:0 7px 50px 0;"></span>
		Registered fail.<br>
		Email '${submittedUserId}' is <b>already existed</b>. 
	</p>
</div>

</div><!-- End login interface -->

