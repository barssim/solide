package models;

public class SellerBean {
	
	String civilite;
	String loginName;
	String emailaddr;
	String telefonNr;
	String userPassword;
	int contactNo;
	public int getContactNo() {
		return contactNo;
	}
	public void setContactNo(int contactNo) {
		this.contactNo = contactNo;
	}
	public int getLocationNo() {
		return locationNo;
	}
	public void setLocationNo(int locationNo) {
		this.locationNo = locationNo;
	}
	int locationNo;
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public String getLoginname() {
		return loginName;
	}
	public void setLoginname(String loginName) {
		this.loginName = loginName;
	}
	public String getEmailaddr() {
		return emailaddr;
	}
	public void setEmailaddr(String emailaddr) {
		this.emailaddr = emailaddr;
	}
	public String getTelefonNr() {
		return telefonNr;
	}
	public void setTelefonNr(String telefonNr) {
		this.telefonNr = telefonNr;
	}
	public String getUserpassword() {
		return userPassword;
	}
	public void setUserpassword(String userPassword) {
		this.userPassword = userPassword;
	}	
	
	

}
