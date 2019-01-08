/**
 * 
 */
package com.chat.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chat.pojo.UserTopic;

/**
 * @author aparnareddychalla
 *
 */
@Repository
public class MessageDao {
	@Autowired
    private SessionFactory sessionFactory;
	private Session session = null;
	private static final Logger LOGGER = Logger.getLogger(MessageDao.class);
	@PostConstruct
    public void	init() {
		System.out.println("MessageDao init() SessionFactory :");
	}
	/**
	 * @author Manidhar
	 * @Date   12-06-2018
	 * it will update the messages object into UserTopic table
	 * 
	 * 
	 * @param topic
	 */
	
	public void saveUserChatUsingTopic(UserTopic topic) {
		session = sessionFactory.getCurrentSession();
		/*
		List<UserTopic> usertopics = session.createCriteria(UserTopic.class).add(Restrictions.eq("topicname", topic)).list();
	if(usertopics.size() > 0) {
		UserTopic usrTopicObject = usertopics.get(0);
		
		usrTopicObject
	}
	*/
		session.saveOrUpdate(topic);
	
	}
}
