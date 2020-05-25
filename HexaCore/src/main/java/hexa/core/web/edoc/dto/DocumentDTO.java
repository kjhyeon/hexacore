package hexa.core.web.edoc.dto;

public class DocumentDTO {

	private int seq;
	private String author;
	private String title;
	private String content;
	private int type_seq;
	private int state;
	private String regdate;
	private int appr_turn;
	
	public DocumentDTO() {
		super();
	}

	public DocumentDTO(int seq, String author, String title, String content, int type_seq, int state, String regdate,
			int appr_turn) {
		super();
		this.seq = seq;
		this.author = author;
		this.title = title;
		this.content = content;
		this.type_seq = type_seq;
		this.state = state;
		this.regdate = regdate;
		this.appr_turn = appr_turn;
	}

	@Override
	public String toString() {
		return "DocumentDTO [seq=" + seq + ", author=" + author + ", title=" + title + ", content=" + content
				+ ", type_seq=" + type_seq + ", state=" + state + ", regdate=" + regdate + ", appr_turn=" + appr_turn
				+ "]";
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType_seq() {
		return type_seq;
	}

	public void setType_seq(int type_seq) {
		this.type_seq = type_seq;
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
	
}
