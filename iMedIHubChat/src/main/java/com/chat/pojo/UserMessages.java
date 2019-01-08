/**
 * 
 */
package com.chat.pojo;

import java.io.Serializable;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author aparnareddychalla
 *
 */
public class UserMessages implements Serializable{
	private static final long serialVersionUID = 7829136421241571165L;
private int userId;
private String topicname;
private List<Message>usermsgs;




/**
 * 
 */
public UserMessages() {
	super();
	// TODO Auto-generated constructor stub
}
/**
 * @param userId
 * @param topicname
 * @param usermsgs
 */
public UserMessages(int userId, String topicname, List<Message> usermsgs) {
	super();
	this.userId = userId;
	this.topicname = topicname;
	this.usermsgs = usermsgs;
	
}
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
 * @return the topicname
 */
public String getTopicname() {
	return topicname;
}
/**
 * @param topicname the topicname to set
 */
public void setTopicname(String topicname) {
	this.topicname = topicname;
}
/**
 * @return the usermsgs
 */
public List<Message> getUsermsgs() {
	return usermsgs;
}
/**
 * @param usermsgs the usermsgs to set
 */
public void setUsermsgs(List<Message> usermsgs) {
	this.usermsgs = usermsgs;
}

//Setters and Getters

private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
{      
	userId = aInputStream.readInt();
	topicname = aInputStream.readUTF();
    
    usermsgs = (List<Message>)aInputStream.readObject();
}

private void writeObject(ObjectOutputStream aOutputStream) throws IOException
{
	aOutputStream.writeInt(userId);
	aOutputStream.writeUTF(topicname);
    aOutputStream.writeObject(usermsgs);
    
    
}

}
