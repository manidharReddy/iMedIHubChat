package com.chat.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.primefaces.model.UploadedFile;



public class User  {

	private int userId;
	private String username;
	private String password;
	
	private Date lastlogin;
	
	private Date creation;
	private Date update;
	private boolean active;
	private UserRegistration userregistration;
	private UserType usertype;
	private UserAddress usraddr;
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the lastlogin
	 */
	public Date getLastlogin() {
		return lastlogin;
	}
	/**
	 * @param lastlogin the lastlogin to set
	 */
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
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
	/**
	 * @return the userregistration
	 */
	public UserRegistration getUserregistration() {
		return userregistration;
	}
	/**
	 * @param userregistration the userregistration to set
	 */
	public void setUserregistration(UserRegistration userregistration) {
		this.userregistration = userregistration;
	}
	/**
	 * @return the usertype
	 */
	public UserType getUsertype() {
		return usertype;
	}
	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}
	/**
	 * @return the usraddr
	 */
	public UserAddress getUsraddr() {
		return usraddr;
	}
	/**
	 * @param usraddr the usraddr to set
	 */
	public void setUsraddr(UserAddress usraddr) {
		this.usraddr = usraddr;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
		
	
}
