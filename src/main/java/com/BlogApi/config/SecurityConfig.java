package com.BlogApi.config;
import org.springframework.web.filter.CorsFilter; // Ensure correct import
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.BlogApi.Security.CustomUserDetailService;
import com.BlogApi.Security.JwtAuthenticationEntryPoint;
import com.BlogApi.Security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {
	
	
	 public static final String[] PUBLIC_URLS= {
			   "/api/v1/auth/**",
			   "/v3/api-docs","/v2/api-docs","/swagger-resources/**","/swagger-ui/**","/webjars/**"
	   };

	@Autowired
	private UserDetailsService userDetailservice;

	@Autowired
	private JwtAuthenticationEntryPoint Point;

	@Autowired
	private JwtAuthenticationFilter Filter;
	
  
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf(csrf->csrf.disable()).cors(cors->cors.disable()).authorizeHttpRequests(auth->auth.requestMatchers("/test")
				.authenticated()
				.requestMatchers(PUBLIC_URLS).permitAll().requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())
		.exceptionHandling(ex->ex.authenticationEntryPoint(Point)).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.addFilterBefore(this.Filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

		

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setUserDetailsService(userDetailservice);
		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
//	@Bean
//	public FilterRegistrationBean coresFilter() {
//		
//		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
//		
//		CorsConfiguration corsConfiguration=new CorsConfiguration();
//		
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.addAllowedOriginPattern("*");
//		corsConfiguration.addAllowedHeader("Authorization");
//		corsConfiguration.addAllowedHeader("content-type");
//		corsConfiguration.addAllowedHeader("Accept");
//		corsConfiguration.addAllowedMethod("post");
//		corsConfiguration.addAllowedMethod("GET");
//		corsConfiguration.addAllowedMethod("DELETE");
//		corsConfiguration.addAllowedMethod("PUT");
//		corsConfiguration.addAllowedMethod("OPTIONS");
//		corsConfiguration.setMaxAge(3600L);
//		
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		
//		FilterRegistrationBean bean =new FilterRegistrationBean(new CorsFilter(source));
//		return bean;
//	}
	
	@Bean

	public FilterRegistrationBean<CorsFilter> corsFilter()
	 
	{

	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowCredentials(true);
	    configuration.addAllowedOriginPattern("*"); // Adjust for specific origins in production
	    configuration.addAllowedHeader("Authorization");
	    configuration.addAllowedHeader("content-type");
	    configuration.addAllowedHeader("Accept");
	    configuration.addAllowedMethod("POST");
	    configuration.addAllowedMethod("GET");
	    configuration.addAllowedMethod("DELETE");
	    configuration.addAllowedMethod("PUT");
	    configuration.addAllowedMethod("OPTIONS");
	    configuration.setMaxAge(3600L);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
   

	    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//	    return new CorsFilter(source);
	     bean.setOrder(-110);
	    return bean;
	}
}
