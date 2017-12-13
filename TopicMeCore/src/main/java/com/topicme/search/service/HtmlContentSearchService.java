package com.topicme.search.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topicme.mongodb.collection.model.PageSearchResultDomain;
import com.topicme.solr.dao.SolrDocumentSearchDao;

@Service
public class HtmlContentSearchService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public enum SEARCH_FIELDS {
		HTML_TITLE,NOTES
	}
	
	
	@Autowired
	private SolrDocumentSearchDao solrDocumentSearchDao;
	
	@PostConstruct
	public void init(){
		LOGGER.info(getClass().getName());
	}
	
	public List<PageSearchResultDomain> searchPage(String query){
		
		Collection<SolrDocument>  results = solrDocumentSearchDao.searchByHtmlContent(query);
		
		List<PageSearchResultDomain> list =  new ArrayList<>(results.size());
		
		for(SolrDocument solrDocument : results){
			
			PageSearchResultDomain pageSearchResultDomain =  new PageSearchResultDomain();
			String notes = (String) solrDocument.getFieldValue(SEARCH_FIELDS.NOTES.name());
			pageSearchResultDomain.setNotes(notes);
			
			String title = (String) solrDocument.getFieldValue(SEARCH_FIELDS.HTML_TITLE.name());
			pageSearchResultDomain.setTitle(title);
			
			list.add(pageSearchResultDomain);
		}
		return list;
	}
}
