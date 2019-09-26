package dedalus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dedalus.domain.TuitionCentre;
import dedalus.security.CurrentUser;
import dedalus.service.api.TuitionCentreService;

@Service
public class DedalusAdminTCUserDetailsService implements UserDetailsService {
	private TuitionCentreService tuitionCentreService;
	
	@Autowired
	public DedalusAdminTCUserDetailsService(TuitionCentreService tuitionCentreService) {
		this.tuitionCentreService = tuitionCentreService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		TuitionCentre tuitionCentre = this.tuitionCentreService.byUserName(userName);
		
		if (tuitionCentre == null) {
			throw new UsernameNotFoundException(userName);
		}
		
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
		
		return new CurrentUser(tuitionCentre, authorities);
	}

}
