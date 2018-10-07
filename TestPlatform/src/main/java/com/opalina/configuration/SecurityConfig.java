package com.opalina.configuration;

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
*/

//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/{
	/*
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/v1/admin/**").hasRole("admin")
				.antMatchers("/v1/examiner/**").hasRole("examiner")
				.antMatchers("/v1/examinee/**").hasRole("examinee")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/v1/login")
				.usernameParameter("email")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/v1/login?logout")
				.permitAll();
	}
	*/
}
