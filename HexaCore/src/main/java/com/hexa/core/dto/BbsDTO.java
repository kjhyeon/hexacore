package com.hexa.core.dto;

import java.io.Serializable;

public class BbsDTO implements Serializable {

	private static final long serialVersionUID = 5525630636364245766L;
	private int seq;
	private String id;
	private String name;
	private String title;
	private String content;
	private int state;
	private String regdate;
	private int views;
	private int root;
	private int reply_seq;
	private int bbs_depth;
	private int f_count;
	private int c_count;

	public BbsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BbsDTO(int seq, String id, String name, String title, String content, int state, String regdate, int views,
			int root, int reply_seq, int bbs_depth, int f_count, int c_count) {
		super();
		this.seq = seq;
		this.id = id;
		this.name = name;
		this.title = title;
		this.content = content;
		this.state = state;
		this.regdate = regdate;
		this.views = views;
		this.root = root;
		this.reply_seq = reply_seq;
		this.bbs_depth = bbs_depth;
		this.f_count = f_count;
		this.c_count = c_count;
	}

	@Override
	public String toString() {
		return "BbsDTO [seq=" + seq + ", id=" + id + ", name=" + name + ", title=" + title + ", content=" + content
				+ ", state=" + state + ", regdate=" + regdate + ", views=" + views + ", root=" + root + ", reply_seq="
				+ reply_seq + ", bbs_depth=" + bbs_depth + ", f_count=" + f_count + ", c_count=" + c_count + "]";
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public int getReply_seq() {
		return reply_seq;
	}

	public void setReply_seq(int reply_seq) {
		this.reply_seq = reply_seq;
	}

	public int getBbs_depth() {
		return bbs_depth;
	}

	public void setBbs_depth(int bbs_depth) {
		this.bbs_depth = bbs_depth;
	}

	public int getF_count() {
		return f_count;
	}

	public void setF_count(int f_count) {
		this.f_count = f_count;
	}

	public int getC_count() {
		return c_count;
	}

	public void setC_count(int c_count) {
		this.c_count = c_count;
	}

}
