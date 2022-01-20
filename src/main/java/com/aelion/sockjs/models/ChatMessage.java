package com.aelion.sockjs.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;



public class ChatMessage implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
	private String content;
	
	private String sender;
	
	private Date date;
	
	private String recipient;
	
	private MessageType type;
	
	public enum MessageType {CHAT, LEAVE, JOIN, TYPING, LIST}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public MessageType getType() {
		return type;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public Optional<String> getRecipient() {
		Optional<String> oRecipient;
		if (this.recipient != null) {
			oRecipient = Optional.of(this.recipient);
		} else {
			oRecipient = Optional.empty();
		}
		
		return oRecipient;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}
	
	public void setChatType() {
		this.type = MessageType.CHAT;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
}
