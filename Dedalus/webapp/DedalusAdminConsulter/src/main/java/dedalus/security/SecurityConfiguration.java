package dedalus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import dedalus.service.DedalusAdminConsulterUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private DedalusAdminConsulterUserDetailsService userDetailsService;
	
	
	@Autowired
	public SecurityConfiguration(DedalusAdminConsulterUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		
		//For JUnit Tests
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
        .passwordEncoder(encoder)
        .withUser("springTester")
        .password(encoder.encode("secret"))
        .roles("USER");		
	}	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        //.and()
        .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/layouts/**").permitAll()
                .antMatchers("/layout-templates/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/", "/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html").permitAll()
        		.usernameParameter("username")
        		.passwordParameter("password")
                .failureUrl("/login-error.html").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // TODO they suggest POST logout instead (which is the default)
                .logoutSuccessUrl("/login.html")
                ;		
	}
}
