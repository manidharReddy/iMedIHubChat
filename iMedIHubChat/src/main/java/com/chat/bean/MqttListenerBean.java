/**
 * 
 */
package com.chat.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author aparnareddychalla
 *
 */
@ManagedBean
@SessionScoped
public class MqttListenerBean implements IMqttMessageListener{
 
	@PostConstruct
	public void init() {
		
	}
	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		FacesContext context = FacesContext.getCurrentInstance();
		ChatterBoxBean chatterBoxBean = context.getApplication().evaluateExpressionGet(context, "#{chatterBoxBean}", ChatterBoxBean.class);
		chatterBoxBean.messageArrived(arg0, arg1);
		
	}

}
