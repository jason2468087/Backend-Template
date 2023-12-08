package com.example.MySecurity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class MySecurityConfiguration {
	private String loginMode = "OAuth2";
	   
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
		if (loginMode == "Form")
		{
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
		}
		else if (loginMode == "OAuth2")
		{
			http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.oauth2Login()
				.and()
			.logout();
		}
		
		
		return http.build();
	}
}
