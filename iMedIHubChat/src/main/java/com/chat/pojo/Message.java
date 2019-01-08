/**
 * 
 */
package com.chat.pojo;

import java.util.Date;

import com.chat.utility.MessageTypeUtility;
import com.chat.utility.UserTypeUtility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * @author aparnareddychalla
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "message",
    "type",
    "image",
    "toUser",
    "fromUser",
    "username",
    "usertopicname",
    "dbmsgId",
    "filename"
    
})
public class Message implements Comparable<Message>{
	@JsonProperty("messageType")
private MessageTypeUtility msgType;
	@JsonProperty("type")
	private String messageType;
	private String usrType;
	@JsonProperty("dbmsgId")
private int usermsgID;
private UserTypeUtility userType;
@JsonProperty("message")
private String content;
private String msgId;
@JsonProperty("toUser")
private String toUserId;
@JsonProperty("fromUser")
private String fromUserId;
@JsonProperty("filename")
private String filename;
@JsonProperty("username")
private String username;
@JsonProperty("usertopicname")
private String topicName;
private String msgDate;
private Date createdOn;


@JsonProperty("messageType")
public MessageTypeUtility getMsgType() {
	return msgType;
}
@JsonProperty("messageType")
public void setMsgType(MessageTypeUtility msgType) {
	this.msgType = msgType;
}
public UserTypeUtility getUserType() {
	return userType;
}
public void setUserType(UserTypeUtility userType) {
	this.userType = userType;
}
@JsonProperty("message")
public String getContent() {
	return content;
}
@JsonProperty("message")
public void setContent(String content) {
	this.content = content;
}



public String getMsgId() {
	return msgId;
}
public void setMsgId(String msgId) {
	this.msgId = msgId;
}

/**
 * @return the filename
 */
@JsonProperty("filename")
public String getFilename() {
	return filename;
}
/**
 * @param filename the filename to set
 */
@JsonProperty("filename")
public void setFilename(String filename) {
	this.filename = filename;
}
/**
 * @return the toUserId
 */
@JsonProperty("toUser")
public String getToUserId() {
	return toUserId;
}
/**
 * @param toUserId the toUserId to set
 */
@JsonProperty("toUser")
public void setToUserId(String toUserId) {
	this.toUserId = toUserId;
}
/**
 * @return the fromUserId
 */@JsonProperty("fromUser")
public String getFromUserId() {
	return fromUserId;
}
/**
 * @param fromUserId the fromUserId to set
 */
 @JsonProperty("fromUser")
public void setFromUserId(String fromUserId) {
	this.fromUserId = fromUserId;
}
@JsonProperty("usertopicname")
public String getTopicName() {
	return topicName;
}
@JsonProperty("usertopicname")
public void setTopicName(String topicName) {
	this.topicName = topicName;
}
public String getMsgDate() {
	return msgDate;
}
public void setMsgDate(String msgDate) {
	this.msgDate = msgDate;
}
/**
 * @return the usermsgID
 */
@JsonProperty("dbmsgId")
public int getUsermsgID() {
	return usermsgID;
}
/**
 * @param usermsgID the usermsgID to set
 */
@JsonProperty("dbmsgId")
public void setUsermsgID(int usermsgID) {
	this.usermsgID = usermsgID;
}
/**
 * @return the messageType
 */
@JsonProperty("type")
public String getMessageType() {
	return messageType;
}
/**
 * @param messageType the messageType to set
 */
@JsonProperty("type")
public void setMessageType(String messageType) {
	this.messageType = messageType;
}
/**
 * @return the usrType
 */
public String getUsrType() {
	return usrType;
}
/**
 * @param usrType the usrType to set
 */
public void setUsrType(String usrType) {
	this.usrType = usrType;
}
/**
 * @return the username
 */
@JsonProperty("username")
public String getUsername() {
	return username;
}
/**
 * @param username the username to set
 */
@JsonProperty("username")
public void setUsername(String username) {
	this.username = username;
}
/**
 * @return the createdOn
 */
public Date getCreatedOn() {
	return createdOn;
}
/**
 * @param createdOn the createdOn to set
 */
public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
}
@Override
public int compareTo(Message o) {
	// TODO Auto-generated method stub
if(o.getCreatedOn().getTime()<this.getCreatedOn().getTime()) {
	return 1;
}else if(o.getCreatedOn().getTime()>this.getCreatedOn().getTime()) {
	return -1;
}
return 0;
}

}
