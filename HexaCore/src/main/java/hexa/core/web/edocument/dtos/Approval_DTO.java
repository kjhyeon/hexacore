package hexa.core.web.edocument.dtos;

public class Approval_DTO {
	private int seq;
	private String id;
	private String chk;
	private int turn;
	
	public Approval_DTO() {
	}

	public Approval_DTO(int seq, String id, String chk, int turn) {
		super();
		this.seq = seq;
		this.id = id;
		this.chk = chk;
		this.turn = turn;
	}

	@Override
	public String toString() {
		return "Approval_DTO [seq=" + seq + ", id=" + id + ", chk=" + chk + ", turn=" + turn + "]";
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
