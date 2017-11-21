package lucene.basic.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.testng.annotations.Test;


public class SearchTest {

	public static final String[][] contents = new String[][]{
			
		{"f","f"},
		{"b","b"},
		{"c","c"},
		{"d","d"},
		{"e","e"}
	};
	
	@Test(enabled=false)
	public void testBasicSearch() throws IOException, ParseException {

		String index = IndexTest.indexPath;
		String field = "contents";
		
		int hitsPerPage = 10;
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		
		QueryParser parser = new QueryParser(field, analyzer);
		
		while (true) {
		
	
		    System.out.println("\nEnter query: ");
			String line = in.readLine();

			if (line == null || line.length() == -1) {
				break;
			}

			line = line.trim();
			if (line.length() == 0) {
				break;
			}

			line = String.format("\"%s\"", line);
			Query query = parser.parse(line);
//			String[] queriedTerm = line.split(" ");
//			
//			PhraseQuery query = new PhraseQuery(field,queriedTerm);
//			
			System.out.println("Searching for: " + query.toString(field));

			TopDocs results = searcher.search(query, 40 * hitsPerPage);
			ScoreDoc[] hits = results.scoreDocs;

			int numTotalHits = results.totalHits;
			System.out.println(numTotalHits + " total matching documents");
			
			for(int i = 0 ; i < hits.length; i++){
		
				Document doc = searcher.doc(hits[i].doc);
			
				String title = doc.get("title");
				String lineContext = doc.get("contents");
				String lineContent = doc.getField("line").stringValue();
				
				String page = doc.get("page");
				System.out.println("Title: "+ title + " Page "+ page +" Content : "+ lineContext );
				System.out.println("Line Content: "+lineContent);
				
				
				IndexableField[] indexableFields = doc.getFields("line");
				
				for(IndexableField indexableField : indexableFields){
					System.out.println(indexableField.stringValue());
				}
			}
		
		}
		reader.close();
	}

	
	
	@Test(enabled=true)
	public void testQueryHighlighter(){
     
		try {
        	
        	String index = IndexTest.indexPath;
    		String field = "contents";
    		IndexReader idxReader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    		IndexSearcher idxSearcher = new IndexSearcher(idxReader);
    		Analyzer analyzer = new StandardAnalyzer();
    		
    		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    		
    		
    		while (true) {
    			
    			
    		    System.out.println("\nEnter query: ");
    			String line = in.readLine();

    			if (line == null || line.length() == -1) {
    				break;
    			}

    			line = line.trim();
    			if (line.length() == 0) {
    				break;
    			}
    		   
    			Query queryToSearch = new QueryParser(field, analyzer).parse(line);
	            TopDocs hits = idxSearcher.search(queryToSearch, idxReader.maxDoc());
	            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
	            Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(queryToSearch));
	 
	            System.out.println("reader maxDoc is " + idxReader.maxDoc());
	            System.out.println("scoreDoc size: " + hits.scoreDocs.length);
	       
	            for (int i = 0; i < hits.totalHits; i++) {
	            
	            	int id = hits.scoreDocs[i].doc;
	                System.out.println("doc id : " + i);
	               // highLight(id, idxSearcher, idxSearcher, field, highlighter,analyzer);    
	            
	            }
	            
	           
    		}
    		
           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
//	public void highLight(int id, IndexSearcher idxSearcher, IndexSearcher searcher , String field, Highlighter highlighter,Analyzer analyzer) {
//     
//		try {
//            Document doc = idxSearcher.doc(id);
//            
//            String page = doc.get("page");
//            System.out.println("Page: "+ page);
//            String text = doc.get(field);
//         //   TokenStream tokenStream = TokenSources.getAnyTokenStream(idxReader, id, field, analyzer);
//           
//            TokenStream tokenStream = TokenSources.get(field, searcher.getIndexReader().getTermVectors(id),
//                    searcher.doc(id).get(field), analyzer, -1);
//
//            TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 4);
//            for (int j = 0; j < frag.length; j++) {
//                if ((frag[j] != null) && frag[j].getScore() > 0) {
//                    System.out.println("score: " + frag[j].getScore() + ", frag: " + (frag[j].toString()));
//                }
//            }
//            
//          
//            
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InvalidTokenOffsetsException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//	}
	

	
}
