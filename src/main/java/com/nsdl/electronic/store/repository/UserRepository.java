package com.nsdl.electronic.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.electronic.store.model.User;


public interface UserRepository  extends JpaRepository<User,String>{

	public Optional<User> findByEmail(String email);
	public Optional<User> findByEmailAndPassword(String email,String password);
    public List<User> findByNameContaining(String keyword);
   
    
}
