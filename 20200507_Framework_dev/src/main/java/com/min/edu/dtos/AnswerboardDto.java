package com.min.edu.dtos;

import java.io.Serializable;

public class AnswerboardDto implements Serializable {
	
	private static final long serialVersionUID = -8730468560420052269L;
	
	private int seq;
	private String id;
	private String title;
	private String content;
	private String regdate;
	private String enabled;
	private int readcount;
	private int reference;
	private int replyseq;
	private int depth;
	
	public AnswerboardDto() {
		super();
	}

	public AnswerboardDto(int seq, String id, String title, String content, String regdate, String enabled,
			int readcount, int reference, int replyseq, int depth) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
		this.enabled = enabled;
		this.readcount = readcount;
		this.reference = reference;
		this.replyseq = replyseq;
		this.depth = depth;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public int getReplyseq() {
		return replyseq;
	}

	public void setReplyseq(int replyseq) {
		this.replyseq = replyseq;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "AnswerboardDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", regdate="
				+ regdate + ", enabled=" + enabled + ", readcount=" + readcount + ", reference=" + reference
				+ ", replyseq=" + replyseq + ", depth=" + depth + "]";
	}

}
