package com.topicme.solr.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


@Service
@PropertySource({
	"classpath:solr-config.properties"
	//,"classpath:db.properties" //if same key, this will 'win'
})
public class SolrServiceConfigServiceImpl {

	@Value("${sorl.url}")
	private String sorlServerUrl;

	private HttpSolrClient solrClient = null;
	
	public HttpSolrClient getSolrClient(){
		
		if(solrClient != null){
			return solrClient;
		}
		
		HttpSolrClient solrClient = new HttpSolrClient.Builder(sorlServerUrl).build();
		solrClient.setParser(new XMLResponseParser());
		
		return solrClient;
	}
}
