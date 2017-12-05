package com.topicme.solr.dao;


import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topicme.solr.config.SolrServiceConfigServiceImpl;

public class WebPageRespositoryTest {
 
	private ClassPathXmlApplicationContext context;
	
	@Before
	public void setup(){
		
		context = new ClassPathXmlApplicationContext("topicme-solr-context.xml");
		Assert.assertNotNull(context);
		
	}
	
	@org.junit.Test
	public void test1(){
		
		SolrServiceConfigServiceImpl solrServiceConfigServiceImpl = (SolrServiceConfigServiceImpl) context.getBean(SolrServiceConfigServiceImpl.class);
		Assert.assertNotNull(solrServiceConfigServiceImpl);
		
		HttpSolrClient solrClient = solrServiceConfigServiceImpl.getSolrClient();
		Assert.assertNotNull(solrClient);
		
	}
	
	@Test
	public void testDeleteDocument() throws SolrServerException, IOException{
		
		SolrDocumentInsertDao solrDocumentInsertDao = (SolrDocumentInsertDao) context.getBean(SolrDocumentInsertDao.class);
		Assert.assertNotNull(solrDocumentInsertDao);
				
		solrDocumentInsertDao.deleteDocument("*:*");
	}
	
	
	

 
}
