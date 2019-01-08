package com.chat.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.chat.pojo.User;
import com.chat.pojo.UserTopic;
import com.chat.utility.SessionUtil;


@Repository
public class LoginDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session = null;
	private static final Logger LOGGER = Logger.getLogger(LoginDao.class);
	

	@PostConstruct
	public void init(){
		System.out.println("DAO-init() "+sessionFactory);
	}
	
	/**
	 * @author Manidhar
	 * @Date   13-07-2018
	 * It will return the list of objects based on class type
	 * 
	 * @param cls
	 * @return
	 */
	//@Transactional
	public List getList(Class cls){
		System.out.println("DAO - getList :"+cls.getName());
		session = sessionFactory.getCurrentSession();
		List list = session.createCriteria(cls).list();
		return list;
	}
	
	/**
	 * @author Manidhar
	 * @Date   13-07-2018
	 * It will return the User object based on the username if exist
	 * 
	 * @param username
	 * @return
	 */
	//@Transactional
	public User getUser(String username){
		LOGGER.info("DAO - getUser("+username+")");
		session = sessionFactory.getCurrentSession();
		List list = session.createCriteria(User.class)
					.add(Restrictions.eq("username", username))
					.list();
		if( list.size() > 0 ){
			User user = (User) list.get(0);
			Hibernate.initialize(user.getUserregistration());
			Hibernate.initialize(user.getUsertype());
			Hibernate.initialize(user.getUsraddr());
			
			return user;
		}
		return null;
	}
	
	/**
	 * @author Manidhar
	 * @Date   13-07-2018
	 * it will return the User object based on the username and password if exist
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	//@Transactional
	public User getAuthenticate(String username, String password){
		session = sessionFactory.getCurrentSession();
		List userList = session.createCriteria(User.class)
				/*.add(Restrictions.and(
						Restrictions.eq("username", username), 
						Restrictions.eq("password", password)))*/
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("password", password))
				//.add(Restrictions.eq("active", true))
			.list();
		System.out.println("User Records are loaded ");
		if( !userList.isEmpty()){
			System.out.println("Users are :"+userList.size());
			User user = (User) userList.get(0);
			return  user;
		}
		
		return null;
	}
	
	
	
	/**
	 * @author Manidhar
	 * @Date   12-06-2017
	 * it will update the user object into lims_user table
	 * 
	 * 
	 * @param user
	 */
	public void updateUser(User user){
		LOGGER.info("DAO - updateUser("+user.getUsername()+")");
		session = sessionFactory.getCurrentSession();
		session.update(user);
		LOGGER.info("DAO - updateUser("+user.getUsername()+") has updated successfully");
	}
	
	/**
	 * @author Manidhar
	 * @Date   12-06-2017
	 * it will update the user object into lims_user table
	 * 
	 * 
	 * @param user
	 */
	
	public List<User> getListUsers(int userid){
		System.out.println("UserId:"+userid);
		session = sessionFactory.getCurrentSession();
		List<User> userList = session.createCriteria(User.class)
				
				.add(Restrictions.ne("userId", userid))
				//.add(Restrictions.eq("active", true))
			.list();
		
		return userList;
	}
	
	/**
	 * @author Manidhar
	 * @Date   12-06-2017
	 * it will update the user object into lims_user table
	 * 
	 * 
	 * @param user
	 */
	
	public List<UserTopic> getListOfUserTpoics(User userid){
		System.out.println("UserId:"+userid);
		session = sessionFactory.getCurrentSession();
		Criteria userTopicCriteria = session.createCriteria(UserTopic.class);
				Criterion loggedUserIds = Restrictions.eq("loggedUser", userid);
				Criterion chatuserIds = Restrictions.eq("chatUser", userid);
				LogicalExpression orExp = Restrictions.or(loggedUserIds, chatuserIds);
				userTopicCriteria.add(orExp);
				
				
				
				//.add(Restrictions.eq("loggedUser", userid))
				//.add(Restrictions.eq("active", true))
			//.list();
		 
		 List<UserTopic> userList = userTopicCriteria.list();
		if( userList.size() > 0 ){
		for(UserTopic topic : userList) {
		
			Hibernate.initialize(topic.getChatUser());
			Hibernate.initialize(topic.getLoggedUser());
			Hibernate.initialize(topic.getMessages());
			Hibernate.initialize(topic.getLoggedUser().getUserregistration());
			Hibernate.initialize(topic.getChatUser().getUserregistration());
			
		}}
		return userList;
	}
	
	
}
