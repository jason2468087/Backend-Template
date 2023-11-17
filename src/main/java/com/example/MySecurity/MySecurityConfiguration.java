package com.example.MySecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySecurityConfiguration {

	@Autowired
	private final MyUserRepository userRepository;
	
	@Autowired
	public MySecurityConfiguration(MyUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(new MyUserDetailsService(userRepository));
		authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		
		return authProvider;
	}
	
	// configure security properties
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		// setup authorization of each page
		.authorizeRequests() 
			.requestMatchers("/welcome/**").permitAll()
			.requestMatchers("/user/**").hasAuthority("USER")
			.requestMatchers("/admin/**").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
		// setup login page
    	.formLogin()
    		/*.loginPage("/my_login_page")*/
    		.permitAll()
    		.and()    		    
    	// setup logout page
    	.logout()
    		/*.logoutUrl("/my_logout_page")*/
    		.and()
    	.authenticationProvider(authenticationProvider())
		.httpBasic();
		
		
		return http.build();
	}
}
