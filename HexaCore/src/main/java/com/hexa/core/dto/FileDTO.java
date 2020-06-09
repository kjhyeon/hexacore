package com.hexa.core.dto;

import java.io.Serializable;

public class FileDTO implements Serializable {

	private static final long serialVersionUID = -7062833468129671120L;
	private String name;
	private int seq;
	private String f_path;
	private String ori_name;
	private String f_size;
	private int category;

	public FileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileDTO(String name, int seq, String f_path, String ori_name, String f_size, int category) {
		super();
		this.name = name;
		this.seq = seq;
		this.f_path = f_path;
		this.ori_name = ori_name;
		this.f_size = f_size;
		this.category = category;
	}

	@Override
	public String toString() {
		return "FileDTO [name=" + name + ", seq=" + seq + ", f_path=" + f_path + ", ori_name=" + ori_name + ", f_size="
				+ f_size + ", category=" + category + "]";
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
