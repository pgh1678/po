package com.sereon.po;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class PoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}

}

class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se){
		se.getSession().setMaxInactiveInterval(60*60*24);
	}
}