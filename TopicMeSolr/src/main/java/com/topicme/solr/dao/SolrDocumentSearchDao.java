package com.topicme.solr.dao;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topicme.solr.config.SolrServiceConfigServiceImpl;

@Service
public class SolrDocumentSearchDao {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private HttpSolrClient httpsolrClient;
	
	
	@Autowired
	private SolrServiceConfigServiceImpl solrServiceConfigServiceImpl;
	
	@PostConstruct
	public void init(){
		LOGGER.info(getClass().getName());
		httpsolrClient = solrServiceConfigServiceImpl.getSolrClient();
	}
	
	public Collection<SolrDocument>  searchByHtmlContent(String searchString){
		
		SolrQuery query = new SolrQuery();
		query.set("q", "notes_ngram:"+searchString );
		
		QueryResponse response;
		SolrDocumentList docList = new SolrDocumentList();
		
		try {
			response = httpsolrClient.query(query);
			 
			docList = response.getResults();
			
		} catch (SolrServerException | IOException e) {
			LOGGER.error("error searching",e);
		}
		
		
		return docList;
	}
}
