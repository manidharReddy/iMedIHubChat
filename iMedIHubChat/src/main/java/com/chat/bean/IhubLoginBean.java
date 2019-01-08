package com.chat.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.chat.service.LoginService;

@ManagedBean(name = "ihubLoginBean")
@SessionScoped
public class IhubLoginBean implements Serializable {
	
@ManagedProperty("#{loginService}")	
private LoginService loginService;

@PostConstruct
public void init() {
	System.out.println("entred");
	
}
}
