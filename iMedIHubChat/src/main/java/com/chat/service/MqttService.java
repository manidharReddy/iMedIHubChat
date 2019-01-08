/**
 * 
 */
package com.chat.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.stereotype.Service;

import com.chat.bean.ChatterBoxBean;
import com.chat.bean.IhubLoginBean;
import com.chat.bean.MqttListenerBean;
import com.chat.pojo.Message;
import com.chat.pojo.MqttSubscribeParam;
import com.chat.pojo.UserTopic;
import com.chat.utility.MQTTUtility;
import com.chat.utility.MessageUtility;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author aparnareddychalla
 *
 */
@Service
public class MqttService {
	public Boolean publishMessage(MqttClient client,String topic,Message mqttMessage){
		String jsonMsg = null;
		try {
			jsonMsg = MessageUtility.serializeJsonString(mqttMessage);
			System.out.println("jsonMsg:"+jsonMsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return	MQTTUtility.publish(client, topic, jsonMsg);

}
	
	public Boolean publishFileMessage(MqttClient client,String topic,Message mqttMessage){
		String jsonMsg = null;
		try {
			jsonMsg = MessageUtility.serializeJsonString(mqttMessage);
			System.out.println("jsonMsg:"+jsonMsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return	MQTTUtility.publishFile(client,jsonMsg.getBytes(), topic);

}
	
	public MqttSubscribeParam getTopicNames(List<UserTopic> topics) {
		
		MqttSubscribeParam param = new MqttSubscribeParam();
		
		FacesContext context = FacesContext.getCurrentInstance();
		MqttListenerBean mqttListenerBean = context.getApplication().evaluateExpressionGet(context, "#{mqttListenerBean}", MqttListenerBean.class);
		 List<String>listOfTopics  = new ArrayList<String>(topics.size());
		 List<Integer>listOfQos = new ArrayList<Integer>(topics.size());
		 
		 
		 List<IMqttMessageListener>listOfIMqttMessageListener = new ArrayList<IMqttMessageListener>(topics.size());
		 
		 
			
		 
		for(UserTopic topic : topics) {
			
			listOfTopics.add(topic.getTopicname());
			listOfQos.add(2);
			listOfIMqttMessageListener.add(mqttListenerBean);
			
		}
		param.setListOfTopics(listOfTopics);
		param.setListOfQos(listOfQos);
		param.setListOfIMqttMessageListener(listOfIMqttMessageListener);
		return param;
	}
	
	public  MqttClient mqttConnection(String url,String clientId) {
		return MQTTUtility.mqttConnection(url, clientId);
	}

	public Boolean disconnectMqtt(MqttClient client) {
		return MQTTUtility.isDisconnected(client);
	}
	
	}
