package com.BlogApi;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.BlogApi.config.AppConstant;
import com.BlogApi.entities.Role;
import com.BlogApi.repository.RoleRepo;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo Rolerepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}
    
	@Bean
	public ModelMapper modelmapper() {
		
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println(passwordEncoder.encode("abc"));		
		
		try {
			Role role=new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles=List.of(role,role1);
			
			List<Role> result=this.Rolerepo.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		}catch(Exception e) {
			
		}
	}
}
