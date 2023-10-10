package com.nsdl.electronic.store.config;

import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nsdl.electronic.store.security.JwtAuthenticationFilter;
import com.nsdl.electronic.store.security.JwtAuthonticationEntryPoint;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtAuthonticationEntryPoint authenticationEntryPoint;
	
	private final String[] PUBLIC_URL = {
			
			"/swagger-ui.html", 
			"/swagger-ui/**", 
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**"
	};
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
		
	}
	
	/*
	 * @Bean public UserDetailsService UserDetailService() {
	 * 
	 * UserDetails normal=User.builder() .username("Ankit")
	 * .password(passwordEncoder().encode("ankit")) .roles("NORMAL") .build();
	 * 
	 * UserDetails admin=User.builder() .username("janardan")
	 * .password(passwordEncoder().encode("jan@123")) .roles("ADMIN") .build();
	 * return new InMemoryUserDetailsManager(normal,admin);
	 * 
	 * }
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/auth/login")
		.permitAll()
		.antMatchers(HttpMethod.POST,"/users")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/users/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/users/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST,"/products").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT,"/products/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/products/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST,"/products/image/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST,"/categories").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT,"/categories/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST,"/categories/coverImage/**").hasAuthority("ADMIN")
		.antMatchers(PUBLIC_URL)
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
		return http.build();
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration=new CorsConfiguration();
		configuration.setAllowCredentials(true);
		//configuration.setAllowedHeaders(Arrays.asList("http://domain2.com","http://localhost:4200"));
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Content-Type");
		configuration.addAllowedHeader("Accept");
		configuration.setAllowedMethods(Arrays.asList("POST","PUT","DELETE","GET","OPTION"));
		configuration.setMaxAge(3000L);
        source.registerCorsConfiguration("/**",configuration);
		FilterRegistrationBean filterRegisterationBaen=new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
		filterRegisterationBaen.setOrder(-100);
		return filterRegisterationBaen;
		
	}
}
