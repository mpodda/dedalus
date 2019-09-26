package dedalus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dedalus.domain.User;
import dedalus.security.CurrentUser;
import dedalus.service.api.UserService;


@Service
public class DedalusAdminConsulterUserDetailsService implements UserDetailsService {
	private UserService userService;

	@Autowired
	public DedalusAdminConsulterUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = this.userService.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
        
        return new CurrentUser(user, authorities);
	}

}
