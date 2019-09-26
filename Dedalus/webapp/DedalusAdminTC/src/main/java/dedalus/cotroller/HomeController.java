package dedalus.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dedalus.domain.structures.AvailableToken;
import dedalus.service.TokenAvailabilityService;
import dedalus.service.UserSessionService;

@Controller
public class HomeController extends BaseController {
	private TokenAvailabilityService tokenAvailabilityService; 

	@Autowired
	public HomeController(UserSessionService userSessionService,
			TokenAvailabilityService tokenAvailabilityService) {
		super(userSessionService);
		
		this.tokenAvailabilityService = tokenAvailabilityService;
	}

	@RequestMapping("/")
	@Override
	public String loadPage(Model model) {
		return super.loadPage(model);
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
    
    
    @RequestMapping("/data/currentUser")
    public  @ResponseBody String currentUser() {
    	return userSessionService.getUser().getUsername();
    }

    @RequestMapping("/data/currentPage")
    public  @ResponseBody String currentPage() {
    	return userSessionService.getCurrentPageName();
    }
    
    @RequestMapping("/data/availableTokens")
    public  @ResponseBody AvailableToken getAvailableTokens() {
    	return this.tokenAvailabilityService.getAvailableTokens();
    }
    
    @RequestMapping("/data/notStartedTokens")
    public  @ResponseBody AvailableToken getNotStartedTokens() {
    	return this.tokenAvailabilityService.getNotStartedTokens();
    }
    
    @RequestMapping("/data/onGoingTokens")
    public  @ResponseBody AvailableToken getOnGoingTokens() {
    	return this.tokenAvailabilityService.getOnGoingTokens();
    }
    
    @RequestMapping("/data/completedTokens")
    public  @ResponseBody AvailableToken getCompletedTokens() {
    	return this.tokenAvailabilityService.getCompletedTokens();
    }
    
    
    
	@Override
	public String requestedPageName() {
		return PageName.Home.getName();
	}

	@Override
	public String pagePath() {
		return "/app/home";
	}

}