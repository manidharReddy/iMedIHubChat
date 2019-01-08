/**
 * 
 */
package com.chat.utility;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.chat.pojo.Message;

/**
 * @author aparnareddychalla
 *
 */
public class MQTTUtility {
	private static MemoryPersistence memoryPersistence = new MemoryPersistence();
public static MqttClient  mqttConnection(String urlPath,String clientId) {
	MqttClient mqqtClient = null;
	try {
		System.out.println("mqttConnection");
		mqqtClient = new MqttClient(urlPath, clientId);
		
		//mqqtClient.setCallback(new MessageCallBack());
		//mqqtClient.connect(connOpts);
	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	
	return mqqtClient;
}

public static Boolean publish(MqttClient client,String topic,String mqttMessage) {
	MqttMessage mqttMsg = null;
	System.out.println("mqttClient Object:"+client);
	mqttMsg = new MqttMessage(mqttMessage.getBytes());
	
	
	mqttMsg.setRetained(false);
	Boolean isPublish=false;
	try {
		mqttMsg.setQos(2);
		System.out.println("topic name:"+topic);
		client.publish(topic, mqttMsg);
		isPublish = true;
		mqttMsg = null;
	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		mqttMsg = null;
	}
	return isPublish;
}

public static Boolean publishFile(MqttClient client,byte[] file,String topic) {
	Boolean isPublish=false;
	try {
		client.publish(topic,file, 2, true);
		isPublish = true;
	} catch (MqttPersistenceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return isPublish;
}

public static Boolean isDisconnected(MqttClient client) {
	if (client.isConnected()) {
		 try {
			client.disconnect();
			client.close();
			return true;
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		 
	} else {
		return true;
	}
	
}

}
