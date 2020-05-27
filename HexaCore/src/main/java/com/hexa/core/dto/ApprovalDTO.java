package com.hexa.core.dto;

import java.io.Serializable;
import java.util.Date;

public class ApprovalDTO implements Serializable{
	
	
	private static final long serialVersionUID = -4378819475081430107L;
	private int seq          ;
	private String id		 ;
	private String name      ;
	private String duty      ;
	private String chk       ;
	private int turn         ;
	private Date appr_date   ;
	private String appr_kind ;
	private String appr_sign ;
	
	public ApprovalDTO() {
		// TODO Auto-generated constructor stub
	}

	public ApprovalDTO(int seq, String id, String name, String duty, String chk, int turn, Date appr_date,
			String appr_kind, String appr_sign) {
		super();
		this.seq = seq;
		this.id = id;
		this.name = name;
		this.duty = duty;
		this.chk = chk;
		this.turn = turn;
		this.appr_date = appr_date;
		this.appr_kind = appr_kind;
		this.appr_sign = appr_sign;
	}

	
	@Override
	public String toString() {
		return "ApprovalDTO [seq=" + seq + ", id=" + id + ", name=" + name + ", duty=" + duty + ", chk=" + chk
				+ ", turn=" + turn + ", appr_date=" + appr_date + ", appr_kind=" + appr_kind + ", appr_sign="
				+ appr_sign + "]";
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

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
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

	public Date getAppr_date() {
		return appr_date;
	}

	public void setAppr_date(Date appr_date) {
		this.appr_date = appr_date;
	}

	public String getAppr_kind() {
		return appr_kind;
	}

	public void setAppr_kind(String appr_kind) {
		this.appr_kind = appr_kind;
	}

	public String getAppr_sign() {
		return appr_sign;
	}

	public void setAppr_sign(String appr_sign) {
		this.appr_sign = appr_sign;
	}
	
}
	