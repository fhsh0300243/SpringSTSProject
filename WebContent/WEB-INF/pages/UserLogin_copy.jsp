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
		<td>
			<h3 class="subTitle">${TAIEX.TAIEXname}</h3>
			<div class="TAIEX" id="TAIEX">${TAIEX.TAIEX}</div>
			<span class="TAIEXY">昨日收盤: ${TAIEX.TAIEXY} </span><span id="TAIEXUpandDown">漲跌: </span><span id="TAIEXZ_Y">${TAIEX.TAIEXZ_Y}</span>
		</td>
		<td>
			<h3 class="subTitle">${TAIEX.BUYname}</h3>
			<div class="TAIEX" id="BUY">${TAIEX.BUY}</div>
			<span class="TAIEXY">昨日收盤: ${TAIEX.BUYY} </span><span id="BUYUpandDown">漲跌: </span><span id="BUYZ_Y">${TAIEX.BUYZ_Y}</span>
		</td>
		<td>
			<h3 class="subTitle">${TAIEX.FormosaName}</h3>
			<div class="TAIEX" id="Formosa">${TAIEX.Formosa}</div>
			<span class="TAIEXY">昨日收盤: ${TAIEX.FormosaY} </span><span id="FormosaUpandDown">漲跌: </span><span id="FormosaZ_Y">${TAIEX.FormosaZ_Y}</span>
		</td>
	</tr>
</table>
<script>
	var taiexz_y = document.getElementById('TAIEXZ_Y').innerHTML;
	var buyz_y = document.getElementById('BUYZ_Y').innerHTML;
	var formosaz_y = document.getElementById('FormosaZ_Y').innerHTML;
	if(parseFloat(taiexz_y)>0){
		document.getElementById('TAIEXZ_Y').style.color = 'red';
		document.getElementById('TAIEX').style.color = 'red';
		document.getElementById('TAIEXUpandDown').style.color = 'red';
	}
	if(parseFloat(taiexz_y)==0){
		document.getElementById('TAIEXZ_Y').style.color = 'white';
		document.getElementById('TAIEX').style.color = 'white';
		document.getElementById('TAIEXUpandDown').style.color = 'white';
	}
	if(parseFloat(taiexz_y)<0){
		document.getElementById('TAIEXZ_Y').style.color = '#77ff00';
		document.getElementById('TAIEX').style.color = '#77ff00';
		document.getElementById('TAIEXUpandDown').style.color = '#77ff00';
	}
	if(parseFloat(buyz_y)>0){
		document.getElementById('BUYZ_Y').style.color = 'red';
		document.getElementById('BUY').style.color = 'red';
		document.getElementById('BUYUpandDown').style.color = 'red';
	}
	if(parseFloat(buyz_y)==0){
		document.getElementById('BUYZ_Y').style.color = 'white';
		document.getElementById('BUY').style.color = 'white';
		document.getElementById('BUYUpandDown').style.color = 'white';
	}
	if(parseFloat(buyz_y)<0){
		document.getElementById('BUYZ_Y').style.color = '#77ff00';
		document.getElementById('BUY').style.color = '#77ff00';
		document.getElementById('BUYUpandDown').style.color = '#77ff00';
	}
	if(parseFloat(buyz_y)>0){
		document.getElementById('FormosaZ_Y').style.color = 'red';
		document.getElementById('Formosa').style.color = 'red';
		document.getElementById('FormosaUpandDown').style.color = 'red';
	}
	if(parseFloat(buyz_y)==0){
		document.getElementById('FormosaZ_Y').style.color = 'white';
		document.getElementById('Formosa').style.color = 'white';
		document.getElementById('FormosaUpandDown').style.color = 'white';
	}
	if(parseFloat(buyz_y)<0){
		document.getElementById('FormosaZ_Y').style.color = '#77ff00';
		document.getElementById('Formosa').style.color = '#77ff00';
		document.getElementById('FormosaUpandDown').style.color = '#77ff00';
	}
	


</script>

</body>
</html>