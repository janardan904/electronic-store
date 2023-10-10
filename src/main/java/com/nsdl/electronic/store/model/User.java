package com.nsdl.electronic.store.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Builder
@Getter
public class User implements UserDetails {
	
	@Id
	@Column(name="user_id")
	private String userId;
	 @Column(name="user_name" ,length=30) 
	private String name;
	 @Column(name="user_email",length = 30)
	private String email;
	@Column(name="user_pwd",length =60)
	private String password;
	private String gender;
	private String about;
	private String image;
	
	@OneToMany(mappedBy= "user", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Order> orders = new ArrayList<>();
	
	@ManyToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(mappedBy= "user",cascade = CascadeType.REMOVE,fetch=FetchType.EAGER)
	private Cart cart;
	
	@Override
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities=roles.stream().map(role-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		return authorities;
	}
	@Override
	public String getUsername() {
		
		return this.email;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
