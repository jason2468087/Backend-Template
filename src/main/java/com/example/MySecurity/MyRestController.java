package com.example.MySecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
	@GetMapping(path = "/welcome")
	public String GetFrontPage() {
		
		return "Welcome";
	}
	
	@GetMapping(path = "/admin")
	public String GetAdmin() {
		
		return "Admin";
	}
	
	@GetMapping(path = "/user")
	public String GetUser() {
		
		return "User";
	}
	
	@GetMapping(path = "/common")
	public String GetCommon() {
		
		return "Common";
	}
}
