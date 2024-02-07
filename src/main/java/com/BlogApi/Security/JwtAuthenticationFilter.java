package com.BlogApi.Security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper; 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// get token
		
		String requestToken=request.getHeader("Authorization");
		
		
		//bearer 2250ddfghh
		
		logger.info(" Header :  {}", requestToken);
		
		System.out.println("Token " + requestToken);
		
		
		String username =null;
		String token =null;
		
		
		if(requestToken !=null && requestToken.startsWith("Bearer ")) {
			token=requestToken.substring(7);
			
			try {
				username=this.jwtTokenHelper.getUsernameFromToken(token);
			}catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }
			
		}else {
            logger.info("Invalid Header Value !! ");
        }
		
		
		// once we get the token , now validate 
		
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDatails=this.userDetailsService.loadUserByUsername(username);
			
			 Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDatails);
			
			if(validateToken) {
				// sahi chal raha hai 
				// authentication krna hai
				
				
				//set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDatails, null, userDatails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug("Setting authentication for user: {}", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                
				
			}else {
                logger.info("Validation fails !!");
            }
		}else {
			System.out.println("username is null or context is not null ");
		}
		
		logger.debug("Before doFilter");
		filterChain.doFilter(request, response);
		logger.debug("After doFilter");

	}
	
	

}
