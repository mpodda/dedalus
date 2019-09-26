package dedalus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dedalus.security.CurrentUser;

@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionService {
	private CurrentUser user;
	
	private String currentPageName; 
	
	@Autowired
	public UserSessionService() {
		this.user = (CurrentUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
	public CurrentUser getUser() {
		return user;
	}

	public String getCurrentPageName() {
		return currentPageName;
	}

	public void setCurrentPageName(String currentPageName) {
		this.currentPageName = currentPageName;
	}
}
