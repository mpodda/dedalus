package dedalus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.service.UserSessionService;

@Controller
public class HomeController {
	private UserSessionService userSessionService;
	
	
	@Autowired
	public HomeController(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("locale", LocaleContextHolder.getLocale().getLanguage());
		return "/app/home";
	}
	
	// Login form
    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/data/currentUserName")
    public @ResponseBody String currentUserName() {
    	return this.userSessionService.getUser().getUser().getFullName();
    }
    
    @RequestMapping("/data/currentLocale")
    public @ResponseBody String currentLocale() {
    	return LocaleContextHolder.getLocale().getLanguage();
    }
    
}
