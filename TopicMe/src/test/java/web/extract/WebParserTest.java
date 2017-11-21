package web.extract;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.bson.Document;
import org.testng.annotations.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.solr.model.WebPage;

public class WebParserTest {

	
	
	@Test
	public void test1() throws IOException, SAXException, TikaException, SolrServerException {

		String urlString = "https://howtodoinjava.com/spring/spring-mvc/spring-mvc-display-validate-and-submit-form-example/";
		URL url = new URL(urlString);
		
		if(urlString.indexOf("https://")!=-1){
			
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		
		con.setConnectTimeout(30000);
		InputStream input = con.getInputStream();
		LinkContentHandler linkHandler = new LinkContentHandler();
		ContentHandler textHandler = new BodyContentHandler();
		ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
		TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
		Metadata metadata = new Metadata();
		ParseContext parseContext = new ParseContext();
		HtmlParser parser = new HtmlParser();
		parser.parse(input, teeHandler, metadata, parseContext);
		
		String title =  metadata.get("title");
		System.out.println("title:\n" + title);
		
//		System.out.println("links:\n" + linkHandler.getLinks());
//		System.out.println("html:\n" + toHTMLHandler.toString());
		
		String htmlTextContent =  textHandler.toString();
		System.out.println("text:\n" +  htmlTextContent);
		
		
		HttpSolrClient solrClient = getSolrClient();
		
		
		WebPage webPage = new WebPage();
		webPage.setHtmlContent(htmlTextContent);
		webPage.setMongoDbCollectionName("Spring");
		webPage.setTitle(title);
		
		
		solrClient.addBean(webPage);
		solrClient.commit();
		
		
	}
	
	
	

	@Test
	public void test3(){
		
		printSearchResult(search(" fundamental challenges companies commonly face"));
		printSearchResult(search("complex many-to-many relationships"));
		
		printSearchResult(search(" Coherence The choice of the removed entries is based on the LRU "));
		
		printSearchResult(search(" relevance"));
	}

	
	@Test
	public void test2() throws SolrServerException, IOException{
		
		HttpSolrClient solrClient = getSolrClient();
		solrClient.deleteById("eda031b7-0b24-4330-9a2d-a4b3962e3017");
		solrClient.commit();
		
	}
	
	
	@Test
	public void testInsertCollection() throws ParseException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);

		MongoDatabase db = mongoClient.getDatabase("mongoDB");
		MongoCollection<Document> documents = db.getCollection("Documents");

		Document  document = new Document();
		document.put("Id", 12345);
		document.put("Name", "abcdefxyz");
		document.put("Age", 25);
		document.put("DateOfBirth", new Date());

		Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse("1981-01-31T17:30:00.000Z");
		document.put("DateOfBirth", date);

		List<String> interests = Arrays.asList( new String[]{ "Reading", "Politics", "Basketball" });
		document.put("Interests", interests);

		BasicDBObject address = new BasicDBObject();
		address.put("addressDetail", "flat no 333, Q Wing, Sita Nagar");
		address.put("area", "Aundh");
		address.put("city", "Pune");
		address.put("pincode", 411017);
		address.put("state", "MAHARASHTRA");
		address.put("country", "INDIA");

		document.put("address", address);

		List<BasicDBObject> booksRead = new ArrayList<BasicDBObject>();
		BasicDBObject book1 = new BasicDBObject();
		book1.put("name", "The Immortals of Meluha");
		book1.put("authorName", "Amish Tripathi");
		book1.put("publishedBy", "Westland Press");
		booksRead.add(book1);

		BasicDBObject book2 = new BasicDBObject();
		book2.put("name", "The Krishna Key");
		book2.put("authorName", "Ashwin Sanghi");
		book2.put("publishedBy", "Westland Ltd");
		booksRead.add(book2);

		BasicDBObject book3 = new BasicDBObject();
		book3.put("name", "Sita: An Illustrated Retelling of Ramayana");
		book3.put("authorName", "Devdutt Pattanaik");
		book3.put("publishedBy", "Mehata Publishing Ltd");
		booksRead.add(book3);

		document.put("BooksRead", booksRead);
		documents.insertOne(document);

		mongoClient.close();
	}
	
	private HttpSolrClient solrClient = null;
	
	private HttpSolrClient getSolrClient(){
		
		if(solrClient != null){
			return solrClient;
		}
		
		String urlString = "http://localhost:8983/solr/websearch";
		HttpSolrClient solrClient = new HttpSolrClient.Builder(urlString).build();
		solrClient.setParser(new XMLResponseParser());
		
		return solrClient;
	}
	
	
	public static Set<String> FIELD_SEARCH = new HashSet<String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add("title".toLowerCase());
		add("mongoDbCollectionName".toLowerCase());
		
	}};
	
	private void printSearchResult(Collection<SolrDocument> docList){

		for (SolrDocument doc : docList) {
		    
			for(String fieldName : doc.getFieldNames()){
			
				
				if(FIELD_SEARCH.contains(fieldName.toLowerCase())){
					System.out.print(" Field Name: " + fieldName +" ");
					System.out.println(doc.getFieldValue(fieldName));
				}
					

			}
		}
	}
	
	private Collection<SolrDocument> search(String searchString) {
		
		HttpSolrClient solrClient = getSolrClient();
		
		SolrQuery query = new SolrQuery();
		query.set("q", "htmlContent:"+searchString);
		
		QueryResponse response;
		SolrDocumentList docList = new SolrDocumentList();
		
		try {
			response = solrClient.query(query);
			 
			docList = response.getResults();
			
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return docList;
	}

}
