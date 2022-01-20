package com.aelion.sockjs.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.aelion.sockjs.models.ChatMessage;
import com.aelion.sockjs.models.ChatMessage.MessageType;

@Controller
public class ChatController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		ChatMessage welcome = new ChatMessage();
		welcome.setSender(chatMessage.getSender());
		welcome.setContent(chatMessage.getSender() + " join the chat");
		welcome.setType(MessageType.CHAT);
		
		// Add username as active user
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		
		return welcome;
	}
	
	@MessageMapping("/chat.welcome")
	@SendTo("/topic/public")
	public ChatMessage welcome(SimpMessageHeaderAccessor headerAccessor) {
		String users = "";
		Map<String, Object> headers = headerAccessor.getSessionAttributes();
		for (Map.Entry<String, Object> entry : headers.entrySet()) {
			users += entry.getValue() + "|";
		}
		ChatMessage welcome = new ChatMessage();
		welcome.setSender("System");
		welcome.setContent(users);
		welcome.setType(MessageType.LIST);
		
		return welcome;
	}
	
	@MessageMapping("/chat.message")
	@SendTo("/topic/public")
	public ChatMessage message(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}
	
	@MessageMapping("/chat.typing")
	@SendTo("/topic/public")
	public ChatMessage typing(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}
	
	@MessageMapping("/u2h")
	public void send(SimpMessageHeaderAccessor headerAccessor, @Payload ChatMessage message) {
		ChatMessage toMessage = new ChatMessage();
		toMessage.setType(MessageType.CHAT);
		toMessage.setContent(message.getContent());
		toMessage.setSender(message.getSender());
		
		simpMessagingTemplate.convertAndSendToUser(message.getRecipient().get(), "/topic/private", toMessage);
	}
}
