package com.hexa.core.ctrl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.model.mng.inf.EmployeeIService;


public class SecurityCtrl implements UserDetailsService{

		@Autowired
		private EmployeeIService service;

		Logger log = LoggerFactory.getLogger(this.getClass());
		
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("$$$$$$$$$$$$$$$$$$$$ {} $$$$$$$$$$$$$$$",username);
		EmployeeDTO dto = service.selectLoginInfo(username);
		System.out.println(dto);
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		String auth = null;
		if(dto.getAuth().trim().equalsIgnoreCase("a"))
			auth = "ROLE_ADMIN";
		else
			auth = "ROLE_USER";
		System.out.println(auth);
		roles.add(new SimpleGrantedAuthority(auth));

		LoginDTO user = new LoginDTO(username, dto.getPassword(), true, true, true, true, roles, dto.getName(), dto.getEmail(), String.valueOf(dto.getE_rank())
								, dto.getDepartment_name(), dto.getProfile_img(), dto.getE_rank_name());
//		UserDetails user = new User(username,dto.getPw(),roles);

		return user;
		}


}
