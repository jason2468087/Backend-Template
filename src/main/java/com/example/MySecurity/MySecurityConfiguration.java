package com.example.MySecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySecurityConfiguration {
	
	// configure security properties
	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
		UserBuilder userBuild = User.withDefaultPasswordEncoder();
        UserDetails user = userBuild
        	.username("user")
            .password("userPassword")
            .build();
        UserDetails admin = userBuild
        	.username("admin")
            .password("adminPassword")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
	
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
    	// setup logout page
    	.logout()
    		/*.logoutUrl("/my_logout_page")*/
    		.and()
		.httpBasic();
		
		
		return http.build();
	}
}
