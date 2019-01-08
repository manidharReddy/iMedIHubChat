/**
 * 
 */
package com.chat.pojo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

/**
 * @author aparnareddychalla
 *
 */
public class MqttSubscribeParam {

	private List<String>listOfTopics;
	private List<Integer>listOfQos;
	private List<IMqttMessageListener>listOfIMqttMessageListener;
public void init() {
	
}

public MqttSubscribeParam() {
	// TODO Auto-generated constructor stub
}

/**
 * @return the listOfTopics
 */
public List<String> getListOfTopics() {
	return listOfTopics;
}

/**
 * @param listOfTopics the listOfTopics to set
 */
public void setListOfTopics(List<String> listOfTopics) {
	this.listOfTopics = listOfTopics;
}

/**
 * @return the listOfQos
 */
public List<Integer> getListOfQos() {
	return listOfQos;
}

/**
 * @param listOfQos the listOfQos to set
 */
public void setListOfQos(List<Integer> listOfQos) {
	this.listOfQos = listOfQos;
}

/**
 * @return the listOfIMqttMessageListener
 */
public List<IMqttMessageListener> getListOfIMqttMessageListener() {
	return listOfIMqttMessageListener;
}

/**
 * @param listOfIMqttMessageListener the listOfIMqttMessageListener to set
 */
public void setListOfIMqttMessageListener(List<IMqttMessageListener> listOfIMqttMessageListener) {
	this.listOfIMqttMessageListener = listOfIMqttMessageListener;
}



}
