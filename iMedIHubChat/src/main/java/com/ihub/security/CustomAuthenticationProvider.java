package com.ihub.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;


import com.chat.dao.LoginDao;

import com.chat.pojo.User;


public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private LoginDao loginDao;
	
	
	
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	@Transactional
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		LOGGER.info("authenticate(auth)");
		
		String username = auth.getName();
		String password = auth.getCredentials().toString();
		
		if( username.isEmpty() || password.isEmpty() ){
			throw new BadCredentialsException("Username/Password is missing");
		}
		
		//password = getEncryptedData(password);
		
		LOGGER.info("username :"+username+"\t password :"+password);
		User user = loginDao.getAuthenticate(username, password); 
		
		if( user != null){
			Calendar today = Calendar.getInstance();
			
			
			//validate product key for On Premises clients
			/*if( false ){
				Customer customer= user.getCustomer();
				String productKey=ProductKeyGenerator.generateProductKey(customer);
				if(!customer.getProdKey().equals(productKey)){
					throw new BadCredentialsException("License has been Expired"); 
				}
				
			}*/
			
			
			
			Date now = new Date();
			try {
				String today1 = format.format(today.getTime()) ;
				now = format.parse(  today1 );
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			System.out.println("PARSE COMPLETED");
			/*System.out.println("Created On :"+user.getCreatedOn());
			System.out.println("Expired On :"+user.getExpiredOn());
			System.out.println("today :"+today.getTime());
			System.out.println("Now :"+now);*/
			/*
			if(user.getCreatedOn() == null){
				throw new BadCredentialsException("User did not created");
			}else if( user.getCreatedOn().after( now    )    ){
				throw new BadCredentialsException("User did not Instantiated");
			}
			
			if( user.getExpiredOn() != null ){
				if( user.getExpiredOn().before(now)){
					throw new BadCredentialsException("User has been Expired");
				}
			}
			
			*/
			
			
			Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			
			
				authorities.add(new SimpleGrantedAuthority(username));
			
			
			
			LOGGER.info("Successully Logged");
			//user.setLastlogin(new Date());
			
		//	loginDao.updateUser(user);
			
			
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), authorities);
			
		}else{
			throw new BadCredentialsException("Invalid Authentication");
		}
	}

	
	
	public static String getEncryptedData(String input){
		String encryptedData = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes());
			byte byteData[] = digest.digest();
			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        encryptedData = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedData;
	}
	
	@Override
	public boolean supports(Class<?> auth) {
		return true;
	}

}
