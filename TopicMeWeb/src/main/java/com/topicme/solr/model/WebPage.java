package com.topicme.solr.model;

import org.apache.solr.client.solrj.beans.Field;

public class WebPage {

	private String title;
	private String htmlContent;
	private String mongoDbCollectionName;
	
	
	public String getMongoDbCollectionName() {
		return mongoDbCollectionName;
	}
	
	@Field
	public void setMongoDbCollectionName(String mongoDbCollectionName) {
		this.mongoDbCollectionName = mongoDbCollectionName;
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}
	
	@Field
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
	public String getTitle() {
		return title;
	}
	
	@Field
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
