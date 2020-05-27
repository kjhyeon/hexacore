package com.hexa.core.dto;

import java.io.Serializable;

public class DepartmentDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7747644485169517834L;
	private int department_id;
	private String name      ;
	private int upper_id     ;
	private String faxnum    ;
	private String d_phone   ;
	private String state;
	public DepartmentDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUpper_id() {
		return upper_id;
	}
	public void setUpper_id(int upper_id) {
		this.upper_id = upper_id;
	}
	public String getFaxnum() {
		return faxnum;
	}
	public void setFaxnum(String faxnum) {
		this.faxnum = faxnum;
	}
	public String getD_phone() {
		return d_phone;
	}
	public void setD_phone(String d_phone) {
		this.d_phone = d_phone;
	}

	@Override
	public String toString() {
		return "DepartmentDTO [department_id=" + department_id + ", name=" + name + ", upper_id=" + upper_id
				+ ", faxnum=" + faxnum + ", d_phone=" + d_phone + "]";
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
