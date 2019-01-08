package com.chat.utility;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SessionUtil {

	@Autowired			  
	static SessionFactory sessionFactory;
	static Session session = null;
	
	static{
		sessionFactory = new Configuration().configure("com/ihub/config/hibernate.cfg.xml").buildSessionFactory();
	}
	
	public static Session getSession(){
		if( session == null )
			session = sessionFactory.openSession();
		return session;
	}
	
	public static void closeSession(Session session){
		if( session != null)
			session.close();
	}
}
