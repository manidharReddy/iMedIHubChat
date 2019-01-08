/**
 * 
 */
package com.chat.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import com.chat.pojo.Message;
import com.chat.pojo.UserMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author aparnareddychalla
 *
 */
public class MessageUtility {
public static ObjectMapper objectMapper = new ObjectMapper();

public static String serializeJsonString(Message msg) throws JsonProcessingException{
	String str = objectMapper.writeValueAsString(msg);
	
	return str;
}

public static Message deserializeJsonObject(String str) {
	Message msg = null;
	try {
		msg = objectMapper.readValue(str, Message.class);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return msg;
}



public static Map<Integer,UserMessages> getUsersChat(List<String>objects){
	Map<Integer,UserMessages>usermsgsMap = null;
	 
	try
    {
	FileInputStream fileIn = new FileInputStream(objects.get(0));
    ObjectInputStream in = new ObjectInputStream(fileIn);
    UserMessages deserializedUser = (UserMessages) in.readObject();
    in.close();
    fileIn.close();
    
}
catch (IOException ioe)
{
    ioe.printStackTrace();
}
catch (ClassNotFoundException cnfe)
{
    cnfe.printStackTrace();
}
	
	return usermsgsMap;
}

}
