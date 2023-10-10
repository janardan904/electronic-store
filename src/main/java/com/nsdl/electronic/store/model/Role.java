package com.nsdl.electronic.store.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Builder
@Getter
@Entity
@Table(name="roles")
public class Role {

	
	
	 @Id
      @Column(name="role_id")
	  private String roleId;
	  private String roleName;
	 
	  
	
}
