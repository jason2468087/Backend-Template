package com.example.MySecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pwd_storage")
public class MyUser {
	@Id
	private Integer id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String pwd;
	@Column(nullable = false)
	private String auth;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		username = name;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pw) {
		pwd = pw;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String a) {
		auth = a;
	}
}
