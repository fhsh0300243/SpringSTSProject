<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>台股練兵系統</title>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,600" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../css/UserLogin.css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+TC&display=swap" rel="stylesheet"> 
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
<section class="ff-login">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="ff-login-box">
                    <form method="post" name="lForm">
                        <h2 class="title">台股練兵系統</h2>
                        <input type="text" class="form-control form-control-lg font-weight-light mt-4" placeholder="Username" name="userName">
                        <span class="wrong">${errorMsgMap.AccountEmptyError}</span>
                        <input type="password" class="form-control form-control-lg font-weight-light mt-3" placeholder="Password" name="userPassword">
                        <span class="wrong">${errorMsgMap.PasswordEmptyError}</span>
                        <div class="custom-control custom-checkbox ml-4 mt-3">
                            <input type="checkbox" name="remember-me" class="custom-control-input" id="rememberMe">
                            <label class="custom-control-label" for="rememberMe">Remember Me</label>
                        </div>
                        <input type="submit" value="Login" class="btn btn-primary btn-lg mt-3 ff-login-btn font-weight-bold" onclick="document.lForm.action='userLoginCheck'"/>
                        <span class="wrong">${errorMsgMap.LoginError}</span>
                        <span class="registerOK">${msgMapFromRegister.registerOK}</span>
                        <span class="wrong">${msgMapFromRegister.registerError}</span>
                    	<input type="submit" value="Create an account" class="btn btn-primary btn-lg mt-3 ff-login-btn font-weight-bold" onclick="document.lForm.action='userRegister'"/>
                    	<span class="registerOK">${MsgFromPwdReset.resetSuccess}</span>
                    	<span class="wrong">${MsgFromActivateAccount.invalidAccount}</span>
                    	<span class="wrong">${MsgFromActivateAccount.notActivated}</span>
                    	<span class="registerOK">${MsgFromActivateAccount.ActivateSucess}</span>
                    	<div><a href="forgetPwd">忘記密碼?</a></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<table>
	<tr>
		<td id="td1">
		</td>
		<td id="td2">
		</td>
		<td id="td3">
		</td>
	</tr>
</table>
<script>

$.ajax({
	url : "../tools/getIndexFromInternet",
	type : "GET",
	success : function(Str) {
		index = JSON.parse(Str);
		if(parseFloat(index.TAIEXZ_Y)>0){
			document.getElementById('td1').innerHTML="<h3 class='subTitle'>"+index.TAIEXname+"</h3><div class='TAIEX' style='color:red;'>"+index.TAIEX+"</div><span class='TAIEXY'>昨日收盤: "+index.TAIEXY+" </span><span style='color:red;'>漲跌: </span><span style='color:red;'>"+index.TAIEXZ_Y+"</span>"
			}
		if(parseFloat(index.TAIEXZ_Y)==0){
			document.getElementById('td1').innerHTML="<h3 class='subTitle'>"+index.TAIEXname+"</h3><div class='TAIEX' style='color:white;'>"+index.TAIEX+"</div><span class='TAIEXY'>昨日收盤: "+index.TAIEXY+" </span><span style='color:white;'>漲跌: </span><span style='color:white;'>"+index.TAIEXZ_Y+"</span>"
			}
		if(parseFloat(index.TAIEXZ_Y)<0){
			document.getElementById('td1').innerHTML="<h3 class='subTitle'>"+index.TAIEXname+"</h3><div class='TAIEX' style='color:#77ff00;'>"+index.TAIEX+"</div><span class='TAIEXY'>昨日收盤: "+index.TAIEXY+" </span><span style='color:#77ff00;'>漲跌: </span><span style='color:#77ff00;'>"+index.TAIEXZ_Y+"</span>"
			}

		if(parseFloat(index.BUYZ_Y)>0){
			document.getElementById('td2').innerHTML="<h3 class='subTitle'>"+index.BUYname+"</h3><div class='TAIEX' style='color:red;'>"+index.BUY+"</div><span class='TAIEXY'>昨日收盤: "+index.BUYY+" </span><span style='color:red;'>漲跌: </span><span style='color:red;'>"+index.BUYZ_Y+"</span>"
			}
		if(parseFloat(index.BUYZ_Y)==0){
			document.getElementById('td2').innerHTML="<h3 class='subTitle'>"+index.BUYname+"</h3><div class='TAIEX' style='color:white;'>"+index.BUY+"</div><span class='TAIEXY'>昨日收盤: "+index.BUYY+" </span><span style='color:white;'>漲跌: </span><span style='color:white;'>"+index.BUYZ_Y+"</span>"
			}
		if(parseFloat(index.TBUYZ_Y)<0){
			document.getElementById('td2').innerHTML="<h3 class='subTitle'>"+index.BUYname+"</h3><div class='TAIEX' style='color:#77ff00;'>"+index.BUY+"</div><span class='TAIEXY'>昨日收盤: "+index.BUYY+" </span><span style='color:#77ff00;'>漲跌: </span><span style='color:#77ff00;'>"+index.BUYZ_Y+"</span>"
			}

		if(parseFloat(index.BUYZ_Y)>0){
			document.getElementById('td3').innerHTML="<h3 class='subTitle'>"+index.FormosaName+"</h3><div class='TAIEX' style='color:red;'>"+index.Formosa+"</div><span class='TAIEXY'>昨日收盤: "+index.FormosaY+" </span><span style='color:red;'>漲跌: </span><span style='color:red;'>"+index.FormosaZ_Y+"</span>"
			}
		if(parseFloat(index.BUYZ_Y)==0){
			document.getElementById('td3').innerHTML="<h3 class='subTitle'>"+index.FormosaName+"</h3><div class='TAIEX' style='color:white;'>"+index.Formosa+"</div><span class='TAIEXY'>昨日收盤: "+index.FormosaY+" </span><span style='color:white;'>漲跌: </span><span style='color:white;'>"+index.FormosaZ_Y+"</span>"
			}
		if(parseFloat(index.TBUYZ_Y)<0){
			document.getElementById('td3').innerHTML="<h3 class='subTitle'>"+index.FormosaName+"</h3><div class='TAIEX' style='color:#77ff00;'>"+index.Formosa+"</div><span class='TAIEXY'>昨日收盤: "+index.FormosaY+" </span><span style='color:#77ff00;'>漲跌: </span><span style='color:#77ff00;'>"+index.FormosaZ_Y+"</span>"
			}
		
		}
	});

function repeatedAjaxRequest(){
	$.ajax({
		url : "../tools/getIndexFromInternet",
		type : "GET",
		success : function(Str) {
			index = JSON.parse(Str);
			if(parseFloat(index.TAIEXZ_Y)>0){
				document.getElementById('td1').innerHTML="<h3 class='subTitle'>"+index.TAIEXname+"</h3><div class='TAIEX' style='color:red;'>"+index.TAIEX+"</div><span class='TAIEXY'>昨日收盤: "+index.TAIEXY+" </span><span style='color:red;'>漲跌: </span><span style='color:red;'>"+index.TAIEXZ_Y+"</span>"
				}
			if(parseFloat(index.TAIEXZ_Y)==0){
				document.getElementById('td1').innerHTML="<h3 class='subTitle'>"+index.TAIEXname+"</h3><div class='TAIEX' style='color:white;'>"+index.TAIEX+"</div><span class='TAIEXY'>昨日收盤: "+index.TAIEXY+" </span><span style='color:white;'>漲跌: </span><span style='color:white;'>"+index.TAIEXZ_Y+"</span>"
				}
			if(parseFloat(index.TAIEXZ_Y)<0){
				document.getElementById('td1').innerHTML="<h3 class='subTitle'>"+index.TAIEXname+"</h3><div class='TAIEX' style='color:#77ff00;'>"+index.TAIEX+"</div><span class='TAIEXY'>昨日收盤: "+index.TAIEXY+" </span><span style='color:#77ff00;'>漲跌: </span><span style='color:#77ff00;'>"+index.TAIEXZ_Y+"</span>"
				}

			if(parseFloat(index.BUYZ_Y)>0){
				document.getElementById('td2').innerHTML="<h3 class='subTitle'>"+index.BUYname+"</h3><div class='TAIEX' style='color:red;'>"+index.BUY+"</div><span class='TAIEXY'>昨日收盤: "+index.BUYY+" </span><span style='color:red;'>漲跌: </span><span style='color:red;'>"+index.BUYZ_Y+"</span>"
				}
			if(parseFloat(index.BUYZ_Y)==0){
				document.getElementById('td2').innerHTML="<h3 class='subTitle'>"+index.BUYname+"</h3><div class='TAIEX' style='color:white;'>"+index.BUY+"</div><span class='TAIEXY'>昨日收盤: "+index.BUYY+" </span><span style='color:white;'>漲跌: </span><span style='color:white;'>"+index.BUYZ_Y+"</span>"
				}
			if(parseFloat(index.TBUYZ_Y)<0){
				document.getElementById('td2').innerHTML="<h3 class='subTitle'>"+index.BUYname+"</h3><div class='TAIEX' style='color:#77ff00;'>"+index.BUY+"</div><span class='TAIEXY'>昨日收盤: "+index.BUYY+" </span><span style='color:#77ff00;'>漲跌: </span><span style='color:#77ff00;'>"+index.BUYZ_Y+"</span>"
				}

			if(parseFloat(index.BUYZ_Y)>0){
				document.getElementById('td3').innerHTML="<h3 class='subTitle'>"+index.FormosaName+"</h3><div class='TAIEX' style='color:red;'>"+index.Formosa+"</div><span class='TAIEXY'>昨日收盤: "+index.FormosaY+" </span><span style='color:red;'>漲跌: </span><span style='color:red;'>"+index.FormosaZ_Y+"</span>"
				}
			if(parseFloat(index.BUYZ_Y)==0){
				document.getElementById('td3').innerHTML="<h3 class='subTitle'>"+index.FormosaName+"</h3><div class='TAIEX' style='color:white;'>"+index.Formosa+"</div><span class='TAIEXY'>昨日收盤: "+index.FormosaY+" </span><span style='color:white;'>漲跌: </span><span style='color:white;'>"+index.FormosaZ_Y+"</span>"
				}
			if(parseFloat(index.TBUYZ_Y)<0){
				document.getElementById('td3').innerHTML="<h3 class='subTitle'>"+index.FormosaName+"</h3><div class='TAIEX' style='color:#77ff00;'>"+index.Formosa+"</div><span class='TAIEXY'>昨日收盤: "+index.FormosaY+" </span><span style='color:#77ff00;'>漲跌: </span><span style='color:#77ff00;'>"+index.FormosaZ_Y+"</span>"
				}
			
			}
		});
}

$(function(){

	 setInterval(function(){ 
	 repeatedAjaxRequest();

	 }, 5000);
	 });




</script>

</body>
</html>