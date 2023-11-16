package com.example.MySecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySecurityConfiguration {
	
	// configure security properties
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		// setup authorization of each page
		.authorizeRequests() 
			.requestMatchers("/welcome/**").permitAll()
			.anyRequest().authenticated()
			.and()
		// setup login page
    	.formLogin()
    		/*.loginPage("/my_login_page")*/
    		.permitAll()
    		.and()    		    
		.httpBasic();
		
		
		return http.build();
	}
}
