package lucene.basic.search;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.testng.annotations.Test;

import lucene.sent.cache.index.SENT_CACHE_COLUMN;

public class SentDimensionKeysIndexTest {

	public static final String ACCOUNT_ID_FIELD ="ACCOUNT_ID";
	public static final String RIID_FIELD ="RIID";
	public static final String LAUNCH_KEY_FIELD ="LAUNCH_KEY";
	public static final String PERSONALIZATION_TS_LONG_FIELD ="PERSONALIZATION_TS_LONG";
	
	@Test
	public void testIndexSentDimensionKey() throws IOException{

		long startTime = System.currentTimeMillis();
		
		String indexPath = "D:/OpenSource/lucene/sentdimensionindex/Feb_20_2017";
		
		Directory dir = FSDirectory.open(Paths.get(indexPath));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		int numberOfAccount = 50;
		
		for(int i = 1; i <= numberOfAccount; i++){
		
			
			
			Field accountIdField = new StringField(ACCOUNT_ID_FIELD, String.valueOf(i), Field.Store.YES);
			
	
			
			for(int ii = 1; ii <= numberOfAccount;ii++){
				
				Field riidField = new StringField(RIID_FIELD, String.valueOf(ii), Field.Store.YES);
						
			
				
				for(int  iii = 1; iii <= numberOfAccount; iii++){
			
					Document doc = new Document();
					doc.add(accountIdField);
					doc.add(riidField);
					
					Field launchKey = new StringField(LAUNCH_KEY_FIELD, String.valueOf(iii), Field.Store.YES);
					doc.add(launchKey);
				
					Field tsInLong = new StringField(PERSONALIZATION_TS_LONG_FIELD, String.valueOf(i), Field.Store.YES);
					doc.add(tsInLong);
					
				
					for(SENT_CACHE_COLUMN sentDimensionKeyCol : SENT_CACHE_COLUMN.values()){
						
						
						Field dimensionKey = new StringField(sentDimensionKeyCol.name(), String.valueOf(System.currentTimeMillis()), Field.Store.YES);
						doc.add(dimensionKey);
						
					}
					writer.addDocument(doc);
				}
				
			}
		}
		
		
		writer.flush();
		writer.commit();
		writer.close();
		
		long elapseTime = System.currentTimeMillis()-startTime;
		
		System.out.print("Took: " + TimeUnit.MILLISECONDS.toSeconds(elapseTime) +" seconds.");

	}
}
