package com.nsdl.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.Role;

public interface RoleRepository extends JpaRepository<Role,String> {

}
