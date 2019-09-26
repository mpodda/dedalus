package dedalus.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dedalus.domain.User;
import dedalus.service.api.UserService;

@Component
public class InitialSecurityLoader implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(InitialSecurityLoader.class);
	
	private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
	public InitialSecurityLoader(UserService userService) {
		this.userService = userService;
	}



	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (this.userService.countUsers() > 0) {
			return;
		}
		
		// Create initial user
		User user = new User();
		user.setUsername("admin");
		user.setPassword(passwordEncoder().encode("admin123"));
		user.setFullName("Dedalus Administrator");
		
		userService.save(user);
		
		logger.info("Added initial users to the database.");
	}

}
