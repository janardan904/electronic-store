package com.nsdl.electronic.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.nsdl.electronic.store.model.Role;
import com.nsdl.electronic.store.repository.RoleRepository;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	@Autowired
    private RoleRepository  roleRepository;
   
  
   
     @Value("${roles.normal_role_id}")
	 private  String normalRoleId;
     
	    @Value("${roles.admin_role_id}")
		private String adminRoleId;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
		
	
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		try {
			Role adminRole=Role.builder().roleId(adminRoleId).roleName("ADMIN").build();
			Role normalRole=Role.builder().roleId(normalRoleId).roleName("NORMAL").build();
			
			
			roleRepository.save(adminRole);
			roleRepository.save(normalRole);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	 
	 

}
