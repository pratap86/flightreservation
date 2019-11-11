package com.pratap.flightreservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.pratap.flightreservation.service.SecurityService;
@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;	// AuthenticationManager of spring, authenticate the request

	@Override
	public boolean login(String username, String password) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		// get the Token from UsernamePasswordAuthenticationToken
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());
		// set the state of token
		authenticationManager.authenticate(token);
		boolean result = token.isAuthenticated();
		if(result) {
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		return result;
	}

}
