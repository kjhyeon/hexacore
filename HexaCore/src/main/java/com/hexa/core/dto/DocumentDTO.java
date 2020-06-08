package com.hexa.core.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentDTO implements Serializable{

	private static final long serialVersionUID = -4661524697256646593L;
	
	private int seq;
	private String author;
	private String title;
	private int state;
	private String regdate;
	private int appr_turn;
	private int type_seq;
	private String content;
	private List<ApprovalDTO> lists;
	private int a_turn;
	
	public DocumentDTO() {
	}

	public DocumentDTO(int seq, String author, String title, int state, String regdate, int appr_turn, int type_seq,
			String content, List<ApprovalDTO> lists, int a_turn) {
		super();
		this.seq = seq;
		this.author = author;
		this.title = title;
		this.state = state;
		this.regdate = regdate;
		this.appr_turn = appr_turn;
		this.type_seq = type_seq;
		this.content = content;
		this.lists = lists;
		this.a_turn = a_turn;
	}

	@Override
	public String toString() {
		return "DocumentDTO [seq=" + seq + ", author=" + author + ", title=" + title + ", state=" + state + ", regdate="
				+ regdate + ", appr_turn=" + appr_turn + ", type_seq=" + type_seq + ", content=" + content + ", lists="
				+ lists + ", a_turn=" + a_turn + "]";
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getAppr_turn() {
		return appr_turn;
	}

	public void setAppr_turn(int appr_turn) {
		this.appr_turn = appr_turn;
	}

	public int getType_seq() {
		return type_seq;
	}

	public void setType_seq(int type_seq) {
		this.type_seq = type_seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<ApprovalDTO> getLists() {
		return lists;
	}

	public void setLists(List<ApprovalDTO> lists) {
		this.lists = lists;
	}

	public int getA_turn() {
		return a_turn;
	}

	public void setA_turn(int a_turn) {
		this.a_turn = a_turn;
	}
 
}
