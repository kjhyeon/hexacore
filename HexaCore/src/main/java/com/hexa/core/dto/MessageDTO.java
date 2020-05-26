package com.hexa.core.dto;

import java.io.Serializable;

public class MessageDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8214217492746279685L;
	private int seq        ;
	private String sender_id  ;
	private String receiver_id;
	private String title      ;
	private String content    ;
	private String regdate    ;
	private int state      ;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "MessageDTO [seq=" + seq + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", title="
				+ title + ", content=" + content + ", regdate=" + regdate + ", state=" + state + "]";
	}
}                             
