package tw.STSProject.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tw.STSProject.model.Users;

@Controller
@RequestMapping(path ="/logout")
@SessionAttributes(names = {"userID", "userMoney", "errorMsgMap", "LoginOK", "TAIEX"})
public class logoutController {
	
	@RequestMapping(path ="/toLoginPage", method = RequestMethod.GET)
	public String toLoginPage(@ModelAttribute("userID") int userID, @ModelAttribute("userMoney") int userMoney,
			@ModelAttribute("errorMsgMap") Map<String, String> errorMsgMap, 
			@ModelAttribute("LoginOK") Users LoginOK, 
			@ModelAttribute("TAIEX") String TAIEX, SessionStatus status) {
		
		status.setComplete();
		return "redirect:/login/userLogin";
	}
}
