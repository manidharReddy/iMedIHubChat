package com.chat.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.bean.IhubLoginBean;
import com.chat.dao.LoginDao;

import com.chat.pojo.User;
import com.chat.pojo.UserTopic;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	//@Autowired
	//private LoggedUser loggedUser;


	
	private IhubLoginBean loginBean;
 



	private static final Logger LOGGER = Logger.getLogger(LoginDao.class);

	@PostConstruct
	public void init() {
		System.out.println("SERVICE - init()" + loginDao);
	}

	/**
	 * it will return LoginBean object
	 * @return
	 */
	public IhubLoginBean getLoginBean(){
		if( loginBean == null){
			FacesContext context = FacesContext.getCurrentInstance();
			loginBean = context.getApplication().evaluateExpressionGet(context, "#{ihubLoginBean}", IhubLoginBean.class);
		}
		return loginBean;
	}
	
		
	
	/**
	 * it will return current User object
	 * 
	 * @param username
	 * @return
	 */
	@Transactional
	public User getUser(String username) {
		LOGGER.info("SERVICE -getUser()");
		return loginDao.getUser(username);
	}
	
	/**
	 * it will return list of User objects
	 * 
	 * @param userid
	 * @return
	 */
	@Transactional
	public List<User> getListUsers(int userid){
		return loginDao.getListUsers(userid);
	}
	
	/**
	 * it will return list of UserTopics objects
	 * 
	 * @param userid
	 * @return
	 */
	@Transactional
	public List<UserTopic> getListOfUserTpoics(User userid){
		return loginDao.getListOfUserTpoics(userid);
	}

}
