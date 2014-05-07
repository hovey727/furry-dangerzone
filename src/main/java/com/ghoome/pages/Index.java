package com.ghoome.pages;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ghoome.fl.domain.Bjdc500Record;
import com.mongodb.MongoClient;

public class Index {
	private static final SimpleDateFormat SSDF = new SimpleDateFormat("yyyyMMdd");
	
	@Persist MongoTemplate template;
	@Property List<Bjdc500Record> records;
	@Property Bjdc500Record m;
	@Property @Persist String input;
	
	@InjectComponent Zone gzone;
	
	void setupRender() throws IOException{
		if(template == null){
			template = configMongoTemplate();
		}
		
		if(input == null){
			input = SSDF.format(new Date());
		}
		
		refreshRonaldo();
	}
	
	Object onActionFromForm(){
		refreshRonaldo();
		return gzone.getBody();
	}
	
	private void refreshRonaldo(){
		records = template.find(
			new Query(
				Criteria.where("monitorNo").is(Integer.valueOf(input))
			).with(new Sort(Sort.Direction.ASC, "no")).limit(200), 
			Bjdc500Record.class
		);
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
	
	public double scaleDouble(double raw, int width){

		if(!Double.isInfinite(raw) && !Double.isNaN(raw)){
			return new BigDecimal(raw).setScale(width, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return -99;
	}
}
