/**
 * 
 */
package com.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.dao.MessageDao;
import com.chat.pojo.Message;
import com.chat.pojo.UserTopic;
import com.chat.utility.MessageUtility;

/**
 * @author aparnareddychalla
 *
 */
@Service
public class MesageService {
	@Autowired
	private MessageDao messageDao ;
	public Message getMessage(String str) {
		Message msg = null;
		msg = MessageUtility.deserializeJsonObject(str);
		
		return msg;
	}

	/**
	 * @author Manidhar
	 * @Date   12-06-2018
	 * it will update the messages object into UserTopic table
	 * 
	 * 
	 * @param topic
	 */
	
	@Transactional
	public void saveUserChatUsingTopic(UserTopic topic) {
		messageDao.saveUserChatUsingTopic(topic);
	}
}
