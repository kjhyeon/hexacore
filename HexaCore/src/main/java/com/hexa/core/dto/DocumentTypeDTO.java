package com.hexa.core.dto;

import java.io.Serializable;

public class DocumentTypeDTO implements Serializable{
	
	private static final long serialVersionUID = 8787103545048636634L;
	
	private int type_seq;
	private String name;
	private String content;
	
	public DocumentTypeDTO() {
	}

	public DocumentTypeDTO(int type_seq, String name, String content) {
		super();
		this.type_seq = type_seq;
		this.name = name;
		this.content = content;
	}
	
	public DocumentTypeDTO(int type_seq) {
		super();
		this.type_seq = type_seq;
	}

	@Override
	public String toString() {
		return "DocumentTypeDTO [type_seq=" + type_seq + ", name=" + name + ", content=" + content + "]";
	}

	public int getType_seq() {
		return type_seq;
	}

	public void setType_seq(int type_seq) {
		this.type_seq = type_seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
