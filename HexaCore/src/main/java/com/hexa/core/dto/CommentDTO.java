package com.hexa.core.dto;

import java.io.Serializable;

public class CommentDTO implements Serializable{

	private static final long serialVersionUID = 6394680361704233110L;
	private int seq;
	private String id;
	private String name;
	private String content;
	private String regdate;
	private int parent_seq;

	public CommentDTO() {
		super();
	}

	public CommentDTO(int seq, String id, String name, String content, String regdate, int parent_seq) {
		super();
		this.seq = seq;
		this.id = id;
		this.name = name;
		this.content = content;
		this.regdate = regdate;
		this.parent_seq = parent_seq;
	}

	@Override
	public String toString() {
		return "CommentDTO [seq=" + seq + ", id=" + id + ", name=" + name + ", content=" + content + ", regdate="
				+ regdate + ", parent_seq=" + parent_seq + "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getParent_seq() {
		return parent_seq;
	}

	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}

}
