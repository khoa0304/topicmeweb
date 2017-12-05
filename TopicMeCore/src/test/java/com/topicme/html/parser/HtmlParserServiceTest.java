package com.topicme.html.parser;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.topicme.solr.common.HtmlDocumentFields.HTML_PARSED_OPTIONS;



public class HtmlParserServiceTest {

	private ClassPathXmlApplicationContext context;
	
	@Before
	public void setup(){
		
		context = new ClassPathXmlApplicationContext("topicme-backend-context.xml");
		Assert.assertNotNull(context);
		
	}
	
	@Test
	public void test1() throws IOException, SAXException, TikaException{
		
		HtmlParserService hmHtmlParserService = (HtmlParserService) context.getBean(HtmlParserService.class);
		Assert.assertNotNull(hmHtmlParserService);
		
		Map<HTML_PARSED_OPTIONS,String> map = 
				hmHtmlParserService.parseHtmlPage("https://docs.oracle.com/middleware/1212/coherence/COHDG/cache_back.htm#COHDG1378");
		
		for(Map.Entry<HTML_PARSED_OPTIONS, String> entry: map.entrySet()){
			
			String val =  entry.getValue();
			Assert.assertFalse(StringUtils.isEmpty(val));
		}
		
	}
	
	
	@Test
	public void test2() throws IOException, SAXException, TikaException, SolrServerException{
		
		//"http://mongodb.github.io/mongo-java-driver/3.5/driver/getting-started/quick-start/"
		HtmlParserService hmHtmlParserService = (HtmlParserService) context.getBean(HtmlParserService.class);
		Assert.assertNotNull(hmHtmlParserService);
		
		Map<HTML_PARSED_OPTIONS,String> map = 
				hmHtmlParserService.parseAndIndexLink("https://lucene.apache.org/solr/guide/7_1/schemaless-mode.html#schemaless-mode","Spring Authentication");
		
		for(Map.Entry<HTML_PARSED_OPTIONS, String> entry: map.entrySet()){
			
			String val =  entry.getValue();
			Assert.assertNotNull(val,entry.getKey());
		}
		
	}
	
}
