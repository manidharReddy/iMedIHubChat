/**
 * 
 */
package com.chat.pojo;

import java.util.Set;

/**
 * @author aparnareddychalla
 *
 */
public class UserTopic {
private int topicid;
private String topicname;
private User loggedUser;
private User chatUser;
private Set<Message>messages;
/**
 * @return the topicid
 */
public int getTopicid() {
	return topicid;
}
/**
 * @param topicid the topicid to set
 */
public void setTopicid(int topicid) {
	this.topicid = topicid;
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
 * @return the loggedUser
 */
public User getLoggedUser() {
	return loggedUser;
}
/**
 * @param loggedUser the loggedUser to set
 */
public void setLoggedUser(User loggedUser) {
	this.loggedUser = loggedUser;
}
/**
 * @return the chatUser
 */
public User getChatUser() {
	return chatUser;
}
/**
 * @param chatUser the chatUser to set
 */
public void setChatUser(User chatUser) {
	this.chatUser = chatUser;
}
/**
 * @return the messages
 */
public Set<Message> getMessages() {
	return messages;
}
/**
 * @param messages the messages to set
 */
public void setMessages(Set<Message> messages) {
	this.messages = messages;
}

}
