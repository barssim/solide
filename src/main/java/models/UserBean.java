package models;

public class UserBean {
	
	String surName;
	String userName;
	String emailaddr;
//	String telefonNr;
	String userPassword;
//	int contactNo;
	int userNo;
	
public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	//	public int getContactNo() {
//		return contactNo;
//	}
//	public void setContactNo(int contactNo) {
//		this.contactNo = contactNo;
//	}
//	public int getLocationNo() {
//		return locationNo;
//	}
//	public void setLocationNo(int locationNo) {
//		this.locationNo = locationNo;
//	}
//	int locationNo;
	public String getSurname() {
		return surName;
	}
	public void setSurname(String surName) {
		this.surName = surName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String loginName) {
		this.userName = loginName;
	}
//	public String getEmailaddr() {
//		return emailaddr;
//	}
//	public void setEmailaddr(String emailaddr) {
//		this.emailaddr = emailaddr;
//	}
//	public String getTelefonNr() {
//		return telefonNr;
//	}
//	public void setTelefonNr(String telefonNr) {
//		this.telefonNr = telefonNr;
//	}
	public String getUserpassword() {
		return userPassword;
	}
	public void setUserpassword(String userPassword) {
		this.userPassword = userPassword;
	}	
	
	

}
