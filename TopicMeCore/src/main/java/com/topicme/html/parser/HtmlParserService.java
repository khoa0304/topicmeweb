package com.topicme.html.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.topicme.solr.common.HtmlDocumentFields.HTML_PARSED_OPTIONS;
import com.topicme.solr.dao.SolrDocumentInsertDao;

@Service
public class HtmlParserService {

	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	
	private Logger LOGGER = LoggerFactory.getLogger(HtmlParserService.class);
	
	@PostConstruct
	public void init(){
		LOGGER.info(getClass().getName());
	}
	
	@Autowired
	private SolrDocumentInsertDao solrDocumentInsertDao;
	
	public Map<HTML_PARSED_OPTIONS,String> parseAndIndexLink(String link, String notes) throws IOException, SAXException, TikaException, SolrServerException{
		
		Map<HTML_PARSED_OPTIONS,String> map = parseHtmlPage(link);
		solrDocumentInsertDao.addDocument(map,notes);
		return map;
		
	}
	
	
	/**
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public Map<HTML_PARSED_OPTIONS,String> parseHtmlPage(String urlString) throws IOException, SAXException, TikaException{
		
		Map<HTML_PARSED_OPTIONS,String> map = new HashMap<>();
		
		int timeout = 600000;
		
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("User-Agent", "Mozilla/4.76"); 
		conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"); 
		conn.setConnectTimeout(timeout);
		
		InputStream input = null;
		
		try{
			input = conn.getInputStream();	
		}catch(Exception e){
			
			final WebClient webClient = new WebClient();
			
			if(urlString.indexOf("https://")!=-1){
				webClient.getOptions().setUseInsecureSSL(true);
			}
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setTimeout(timeout);
			final HtmlPage page = webClient.getPage(urlString);
			String pageAsText = page.asText();
			input = IOUtils.toInputStream(pageAsText, "UTF-8");	
		}
		
		if(input == null) return new HashMap<>(0);
		
	    
		LinkContentHandler linkHandler = new LinkContentHandler();
		BodyContentHandler textHandler = new BodyContentHandler(-1);
		ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
		TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
	
		Metadata metadata = new Metadata();
		
		ParseContext parseContext = new ParseContext();
		//HtmlParser parser = new HtmlParser();
		Parser parser = new AutoDetectParser();
		parser.parse(input, teeHandler, metadata, parseContext);
		
		String htmlTextContent = textHandler.toString().replaceAll("(\n)+", "\n");
		
		map.put(HTML_PARSED_OPTIONS.HTML_TITLE, metadata.get(TITLE));
		map.put(HTML_PARSED_OPTIONS.HTML_METADATA_DESCRIPTION, metadata.get(DESCRIPTION));
		map.put(HTML_PARSED_OPTIONS.HTML_TEXT_CONTENT,htmlTextContent);
		map.put(HTML_PARSED_OPTIONS.HTML_CONTENT, toHTMLHandler.toString());
		
		LOGGER.info(identifyLanguage(metadata.get(TITLE)));
		
		return map;
		
	}
	
	
	public String identifyLanguage(String text) throws IOException {
		LanguageDetector detector = new OptimaizeLangDetector().loadModels();
        LanguageResult result = detector.detect(text);
        return result.getLanguage();
	}
}
