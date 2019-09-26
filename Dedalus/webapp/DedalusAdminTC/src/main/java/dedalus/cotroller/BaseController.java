package dedalus.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import dedalus.service.UserSessionService;

@Controller
public abstract class BaseController implements IBaseController {
	protected UserSessionService userSessionService;
	
	protected enum PageName {
		Home("home"),
		Pupils("pupils"),
		Tokens("tokens"),
		Logo("logo");
		
		private String name;
		
		private PageName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}

	@Autowired
	public BaseController(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}
	
	public UserSessionService getUserSessionService() {
		return userSessionService;
	}
	
	@Override
	public void setCurrentPageName() {
		this.userSessionService.setCurrentPageName(requestedPageName());
	}
}
