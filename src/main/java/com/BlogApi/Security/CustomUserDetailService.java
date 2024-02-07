package com.BlogApi.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BlogApi.entities.User;
import com.BlogApi.exeption.ResourceNotFoundException;
import com.BlogApi.repository.Userrepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private Userrepo userrepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		System.out.println(username + "custom class");
     User user=this.userrepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email :"+username, 0));	
    		 return user;
	}

}
