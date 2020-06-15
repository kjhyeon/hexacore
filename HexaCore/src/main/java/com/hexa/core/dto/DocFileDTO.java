package com.hexa.core.dto;

import java.io.Serializable;

public class DocFileDTO implements Serializable {

	private static final long serialVersionUID = -8727835072387832975L;
	private String name;
	private int seq;
	private String f_path;
	private String ori_name;
	private String f_size;
	
	public DocFileDTO() {
	}
	
	public DocFileDTO(String name, int seq, String f_path, String ori_name, String f_size) {
		super();
		this.name = name;
		this.seq = seq;
		this.f_path = f_path;
		this.ori_name = ori_name;
		this.f_size = f_size;
	}
	
	@Override
	public String toString() {
		return "DocFileDTO [name=" + name + ", seq=" + seq + ", f_path=" + f_path + ", ori_name=" + ori_name
				+ ", f_size=" + f_size + "]";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getF_path() {
		return f_path;
	}
	public void setF_path(String f_path) {
		this.f_path = f_path;
	}
	public String getOri_name() {
		return ori_name;
	}
	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}
	public String getF_size() {
		return f_size;
	}
	public void setF_size(String f_size) {
		this.f_size = f_size;
	}

}
