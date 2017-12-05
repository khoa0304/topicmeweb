package com.topicme.solr.dao;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.topicme.solr.common.HtmlDocumentFields.HTML_PARSED_OPTIONS;
import com.topicme.solr.common.HtmlDocumentFields.INDEX_FIELDS;
import com.topicme.solr.config.SolrServiceConfigServiceImpl;
import com.topicme.solr.model.WebPageModel;

@Repository
public class SolrDocumentInsertDao {

	private Logger LOGGER = LoggerFactory.getLogger(SolrDocumentInsertDao.class);
	
	private HttpSolrClient httpsolrClient;
	
	
	@Autowired
	private SolrServiceConfigServiceImpl solrServiceConfigServiceImpl;
	
	@PostConstruct
	public void init(){
		LOGGER.info(getClass().getName());
		httpsolrClient = solrServiceConfigServiceImpl.getSolrClient();
	}
	
	
	public void addDocument(WebPageModel webPage){
		
		try {
			 
			httpsolrClient.addBean(webPage);
			httpsolrClient.commit();
	
		} catch (Exception e) {
			
			LOGGER.error("Error Indexing Document",e);
		} 
	}
	
	public void addDocument(Map<HTML_PARSED_OPTIONS,String> htmlPageDataMap,String notes) throws SolrServerException, IOException{
		
		SolrInputDocument doc = new SolrInputDocument();
		
		for(Map.Entry<HTML_PARSED_OPTIONS, String> entry : htmlPageDataMap.entrySet()){
			
			doc.addField(entry.getKey().name(), entry.getValue());
		}
		
		doc.addField(INDEX_FIELDS.NOTES.name(), notes);
		
		httpsolrClient.add(doc);
		httpsolrClient.commit();
		
	}
	

	
	public void deleteDocument(String query) throws SolrServerException, IOException{
		
		httpsolrClient.deleteByQuery(query);
		httpsolrClient.commit();
	}
}
