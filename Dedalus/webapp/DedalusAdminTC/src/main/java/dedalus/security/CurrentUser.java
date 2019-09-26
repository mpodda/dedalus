package dedalus.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import dedalus.domain.TuitionCentre;

public class CurrentUser extends User {
	private static final long serialVersionUID = 4993480812951403057L;
	
	private TuitionCentre tuitionCentre;
	
	public CurrentUser(TuitionCentre tuitionCentre, Collection<? extends GrantedAuthority> authorities) {
		super(tuitionCentre.getUserName(), tuitionCentre.getPassword(), authorities);
		this.tuitionCentre = tuitionCentre;
	}
	
	public TuitionCentre getTuitionCentre() {
		return tuitionCentre;
	}
	
	public String getUserName() {
		if (tuitionCentre == null) {
			return "";
		}
		
		return tuitionCentre.getUserName();
	}
	
	public String getUserFullName() {
		if (tuitionCentre == null) {
			return "";
		}
		
		return tuitionCentre.getDescription();
	}
}