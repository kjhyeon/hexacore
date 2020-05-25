package hexa.core.web.mng.dto;


public class EmployeeDTO {
	private int employee_number;
	private String id;
	private String password       ;
	private String name           ;
	private String phone          ;
	private String email          ;
	private int e_rank         ;
	private String auth;
	private String e_rank_name    ;
	private String birth         ;
	private int department_id  ;
	private String department_name; 
	private String join_date      ;
	private String gender         ;
	private String postcode       ;
	private String address        ;
	private String detailaddress  ;
	private String state;
	private String profile_img    ;
	private String sign_img       ;
	public EmployeeDTO() {
		// TODO Auto-generated constructor stub
	}
	public int getEmployee_number() {
		return employee_number;
	}
	public void setEmployee_number(int employee_number) {
		this.employee_number = employee_number;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getE_rank() {
		return e_rank;
	}
	public void setE_rank(int e_rank) {
		this.e_rank = e_rank;
	}
	public String getE_rank_name() {
		return e_rank_name;
	}
	public void setE_rank_name(String e_rank_name) {
		this.e_rank_name = e_rank_name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailaddress() {
		return detailaddress;
	}
	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public String getSign_img() {
		return sign_img;
	}
	public void setSign_img(String sign_img) {
		this.sign_img = sign_img;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "EmployeeDTO [employee_number=" + employee_number + ", id=" + id + ", password=" + password + ", name="
				+ name + ", phone=" + phone + ", email=" + email + ", e_rank=" + e_rank + ", auth=" + auth
				+ ", e_rank_name=" + e_rank_name + ", birth=" + birth + ", department_id=" + department_id
				+ ", department_name=" + department_name + ", join_date=" + join_date + ", gender=" + gender
				+ ", postcode=" + postcode + ", address=" + address + ", detailaddress=" + detailaddress + ", state="
				+ state + ", profile_img=" + profile_img + ", sign_img=" + sign_img + "]";
	}
}
