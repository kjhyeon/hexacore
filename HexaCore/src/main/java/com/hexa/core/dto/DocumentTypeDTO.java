package com.hexa.core.dto;

public class DocumentTypeDTO {
	
	private int type_seq;
	private String name;
	private String content;
	private String category;
	
	public DocumentTypeDTO() {
		super();
	}

	public DocumentTypeDTO(int type_seq, String name, String content, String category) {
		super();
		this.type_seq = type_seq;
		this.name = name;
		this.content = content;
		this.category = category;
	}

	@Override
	public String toString() {
		return "DocTypeDTO [type_seq=" + type_seq + ", name=" + name + ", content=" + content + ", category=" + category
				+ "]";
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
