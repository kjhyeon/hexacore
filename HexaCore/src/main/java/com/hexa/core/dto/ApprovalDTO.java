package com.hexa.core.dto;

import java.io.Serializable;

public class ApprovalDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1634172012210979602L;
	private int seq;
	private String id;
	private String chk;
	private int turn;
	
	public ApprovalDTO() {
		super();
	}

	public ApprovalDTO(int seq, String id, String chk, int turn) {
		super();
		this.seq = seq;
		this.id = id;
		this.chk = chk;
		this.turn = turn;
	}

	@Override
	public String toString() {
		return "ApprovalDTO [seq=" + seq + ", id=" + id + ", chk=" + chk + ", turn=" + turn + "]";
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

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
}
