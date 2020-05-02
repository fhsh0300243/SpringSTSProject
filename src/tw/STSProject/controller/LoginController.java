package tw.STSProject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import tw.STSProject.model.Users;
import tw.STSProject.model.UsersService;
import tw.STSProject.util.AESUtil;
import tw.STSProject.util.Captcha;
import tw.STSProject.util.EmailUtil;
import tw.STSProject.util.GenerateLinkUtil;
import tw.STSProject.util.GifCaptcha;
import tw.STSProject.util.STSNecessaryTools;


@Controller
@SessionAttributes(names = {"userID", "userMoney", "LoginOK", "userName", "gifCode"})
@RequestMapping(path ="/login")
public class LoginController {
	private UsersService uService;
	private STSNecessaryTools stsnt=new STSNecessaryTools();
	private AESUtil aes=new AESUtil();
	
	@Autowired
	public LoginController(UsersService uService){
		this.uService = uService;
		
	}
	
	@RequestMapping(path = "/userLogin", method = RequestMethod.GET)
	public String goToLoginPage(Model model) throws IOException, Exception {
		String TAIEX=stsnt.getTAIEXFromInternet();
		model.addAttribute("TAIEX",TAIEX);
		return "UserLogin";
	}
	
	@RequestMapping(path = "/userLoginCheck", method = RequestMethod.POST)
	public String processLoginAction(@RequestParam(value="userName", required=false) String userAccount,
			@RequestParam(value="userPassword", required=false) String userPwd,
			 Model model) throws IOException, Exception {
		String TAIEX=stsnt.getTAIEXFromInternet();
		Map<String, String> errorMsgMap = new HashMap<String, String>();
		model.addAttribute("errorMsgMap", errorMsgMap);
		model.addAttribute("TAIEX",TAIEX);
		
		if((userAccount==null || userAccount.trim().length() == 0) && (userPwd==null || userPwd.trim().length() == 0)) {
			errorMsgMap.put("NoLoginError", "Please Login again");
			return "UserLogin";
		}
		
		if (userAccount == null || userAccount.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "User account should not be empty");
		}
		
		if (userPwd == null || userPwd.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "User password should not be empty");
		}

		if (!errorMsgMap.isEmpty()) {
			return "UserLogin";
		}
		
		String encryptPwd=aes.parseByte2HexStr(aes.encrypt(userPwd));
		List<Users> loginResult=uService.findUsers(userAccount, encryptPwd);
		
		if(loginResult.size()>0) {
			Users uTemp = loginResult.get(0);
			if(uTemp.isActivated()) {
				model.addAttribute("userID", uTemp.getUserID());
				model.addAttribute("userMoney", uTemp.getTotalMoney());
				model.addAttribute("LoginOK", uTemp);
				return "redirect:/main/toMainTable";
			}
			Map<String, String> MsgFromActivateAccount = new HashMap<String, String>();
			MsgFromActivateAccount.put("notActivated","請先啟用帳號再登入");
			model.addAttribute("MsgFromActivateAccount",MsgFromActivateAccount);
			return "UserLogin";
		}
		else {
			errorMsgMap.put("LoginError", "Account doesn't exit or password wrong");
			return "UserLogin";
		}
	} 
	
	@RequestMapping(path = "/userRegister", method = RequestMethod.POST)
	public String goToRegisterPage(){
		return "Register";
	}
	
	@RequestMapping(path = "/userRegisterCheck", method = RequestMethod.POST)
	public String processRegisterAction(@RequestParam("usrname") String userAccount, @RequestParam("psw") String userPwd
			, @RequestParam("email") String email, @RequestParam(value="captcha") String captcha, 
			@ModelAttribute("gifCode") String gifCode, Model model) throws IOException, Exception {
		Map<String, String> msgMapFromRegister = new HashMap<String, String>();
		model.addAttribute("msgMapFromRegister", msgMapFromRegister);
		
		System.out.println("gifCode: "+gifCode);
		System.out.println("captcha: "+captcha);
		
		if(!gifCode.equals(captcha)) {
			msgMapFromRegister.put("captchaError", "驗證碼錯誤");
			return "Register";
		}
		
		String encryptPwd=aes.parseByte2HexStr(aes.encrypt(userPwd));
		boolean userRegister=uService.insertUsers(userAccount, encryptPwd, 100000, email);
		
		if(userRegister) {
			EmailUtil eu=new EmailUtil();
			List<Users> uList=uService.findUsers(userAccount, encryptPwd);
			Users uBean=uList.get(0);
			eu.sendAccountActivateEmail(uBean);
			msgMapFromRegister.put("registerOK", "註冊成功 請先前往E-mail信箱收啟用信");
			String TAIEX=stsnt.getTAIEXFromInternet();
			model.addAttribute("TAIEX",TAIEX);
			return "UserLogin";
		}
		else {
			msgMapFromRegister.put("registerError", "註冊失敗 請重新註冊");
			return "Register";
		}
	}
	
	@RequestMapping(path = "/forgetPwd", method = RequestMethod.GET)
	public String goToSendEmailPage() {
		return "CheckEmail";
	}
	
	@RequestMapping(path = "/sendMail", method = {RequestMethod.POST, RequestMethod.GET})
	public String ProcessForgotPwd(@RequestParam("email") String email, Model model) {
		Map<String, String> errorMsgFromForgetPwd = new HashMap<String, String>();
		model.addAttribute("errorMsgFromForgetPwd", errorMsgFromForgetPwd);
		
		if (email == null || email.trim().length() == 0) {
			errorMsgFromForgetPwd.put("emailEmptyError", "E-mail should not be empty");
			return "CheckEmail";
		}
		
		List<Users> usersByEmail=uService.findUsersByEmail(email);
		
		if(usersByEmail.size() <= 0) {						
			errorMsgFromForgetPwd.put("emailNotFound", "E-mail沒被註冊過");
			return "CheckEmail";
		}
		
		Iterator<Users> usersByEmailIT=usersByEmail.iterator();
		Users uBean=usersByEmailIT.next();
		EmailUtil eUtil=new EmailUtil();
		eUtil.sendResetPasswordEmail(uBean, email);
		errorMsgFromForgetPwd.put("emailSucess", "提交成功，請查看你的E-mail信箱");
		return "CheckEmail";
	}
	
	@RequestMapping(path = "/resetPassword", method = RequestMethod.GET)
	public String goToResetPwdPage(@RequestParam("userName") String userName, Model model) {
		model.addAttribute("userName",userName);
		return "resetPassword";
	}
	
	@RequestMapping(path = "/passwordChange", method = RequestMethod.POST)
	public String resetPwd(@ModelAttribute("userName") String userName, @RequestParam("psw") String psw, Model model) {
		String encryptPwd=aes.parseByte2HexStr(aes.encrypt(psw));
		Boolean status=uService.updateUsersPassword(userName, encryptPwd);
		Map<String, String> MsgFromPwdReset = new HashMap<String, String>();
		model.addAttribute("MsgFromPwdReset", MsgFromPwdReset);
		if(status) {
			MsgFromPwdReset.put("resetSuccess", "密碼重設成功，請重新登入");
			return "UserLogin";
		}
		MsgFromPwdReset.put("resetFailed", "密碼重設失敗，請再試一次");
		return "resetPassword";
	}
	
	@RequestMapping(path = "/activateAccount", method = RequestMethod.GET)
	public String activateAcc(@RequestParam("id") String idValue, @RequestParam("checkCode") String checkCode, Model model) throws Exception {
		Map<String, String> MsgFromActivateAccount = new HashMap<String, String>();
		model.addAttribute("MsgFromActivateAccount",MsgFromActivateAccount);
		int id = -1;
		try {
			id = Integer.parseInt(idValue);
		} catch (NumberFormatException e) {
			MsgFromActivateAccount.put("invalidAccount", "無效的用戶!");
			return "UserLogin";
		}
		List<Users> usersList=uService.findUsersByUserID(id);
		
		GenerateLinkUtil glu=new GenerateLinkUtil();
		uService.updateUserActivated(id, glu.verifyCheckcode(usersList.get(0), checkCode));
		MsgFromActivateAccount.put("ActivateSucess", "啟用成功 請重新登入");
		String TAIEX=stsnt.getTAIEXFromInternet();
		model.addAttribute("TAIEX",TAIEX);
		return "UserLogin";
	}
	
	@RequestMapping(value="/getGifCode",method=RequestMethod.GET)
	public void getGifCode(HttpServletResponse response,HttpServletRequest request, Model model){
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/gif");  
	        /**
	         * gif格式动画验证码
	         * 宽，高，位数。
	         */
	        Captcha captcha = new GifCaptcha(146,33,4);
	        //输出
	        captcha.out(response.getOutputStream());
	        //存入Session
	        model.addAttribute("gifCode",captcha.text().toLowerCase());  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
		
		
	



