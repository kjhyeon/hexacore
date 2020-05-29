package com.hexa.core.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginDTO extends User implements Serializable{
	
	public LoginDTO(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String name, String email, String e_rank,String department_name,String profile_img, String e_rank_name) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.name = name;
		this.email = email;
		this.e_rank = e_rank;
		this.e_rank_name = e_rank_name;
		this.department_name = department_name;
		this.profile_img = profile_img;
		
	}
	private String name         ;
	private String email          ;
	private String e_rank         ;
	private String department_id;
	private String profile_img    ;
	private String department_name;
	private String e_rank_name    ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String getE_rank() {
		return e_rank;
	}
	public void setE_rank(String e_rank) {
		this.e_rank = e_rank;
	}
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getE_rank_name() {
		return e_rank_name;
	}
	public void setE_rank_name(String e_rank_name) {
		this.e_rank_name = e_rank_name;
	}
	@Override
	public String toString() {
		return "LoginDTO [name=" + name + ", email=" + email + ", e_rank=" + e_rank + ", department_id=" + department_id
				+ ", profile_img=" + profile_img + ", department_name=" + department_name + ", e_rank_name="
				+ e_rank_name + "]";
	}
}
