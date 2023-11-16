package com.example.MySecurity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository <MyUser, Integer>{
	Optional<MyUser> findByUsername(String username);
}
