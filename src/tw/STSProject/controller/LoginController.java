package tw.STSProject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import tw.STSProject.model.Users;
import tw.STSProject.model.UsersService;
import tw.STSProject.util.STSNecessaryTools;


@Controller
@SessionAttributes(names = {"userID", "userMoney", "errorMsgMap", "LoginOK", "TAIEX", "msgMapFromRegister"})
@RequestMapping(path ="/login")
public class LoginController {
	private UsersService uService;
	private STSNecessaryTools stsnt=new STSNecessaryTools();
	
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
	public String processLoginAction(@RequestParam("userName") String userAccount,
			@RequestParam("userPassword") String userPwd, Model model) throws IOException, Exception {
		String TAIEX=stsnt.getTAIEXFromInternet();
		Map<String, String> errorMsgMap = new HashMap<String, String>();
		model.addAttribute("errorMsgMap", errorMsgMap);
		model.addAttribute("TAIEX",TAIEX);
		
		if (userAccount == null || userAccount.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "User account should not be empty");
		}
		
		if (userPwd == null || userPwd.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "User password should not be empty");
		}

		if (!errorMsgMap.isEmpty()) {
			return "UserLogin";
		}
		
		List<Users> loginResult=uService.findUsers(userAccount, userPwd);
		
		if(loginResult.size()>0) {
			Iterator<Users> loginResultIT = loginResult.iterator();
			Users uTemp = loginResultIT.next();
			model.addAttribute("userID", uTemp.getUserID());
			model.addAttribute("userMoney", uTemp.getTotalMoney());
			model.addAttribute("LoginOK", uTemp);
			return "redirect:/main/toMainTable";
			
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
	public String processRegisterAction(@RequestParam("usrname") String userAccount, @RequestParam("psw") String userPwd, Model model) throws IOException, Exception {
		Map<String, String> msgMapFromRegister = new HashMap<String, String>();
		boolean userRegister=uService.insertUsers(userAccount, userPwd, 100000);
		
		if(userRegister) {
			msgMapFromRegister.put("registerOK", "註冊成功 請重新登入");
			String TAIEX=stsnt.getTAIEXFromInternet();
			model.addAttribute("msgMapFromRegister", msgMapFromRegister);
			model.addAttribute("TAIEX",TAIEX);
			return "UserLogin";
		}
		else {
			msgMapFromRegister.put("registerError", "註冊失敗 請重新註冊");
			model.addAttribute("msgMapFromRegister", msgMapFromRegister);
			return "Register";
		}
	}
	
}
		
		
	



