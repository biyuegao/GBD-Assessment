package com.platform.cloud.xpmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class XpManagementApplication {

	private static final String USER_TIMEZONE = "user.timezone";
	private static final String ASIA_SINGAPORE = "Asia/Singapore";

	public static void main(String[] args) {
		System.setProperty(USER_TIMEZONE, ASIA_SINGAPORE);
		TimeZone.setDefault(null);
		SpringApplication.run(XpManagementApplication.class, args);
	}

}
