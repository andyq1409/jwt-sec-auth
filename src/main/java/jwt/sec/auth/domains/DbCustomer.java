package jwt.sec.auth.domains;

public class DbCustomer {

	private Long customer_id;
	private String cust_first_name;
	private String cust_last_name;
	private String cust_street_address1;
	private String cust_street_address2;
	private String cust_city;
	private String cust_state;
	private String cust_postal_code;
	private String cust_email;
	private String phone_number1;
	private String phone_number2;
	private String url;	
	
	public DbCustomer() {		
	}	
	
	public DbCustomer(Long customer_id, String cust_first_name, String cust_last_name, String cust_street_address1,
			String cust_city, String cust_state, String cust_postal_code, String cust_email, String phone_number1) {
		super();
		this.customer_id = customer_id;
		this.cust_first_name = cust_first_name;
		this.cust_last_name = cust_last_name;
		this.cust_street_address1 = cust_street_address1;
		this.cust_city = cust_city;
		this.cust_state = cust_state;
		this.cust_postal_code = cust_postal_code;
		this.cust_email = cust_email;
		this.phone_number1 = phone_number1;
	}

	public Long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	public String getCust_first_name() {
		return cust_first_name;
	}
	public void setCust_first_name(String cust_first_name) {
		this.cust_first_name = cust_first_name;
	}
	public String getCust_last_name() {
		return cust_last_name;
	}
	public void setCust_last_name(String cust_last_name) {
		this.cust_last_name = cust_last_name;
	}
	public String getCust_street_address1() {
		return cust_street_address1;
	}
	public void setCust_street_address1(String cust_street_address1) {
		this.cust_street_address1 = cust_street_address1;
	}
	public String getCust_street_address2() {
		return cust_street_address2;
	}
	public void setCust_street_address2(String cust_street_address2) {
		this.cust_street_address2 = cust_street_address2;
	}
	public String getCust_city() {
		return cust_city;
	}
	public void setCust_city(String cust_city) {
		this.cust_city = cust_city;
	}
	public String getCust_state() {
		return cust_state;
	}
	public void setCust_state(String cust_state) {
		this.cust_state = cust_state;
	}
	public String getCust_postal_code() {
		return cust_postal_code;
	}
	public void setCust_postal_code(String cust_postal_code) {
		this.cust_postal_code = cust_postal_code;
	}
	public String getCust_email() {
		return cust_email;
	}
	public void setCust_email(String cust_email) {
		this.cust_email = cust_email;
	}
	public String getPhone_number1() {
		return phone_number1;
	}
	public void setPhone_number1(String phone_number1) {
		this.phone_number1 = phone_number1;
	}
	public String getPhone_number2() {
		return phone_number2;
	}
	public void setPhone_number2(String phone_number2) {
		this.phone_number2 = phone_number2;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "DbCustomer [customer_id=" + customer_id + ", cust_first_name=" + cust_first_name + ", cust_last_name="
				+ cust_last_name + ", cust_street_address1=" + cust_street_address1 + ", cust_street_address2="
				+ cust_street_address2 + ", cust_city=" + cust_city + ", cust_state=" + cust_state
				+ ", cust_postal_code=" + cust_postal_code + ", cust_email=" + cust_email + ", phone_number1="
				+ phone_number1 + ", phone_number2=" + phone_number2 + ", url=" + url + "]";
	}
	
	
}
