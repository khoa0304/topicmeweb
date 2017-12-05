package com.topicme.persistence.dao;

import java.util.Arrays;

import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongdbUtilTest {

	@Test
	public void test1(){
		

		MongoCredential credential = MongoCredential.createCredential("user1", "topicme", "welcome1".toCharArray());
		
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017),
                Arrays.asList(credential));
		MongoDatabase mongoDatabase = mongoClient.getDatabase("topicme");
		mongoDatabase.listCollectionNames();
		
	}
	
}
