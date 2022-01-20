package com.aelion.sockjs.configs;

import java.net.URI;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.aelion.sockjs.helpers.HttpHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	private URI allowOrigin;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("/stomp")
			.setAllowedOrigins(allowOrigin.toString())
			.addInterceptors(new HttpHandshakeInterceptor())
			.withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/users");
	}
	
	@Inject
	public void setAllowOrigin( @Value("${allowOrigin}") final URI allowOrigin) {
		this.allowOrigin = Objects.requireNonNull(allowOrigin);
	}
}
