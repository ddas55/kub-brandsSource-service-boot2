package com.dd.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseConfiguration  {
	
    private final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);
    
    @Value("${mongodb.host}")
    private String host;
    
    @Value("${mongodb.port}")
    private String port;
    
    @Value("${mongodb.username}")
    private String username;
    
    @Value("${mongodb.database}")
    private String database;
    
    @Value("${mongodb.password}")
    private String password;

    @Profile("dev")
	@Bean
	public MongoDbFactory devMongoDbFactory() {
		MongoDbFactory fact=null;
		try {
			logger.info("************ DEV DatabaseConfiguration.mongo host:" + host + " ,port:" + port
					+ " ,database:" + database + " ,username:" + username
					+ " ,password:" + password );
			
			MongoCredential credential = MongoCredential.createCredential(username.toString(), database, password.toCharArray());
			ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
			MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 
			logger.info("************ DatabaseConfiguration.mongo mongoClient :" +  mongoClient );
			fact =  new SimpleMongoDbFactory(mongoClient, database);
			logger.info("************ DatabaseConfiguration.mongo MongoDbFactory :" +  fact );
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	    return fact;
	}
    
    @Profile("prod")
	@Bean
	public MongoDbFactory prodMongoDbFactory() {
		MongoDbFactory fact=null;
		try {
			logger.info("************ PROD DatabaseConfiguration.mongo host:" + host + " ,port:" + port
					+ " ,database:" + database + " ,username:" + username
					+ " ,password:" + password );
			
			MongoCredential credential = MongoCredential.createCredential(username.toString(), database, password.toCharArray());
			ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
			MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 
			logger.info("************ DatabaseConfiguration.mongo mongoClient :" +  mongoClient );
			fact =  new SimpleMongoDbFactory(mongoClient, database);
			logger.info("************ DatabaseConfiguration.mongo MongoDbFactory :" +  fact );
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	    return fact;
	}


}
