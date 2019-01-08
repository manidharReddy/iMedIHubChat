package com.chat.pojo;

import java.util.Date;

public class UserRegistration {
private int userregid;
private String userfirstname;
private String userlastname;
private String useremailid;
private String usermobilenumber;
private Date creation;
private Date update;
/**
 * @return the userregid
 */
public int getUserregid() {
	return userregid;
}
/**
 * @param userregid the userregid to set
 */
public void setUserregid(int userregid) {
	this.userregid = userregid;
}
/**
 * @return the userfirstname
 */
public String getUserfirstname() {
	return userfirstname;
}
/**
 * @param userfirstname the userfirstname to set
 */
public void setUserfirstname(String userfirstname) {
	this.userfirstname = userfirstname;
}
/**
 * @return the userlastname
 */
public String getUserlastname() {
	return userlastname;
}
/**
 * @param userlastname the userlastname to set
 */
public void setUserlastname(String userlastname) {
	this.userlastname = userlastname;
}
/**
 * @return the useremailid
 */
public String getUseremailid() {
	return useremailid;
}
/**
 * @param useremailid the useremailid to set
 */
public void setUseremailid(String useremailid) {
	this.useremailid = useremailid;
}
/**
 * @return the usermobilenumber
 */
public String getUsermobilenumber() {
	return usermobilenumber;
}
/**
 * @param usermobilenumber the usermobilenumber to set
 */
public void setUsermobilenumber(String usermobilenumber) {
	this.usermobilenumber = usermobilenumber;
}
/**
 * @return the creation
 */
public Date getCreation() {
	return creation;
}
/**
 * @param creation the creation to set
 */
public void setCreation(Date creation) {
	this.creation = creation;
}
/**
 * @return the update
 */
public Date getUpdate() {
	return update;
}
/**
 * @param update the update to set
 */
public void setUpdate(Date update) {
	this.update = update;
}


}
