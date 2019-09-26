package dedalus.security;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

public class CurrentUser extends User {
	private static final long serialVersionUID = -2345046292776946759L;
	private dedalus.domain.User user;

	public CurrentUser(dedalus.domain.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		
		this.user = user;
	}
	
	public dedalus.domain.User getUser() {
		return user;
	}
}