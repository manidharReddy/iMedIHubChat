/**
 * 
 */
package com.chat.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.zip.InflaterInputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.hibernate.hql.internal.ast.tree.FromReferenceNode;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chat.pojo.Message;
import com.chat.pojo.MqttSubscribeParam;
import com.chat.service.LoginService;
import com.chat.service.MesageService;
import com.chat.service.MqttService;
import com.chat.utility.FilesUtility;
import com.chat.utility.MQTTUtility;
import com.chat.utility.MessageTypeUtility;
import com.chat.utility.UserTypeUtility;
import com.chat.pojo.User;
import com.chat.pojo.UserTopic;
/**
 * @author aparnareddychalla
 *
 */
@ManagedBean
@SessionScoped
public class ChatterBoxBean implements Serializable,MqttCallback,IMqttMessageListener{
	
	
@ManagedProperty("#{loginService}")	
private LoginService loginService;

@ManagedProperty("#{mesageService}")	
private MesageService messageService;

private TreeSet<Message> messages;
private Message message;
@ManagedProperty("#{mqttService}")
private MqttService mqttService;
//private ChatterBoxService chatterBoxService;
private String clientId;
private String topicName;
private String msgContent;
private MqttClient mqttClient = null;
private Boolean isSend = false;
public static String str = "tcp://144.76.40.143:1883";
//private static Date createdDate = new Date();
private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
private Boolean recievedMsg;
private User user;
private User selectedUser;
private List<User> usersList;
private List<UserTopic> userstopiclist;
private Map<Integer,UserTopic> mapOfUserTopic;
private boolean loggedUser,incomingCall,videoCallOn,formRefresh,ringingCall,audioCall,isFileExist;
private UserTopic selectedUserTopic;
private int callerIdentifier;
private String filepath;


@PostConstruct
public void init() {
	if(true) {
	String username = SecurityContextHolder.getContext().getAuthentication().getName();
	user = loginService.getUser(username);
	//usersList = loginService.getListUsers(user.getUserId());
	userstopiclist = loginService.getListOfUserTpoics(user);
	System.out.println("usertopics objects:"+userstopiclist);
	mapOfUserTopic = new LinkedHashMap<Integer,UserTopic>();
	for(UserTopic topic:userstopiclist) {
		if(topic.getChatUser().getUserId() != user.getUserId()) {
			
		mapOfUserTopic.put(topic.getChatUser().getUserId(), topic);
		}else if(topic.getLoggedUser().getUserId() != user.getUserId()) {
			mapOfUserTopic.put(topic.getLoggedUser().getUserId(), topic);
			}
	}
	//System.out.println("selectedUser Object:"+selectedUser);
	recievedMsg = true;
	incomingCall = false;
	videoCallOn = false;
	ringingCall = false;
	audioCall = false;
	isFileExist = false;
	connectToMqtt(String.valueOf(user.getUserId()));
	if(userstopiclist.size()>0) {
		System.out.println("userstopiclist.size():"+userstopiclist.size());
		selectedUserTopic = userstopiclist.get(0);
		selectChat(selectedUserTopic);
		
		
	}	
  }
}

//public void connectToMqtt(ActionEvent action) {
public void connectToMqtt(String clientId) {
	if(mqttClient == null) {
		System.out.println("clientId:"+clientId);
mqttClient = mqttService.mqttConnection(str, clientId);
MqttConnectOptions connOpts = new MqttConnectOptions();
connOpts.setAutomaticReconnect(true);
connOpts.setCleanSession(false);
//connOpts.setKeepAliveInterval(30);

try {
	mqttClient.setCallback(this);
	mqttClient.connect(connOpts);
	connOpts = null;
	//mqttClient.subscribe(topicName, 2, this);
	/*
	MqttSubscribeParam param = mqttService.getTopicNames(userstopiclist);
	
	String[] tps =  Arrays.stream(param.getListOfTopics().toArray()).toArray(String[]::new);
	
	int[] qos = param.getListOfQos().stream().mapToInt(Integer::intValue).toArray();
	IMqttMessageListener[] listner = Arrays.stream(param.getListOfIMqttMessageListener().toArray()).toArray(IMqttMessageListener[]::new);
	System.out.println("topicname:"+tps+"qos"+qos+"listerner:"+listner);
	//mqttClient.subscribe(tps, qos,listner);
	*/
	//mqttClient.disconnect();
	
	
} catch (MqttException e) {
	// TODO Auto-generated catch block
	//mqttClient.disconnect();
	//mqttClient.close();
	disconnectMqtt();
	e.printStackTrace();
}

	}else {
		System.out.println("Mqtt Connection is already established");
	}
}

//public void disconnectMqtt(ActionEvent action) {
public void disconnectMqtt() {
	if(mqttClient.isConnected()) {
		mqttService.disconnectMqtt(mqttClient);
	}
}
public void sendMessage(ActionEvent action) {
	callerIdentifier = generateRandomNumbers();
	System.out.println("messages object before:"+messages);
	//if(messages==null) {
	
	//messages = new TreeSet<Message>();
	//s}
	System.out.println("messages object after:"+messages);
	Message msg = new Message();
	msg.setContent(msgContent);
	msg.setMsgType(MessageTypeUtility.text);
	msg.setMessageType("text");
	
	msg.setTopicName(topicName);
	msg.setMsgId(String.valueOf(callerIdentifier));
	//msg.setUserId(String.valueOf(user.getUserId()));
	msg.setFromUserId(String.valueOf(user.getUserId()));
	msg.setUserType(UserTypeUtility.Owner);
	msg.setUsername(user.getUserregistration().getUserfirstname()+" "+user.getUserregistration().getUserlastname());
	
	if(selectedUserTopic.getChatUser().getUserId()!=user.getUserId()) {
	//	msg.setUserId(String.valueOf(selectedUserTopic.getChatUser().getUserId()));
		msg.setToUserId(String.valueOf(selectedUserTopic.getChatUser().getUserId()));
		msg.setUserType(UserTypeUtility.Owner);
		msg.setUsername(selectedUserTopic.getChatUser().getUserregistration().getUserfirstname()+" "+selectedUserTopic.getChatUser().getUserregistration().getUserlastname());	
	}else if(selectedUserTopic.getLoggedUser().getUserId()!=user.getUserId())  {
		msg.setToUserId(String.valueOf(selectedUserTopic.getLoggedUser().getUserId()));
		msg.setUserType(UserTypeUtility.Owner);
		msg.setUsername(selectedUserTopic.getLoggedUser().getUserregistration().getUserfirstname()+" "+selectedUserTopic.getLoggedUser().getUserregistration().getUserlastname());
	}
	
	Date createdDate = new Date();
	msg.setMsgDate(formatter.format(createdDate));
	createdDate = null;
	msg.setCreatedOn(new Date());
	System.out.println("messagesv:"+msg.getTopicName());
	System.out.println("messagesv id:"+msg.getUsermsgID());
	
	System.out.println("messages object before add:"+messages);
	//messages.add(msg);
	System.out.println("messages object after add:"+messages);
	//System.out.println("messages value:"+messages.get(0).getContent());
	System.out.println("messages object message:"+msg.getContent());
	System.out.println("messages object nsg:"+msgContent);
	messages.add(msg);
	//updateOpenChats();
	//selectedUserTopic.setMessages(messages);
	selectedUserTopic.getMessages().add(msg);
	
	//System.out.println("msg id:"+msg.getUsermsgID());
	
	messageService.saveUserChatUsingTopic(selectedUserTopic);
	//updateOpenChats();
	if(mqttService.publishMessage(mqttClient, topicName, msg)) {
		System.out.println("message send succesfully");
		isSend = true;
		
		//return;
	
	}else {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Msg Failed to send", ""));
	}
}


public void onRowSelect(SelectEvent event) {
	System.out.println("onRowSelect select");
	UserTopic topic = (UserTopic) event.getObject();
	if(topic!=null) {
		selectChat(topic);
	}
	    
}
/*
public void moveToLastMessage() {
	DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:msglistid");

    int lastPage = dataTable.getRowCount() / dataTable.getRows();
    int rowFirst = (lastPage * dataTable.getRows());

    dataTable.setFirst(firstRow);
    dataTable.setPage(lastPage + 1);
    dataTable.setRowIndex(dataTable.getRowCount() - 1);
}
*/
public void selectChat(UserTopic topic) {
	System.out.println("selectChat");
   try {
	   if(topicName!=null) {
		   mqttClient.unsubscribe(topicName);
	   topicName = null;
		//selectedUserTopic = null;
		messages = null;
	   }
	   
	
	if(topic.getChatUser().getUserId()!=user.getUserId()) {
		selectedUser = topic.getChatUser();
		FacesMessage msg = new FacesMessage("User Selected", topic.getChatUser().getUserregistration().getUserfirstname()+" "+topic.getChatUser().getUserregistration().getUserlastname());
	    FacesContext.getCurrentInstance().addMessage(null, msg);	
	}else if(topic.getLoggedUser().getUserId()!=user.getUserId())  {
		selectedUser = topic.getLoggedUser();
		FacesMessage msg = new FacesMessage("User Selected", topic.getLoggedUser().getUserregistration().getUserfirstname()+" "+topic.getLoggedUser().getUserregistration().getUserlastname());
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
   messages = new TreeSet<Message>(topic.getMessages());
   callerIdentifier = generateRandomNumbers();
   topicName = topic.getTopicname();
  mqttClient.subscribe(topicName, 2, this);
  
	filepath = "/Users/aparnareddychalla/Desktop/iMedIHUBChatImages/"+selectedUser.getUsername();
} catch (MqttException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
public int generateRandomNumbers() {
	callerIdentifier = ThreadLocalRandom.current().nextInt();
	return callerIdentifier;
}
public void isRecievedMessage() {
	if(!isSend) {
		updateOpenChats();
	}
}

@Override
public void connectionLost(Throwable arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void deliveryComplete(IMqttDeliveryToken arg0) {
	// TODO Auto-generated method stub
	
	System.out.println("Succefluyy sended");
	//updateOpenChats();
}

public void isRecieverRecievedMessage() {
	recievedMsg = true;
	if(formRefresh) {
		formRefresh = false;
	PrimeFaces.current().ajax().update("form");
	
	}else{
		
	}

}
/*
 * @Author:Manidhar Reddy
 * @Param:audio call button action event
 * @Description:Make a audio call to selected user based on topicname
 * @Exception: Making a audio call failed
 */
public void makeAudioCallAction(ActionEvent actionEvent) {
	callerIdentifier = generateRandomNumbers();
    Message makeCallMsg = new Message();
    
    makeCallMsg.setCreatedOn(new Date());
    makeCallMsg.setFromUserId(String.valueOf(user.getUserId()));
    
    makeCallMsg.setToUserId(String.valueOf(selectedUser.getUserId()));
    makeCallMsg.setContent(user.getUserregistration().getUserfirstname()+""+user.getUserregistration().getUserlastname()+" maked call to "+selectedUser.getUserregistration().getUserfirstname()+""+selectedUser.getUserregistration().getUserlastname());
    makeCallMsg.setTopicName(topicName);
    makeCallMsg.setMsgDate(formatter.format(new Date()));
    makeCallMsg.setUsername(selectedUser.getUserregistration().getUserfirstname()+""+selectedUser.getUserregistration().getUserlastname());
    makeCallMsg.setUsrType("Owner");
    makeCallMsg.setMessageType("audio");
    makeCallMsg.setMsgType(MessageTypeUtility.audio);
    makeCallMsg.setMsgId(String.valueOf(callerIdentifier));
    messages.add(makeCallMsg);
    
    selectedUserTopic.getMessages().add(makeCallMsg);
	messageService.saveUserChatUsingTopic(selectedUserTopic);
	//updateOpenChats();
	if(mqttService.publishMessage(mqttClient, topicName, makeCallMsg)) {
		System.out.println(" audio message send succesfully");
		//isSend = true;
		incomingCall = true;
		videoCallOn = false;
		audioCall = true;
		//return;
	
	}else {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Making a audio call failed", ""));
	}

}
/*
 * @Author:Manidhar Reddy
 * @Param:audio call button action event
 * @Description:Make a Video call to selected user based on topicname
 * @Exception: Making a Video call failed
 */

public void makeCallAction(ActionEvent actionEvent) {
	callerIdentifier = generateRandomNumbers();
    Message makeCallMsg = new Message();
    
    makeCallMsg.setCreatedOn(new Date());
    makeCallMsg.setFromUserId(String.valueOf(user.getUserId()));
    
    makeCallMsg.setToUserId(String.valueOf(selectedUser.getUserId()));
    makeCallMsg.setContent(user.getUserregistration().getUserfirstname()+""+user.getUserregistration().getUserlastname()+" maked call to "+selectedUser.getUserregistration().getUserfirstname()+""+selectedUser.getUserregistration().getUserlastname());
    makeCallMsg.setTopicName(topicName);
    makeCallMsg.setMsgDate(formatter.format(new Date()));
    makeCallMsg.setUsername(selectedUser.getUserregistration().getUserfirstname()+""+selectedUser.getUserregistration().getUserlastname());
    makeCallMsg.setUsrType("Owner");
    makeCallMsg.setMessageType("video");
    makeCallMsg.setMsgId(String.valueOf(callerIdentifier));
    makeCallMsg.setMsgType(MessageTypeUtility.video);
    messages.add(makeCallMsg);
    //isIncomingCall = true;
selectedUserTopic.getMessages().add(makeCallMsg);
	
	//System.out.println("msg id:"+msg.getUsermsgID());

	messageService.saveUserChatUsingTopic(selectedUserTopic);
	//updateOpenChats();
	if(mqttService.publishMessage(mqttClient, topicName, makeCallMsg)) {
		System.out.println(" video message send succesfully");
		//isSend = true;
		incomingCall = true;
		videoCallOn = true;
		
		//return;
	
	}else {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("video Msg Failed to send", ""));
	}
    

}

/*
 * @Author:Manidhar Reddy
 * @Param:FileUploadEvent event
 * @Description:Send a file in message
 * @Exception: File not send
 */

public void handleFileUpload(FileUploadEvent event) {
    
   try {
	   
	   //String fileAppend = filepath+selectedUser.getUsername();
	   String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
	   String extension = FilenameUtils.getExtension(event.getFile().getFileName());
	   //Path fileph = FileSystems.getDefault().getPath(filepath);
	   
	   Path ph = Paths.get(filepath+"/"+filename +"." + extension);
	   
	  // Path fileP = Files.createTempFile(fileph, filename + "-", "." + extension);
	   Path fileP = Files.createDirectories(ph);
	   
	   //InflaterInputStream iis = new InflaterInputStream(event.getFile().getInputstream());
	   InputStream iis = event.getFile().getInputstream();
	   //InputStream iis = new ByteArrayInputStream(event.getFile().getInputstream());
       Files.copy(iis, fileP,
               StandardCopyOption.REPLACE_EXISTING);
       //fileP.toString();
       
       String binaryFileString = new String(IOUtils.readFully(iis, iis.available()));
       //String binaryFileString = Base64.getEncoder().encodeToString(IOUtils.toByteArray(iis)); 
       System.out.println("binary pdf:"+binaryFileString);
       iis.close();
   
   FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
   FacesContext.getCurrentInstance().addMessage(null, message);
   
   Message msg = createMessage();
   msg.setCreatedOn(new Date());
   msg.setFromUserId(String.valueOf(user.getUserId()));
   msg.setMsgDate(formatter.format(new Date()));
   msg.setTopicName(selectedUserTopic.getTopicname());
   msg.setToUserId(String.valueOf(selectedUser.getUserId()));
   msg.setUsername(selectedUser.getUserregistration().getUserfirstname()+" "+selectedUser.getUserregistration().getUserlastname());
   msg.setMessageType(extension);
   msg.setUsrType("Owner");
   msg.setFilename(filename);
   msg.setContent(fileP.toString());
   messages.add(msg);
   messageService.saveUserChatUsingTopic(selectedUserTopic);
	//updateOpenChats();
	//if(mqttService.publishMessage(mqttClient, topicName, msg)) {
   if(mqttService.publishFileMessage(mqttClient, topicName, msg)){
		System.out.println(" file message send succesfully");
		//isSend = true;
		//incomingCall = true;
		//videoCallOn = true;
		isFileExist = true;
		
		//return;
	
	}else {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File upload failed to send", ""));
	}

   
   } catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	FacesMessage message = new FacesMessage("Failed to upload", event.getFile().getFileName());
    FacesContext.getCurrentInstance().addMessage(null, message);
}
    
}



public Message createMessage() {
	return new Message();
}
/*
 * @Author:Manidhar Reddy
 * @Param:liftCall button event
 * @Description:Lifiting recieved call
 * 
 */

public void liftCallButton(ActionEvent actionEvent) {
	Message msg = messages.last();
	if(MessageTypeUtility.video.name().equalsIgnoreCase(msg.getMsgType().name())) {
		videoCallOn = true;
		audioCall = false;
	}else if(MessageTypeUtility.audio.name().equalsIgnoreCase(msg.getMsgType().name())) {
		videoCallOn = false;
		audioCall = true;
	}
	
	ringingCall = false;
}
/*
 * @Author:Manidhar Reddy
 * @Param:ActionEvent for ending a call
 * @Description:Disconnecting recieving call
 * 
 */

public void endCallButton(ActionEvent actionEvent) {
	incomingCall = false;
	videoCallOn = false;
	audioCall = false;
	ringingCall = false;
}

/*
 * @Author:Manidhar Reddy
 * @Param:Topic name(arg0),Body of the mqttMessage(arg1)
 * @Description:Receiveing subscribed topic messages
 * @Exception: MqttException
 */

@Override
public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
	// TODO Auto-generated method stub
	//isSend = false;
	System.out.println("called mesh");
	
	
	if(isSend) {
		System.out.println("is returned");
		isSend = false;
		recievedMsg = false;
		return;
	}else {
	isSend = true;
	System.out.println("isSend:"+isSend);	
	String msgStr = new String(arg1.getPayload());
	System.out.println("msgStr:"+msgStr);
	Message msg = messageService.getMessage(msgStr);
	
	System.out.println("userId:"+user.getUserId());
	System.out.println("msg userId:"+msg.getToUserId());
	if(msg.getToUserId().equalsIgnoreCase(String.valueOf(user.getUserId()))) {
		msg.setUserType(UserTypeUtility.Reciever);
		//msg.setMsgType(MessageTypeUtility.text);
		msg.setUsermsgID(0);
			// if((msg.getFromUserId().equalsIgnoreCase(String.valueOf(selectedUserTopic.getLoggedUser().getUserId()))) || (msg.getFromUserId().equalsIgnoreCase(String.valueOf(selectedUserTopic.getChatUser().getUserId())))) {
	    		System.out.println("if mesage added to set");
				 
	    		msg.setCreatedOn(new Date());
	    	    
	    	    msg.setMsgDate(formatter.format(new Date()));
	    	   	    		messages.add(msg);
				 selectedUserTopic.getMessages().add(msg);
					
					//System.out.println("msg id:"+msg.getUsermsgID());
					
					//messageService.saveUserChatUsingTopic(selectedUserTopic);
					
					callerIdentifier = (int)Integer.valueOf(msg.getMsgId());
					System.out.println("callerid:"+callerIdentifier);
					System.out.println("msgTypeCheck:"+msg.getMsgType().toString());
					if(MessageTypeUtility.video.name().equalsIgnoreCase(msg.getMsgType().name())) {
						System.out.println("msgTypeCheck videoooo:");
						incomingCall = true;
						ringingCall = true;
						formRefresh = true;
						
					}else if(MessageTypeUtility.audio.name().equalsIgnoreCase(msg.getMsgType().name())) {
						System.out.println("msgTypeCheck audiooooo:");
						incomingCall = true;
						ringingCall = true;
						formRefresh = true;
					}
					
					/*
					
				if(msg.getMessageType().equalsIgnoreCase("video")) {
					incomingCall = true;
					ringingCall = true;
					callerIdentifier = (int)Integer.valueOf(msg.getMsgId());
					formRefresh = true;
				}else if(msg.getMessageType().equalsIgnoreCase("audio")) {
					incomingCall = true;
					ringingCall = true;
					callerIdentifier = (int)Integer.valueOf(msg.getMsgId());
					formRefresh = true;
				}else if(msg.getMessageType().equalsIgnoreCase("pdf")) {
					Path ph = Paths.get(filepath+"/"+msg.getFilename() +".pdf");
					   
					  // Path fileP = Files.createTempFile(fileph, filename + "-", "." + extension);
					   Path fileP = Files.createDirectories(ph);
					   
					Files.write(fileP, msg.getContent().getBytes());
				}
				*/
				recievedMsg = true;
	    	// } 
			 
	    	 return;
	     //}
	  }
	}
	
	
 }	

public Message getUserLastMessage() {
	
	Message msg = null;
	
	if(!messages.isEmpty()) {
		
	msg = messages.last();
	
	}
	return msg;
}
public void updateOpenChats() {
	PrimeFaces.current().ajax().update(":msglistid");
	PrimeFaces.current().ajax().update(":singleDT");
	
}


/**
 * @return the messages
 */
public TreeSet<Message> getMessages() {
	return messages;
}

/**
 * @param messages the messages to set
 */
public void setMessages(TreeSet<Message> messages) {
	this.messages = messages;
}

public Message getMessage() {
	return message;
}
public void setMessage(Message message) {
	this.message = message;
}

public String getClientId() {
	return clientId;
}
public void setClientId(String clientId) {
	this.clientId = clientId;
}

public MqttClient getMqttClient() {
	return mqttClient;
}

/**
 * @return the mqttService
 */
public MqttService getMqttService() {
	return mqttService;
}

/**
 * @param mqttService the mqttService to set
 */
public void setMqttService(MqttService mqttService) {
	this.mqttService = mqttService;
}

public void setMqttClient(MqttClient mqttClient) {
	this.mqttClient = mqttClient;
}

public String getTopicName() {
	return topicName;
}

public void setTopicName(String topicName) {
	this.topicName = topicName;
}

public String getMsgContent() {
	return msgContent;
}

public void setMsgContent(String msgContent) {
	this.msgContent = msgContent;
}




/**
 * @return the recievedMsg
 */
public Boolean getRecievedMsg() {
	return recievedMsg;
}

/**
 * @param recievedMsg the recievedMsg to set
 */
public void setRecievedMsg(Boolean recievedMsg) {
	this.recievedMsg = recievedMsg;
}

/**
 * @return the user
 */
public User getUser() {
	return user;
}

/**
 * @param user the user to set
 */
public void setUser(User user) {
	this.user = user;
}

/**
 * @return the loginService
 */
public LoginService getLoginService() {
	return loginService;
}

/**
 * @param loginService the loginService to set
 */
public void setLoginService(LoginService loginService) {
	this.loginService = loginService;
}

/**
 * @return the selectedUser
 */
public User getSelectedUser() {
	return selectedUser;
}

/**
 * @param selectedUser the selectedUser to set
 */
public void setSelectedUser(User selectedUser) {
	this.selectedUser = selectedUser;
}

/**
 * @return the usersList
 */
public List<User> getUsersList() {
	return usersList;
}

/**
 * @param usersList the usersList to set
 */
public void setUsersList(List<User> usersList) {
	this.usersList = usersList;
}

/**
 * @return the userstopiclist
 */
public List<UserTopic> getUserstopiclist() {
	return userstopiclist;
}

/**
 * @param userstopiclist the userstopiclist to set
 */
public void setUserstopiclist(List<UserTopic> userstopiclist) {
	this.userstopiclist = userstopiclist;
}

/**
 * @return the messageService
 */
public MesageService getMessageService() {
	return messageService;
}

/**
 * @param messageService the messageService to set
 */
public void setMessageService(MesageService messageService) {
	this.messageService = messageService;
}

/**
 * @return the loggedUser
 */
public boolean isLoggedUser() {
	return loggedUser;
}

/**
 * @param loggedUser the loggedUser to set
 */
public void setLoggedUser(boolean loggedUser) {
	this.loggedUser = loggedUser;
}

/**
 * @return the selectedUserTopic
 */
public UserTopic getSelectedUserTopic() {
	return selectedUserTopic;
}

/**
 * @param selectedUserTopic the selectedUserTopic to set
 */
public void setSelectedUserTopic(UserTopic selectedUserTopic) {
	this.selectedUserTopic = selectedUserTopic;
}



/**
 * @return the incomingCall
 */
public boolean isIncomingCall() {
	return incomingCall;
}

/**
 * @param incomingCall the incomingCall to set
 */
public void setIncomingCall(boolean incomingCall) {
	this.incomingCall = incomingCall;
}

/**
 * @return the callerIdentifier
 */
public int getCallerIdentifier() {
	return callerIdentifier;
}

/**
 * @param callerIdentifier the callerIdentifier to set
 */
public void setCallerIdentifier(int callerIdentifier) {
	this.callerIdentifier = callerIdentifier;
}

/**
 * @return the videoCallOn
 */
public boolean isVideoCallOn() {
	return videoCallOn;
}

/**
 * @param videoCallOn the videoCallOn to set
 */
public void setVideoCallOn(boolean videoCallOn) {
	this.videoCallOn = videoCallOn;
}

/**
 * @return the formRefresh
 */
public boolean isFormRefresh() {
	return formRefresh;
}

/**
 * @param formRefresh the formRefresh to set
 */
public void setFormRefresh(boolean formRefresh) {
	this.formRefresh = formRefresh;
}

/**
 * @return the ringingCall
 */
public boolean isRingingCall() {
	return ringingCall;
}

/**
 * @param ringingCall the ringingCall to set
 */
public void setRingingCall(boolean ringingCall) {
	this.ringingCall = ringingCall;
}

/**
 * @return the filepath
 */
public String getFilepath() {
	return filepath;
}

/**
 * @param filepath the filepath to set
 */
public void setFilepath(String filepath) {
	this.filepath = filepath;
}

/**
 * @return the audioCall
 */
public boolean isAudioCall() {
	return audioCall;
}

/**
 * @param audioCall the audioCall to set
 */
public void setAudioCall(boolean audioCall) {
	this.audioCall = audioCall;
}



}
