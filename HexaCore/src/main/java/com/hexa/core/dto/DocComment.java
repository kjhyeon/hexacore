package com.hexa.core.dto;

import java.io.Serializable;
import java.util.Date;

public class DocComment implements Serializable{
	
	
	private static final long serialVersionUID = -4378413964846488201L;
	int seq          ;
	int comment_seq  ;
	String id        ;
	String name      ;
	String content   ;
	Date regdate     ;
	
	public DocComment() {
		// TODO Auto-generated constructor stub
	}

	public DocComment(int seq, int comment_seq, String id, String name, String content, Date regdate) {
		super();
		this.seq = seq;
		this.comment_seq = comment_seq;
		this.id = id;
		this.name = name;
		this.content = content;
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "DocComment [seq=" + seq + ", comment_seq=" + comment_seq + ", id=" + id + ", name=" + name
				+ ", content=" + content + ", regdate=" + regdate + "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getComment_seq() {
		return comment_seq;
	}

	public void setComment_seq(int comment_seq) {
		this.comment_seq = comment_seq;
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

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}