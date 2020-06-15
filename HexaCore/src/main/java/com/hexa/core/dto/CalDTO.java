package com.hexa.core.dto;

public class CalDTO {

	private String id     ;
	private String title  ;
	private String start_date  ;
	private String end_date    ;
	
	public CalDTO() {
	}
	
	@Override
	public String toString() {
		return "CalDTO [id=" + id + ", title=" + title + ", start_date=" + start_date + ", end_date=" + end_date + "]";
	}
	public CalDTO(String id, String title, String start_date, String end_date) {
		super();
		this.id = id;
		this.title = title;
		this.start_date = start_date;
		this.end_date = end_date;
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
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
}
