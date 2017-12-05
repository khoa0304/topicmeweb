package com.topicme.solr.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "WebPage")
public class WebPage {

	@Id
    @Indexed(name = "url", type = "string")
	private String url;
	
	
	@Indexed(name = "title", type = "string")
	private String title;
	
    @Indexed(name = "htmlContent", type = "string")
	private String htmlContent;
	
	@Indexed(name = "mongoDbCollectionName", type = "string")
	private String mongoDbCollectionName;
	
	
	
}
