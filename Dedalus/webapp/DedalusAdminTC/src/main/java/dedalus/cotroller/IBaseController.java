package dedalus.cotroller;

import org.springframework.ui.Model;

import dedalus.service.UserSessionService;

public interface IBaseController {
	public default String loadPage(Model model) {
		setCurrentPageName();
		model.addAttribute("currentUser", getUserSessionService().getUser());
		
		//model.addAttribute("locale", LocaleContextHolder.getLocale().getLanguage());
		
		return pagePath();
	}
	
	public String requestedPageName();
	
	public void setCurrentPageName();
	
	public String pagePath();
	
	public UserSessionService getUserSessionService();
}