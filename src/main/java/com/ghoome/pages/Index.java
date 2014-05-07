package com.ghoome.pages;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class Index {
	
	@Persist MongoTemplate template;
	@Property Set<String> dbNames;
	@Property String dbName;
	
	void setupRender() throws IOException{
		if(template == null){
			template = configMongoTemplate();
		}
		
		dbNames = template.getCollectionNames();
	}
	
	private MongoTemplate configMongoTemplate() throws IOException{
		Properties property = new Properties();
		property.load(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("credential.properties"));
		
		return new MongoTemplate(
			new MongoClient(
				property.getProperty("host"),
				Integer.valueOf(property.getProperty("port"))
			),
			property.getProperty("db"),
			new UserCredentials(property.getProperty("username"),
					property.getProperty("password"))
		);
	}
}
