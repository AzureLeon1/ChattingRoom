package common;

import java.io.Serializable;

/**
 * 发送的消息类
 * @author Leon
 *
 */
public class ChatMessage implements Serializable{
	private String MessageCode;
	private String Content;
	private String Time;
	private String Sender;
	private String Getter;
	
	public ChatMessage(){
		
	}
	
	public ChatMessage(String MessageCode,String Content,String Time,String Sender,String Getter){
		this.MessageCode = MessageCode;
		this.Content = Content;
		this.Time = Time;
		this.Sender = Sender;
		this.Getter = Getter;
	}

	public String getMessageType() {
		return MessageCode;
	}

	public void setMessageType(String messageType) {
		MessageCode = messageType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getGetter() {
		return Getter;
	}

	public void setGetter(String getter) {
		Getter = getter;
	}
	
	
}
