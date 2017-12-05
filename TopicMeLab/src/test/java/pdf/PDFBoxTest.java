package pdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFBoxTest {

	Logger logger = LoggerFactory.getLogger(PDFBoxTest.class);

	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;

	private String text;
	//private String filePath = "D:/OpenSource/lucene/Manning.Lucene.in.Action.2nd.Edition.Jun.2010.MEAP.pdf";
	private String filePath ="D:/KhoaBackupDoNoDelete/EBooks-2016-03-08/EBooks/Lucene 4 Cookbook.pdf";
	private File file;

	public static final String indexPath = "D:/OpenSource/lucene/index";
	
	
	private BufferedWriter writer = null;

	private Document doc;
	
	private IndexWriter indexWriter;
	
	@Test
	public void parsePdfTest1() throws IOException {

		try {

			long startTime = System.currentTimeMillis();

	

			file = new File(filePath);
			parser = new PDFParser(new RandomAccessFile(file, "r")); 
			
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			int numberOfPages = pdDoc.getNumberOfPages();
			
			String lineSeperator = pdfStripper.getLineSeparator();
			int endPage = 0;

			Path path = Paths.get("D:/OpenSource/lucene/"+file.getName()+".txt");
			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,StandardOpenOption.CREATE);

			System.out.println("Indexing to directory '" + indexPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			
			// Add new documents to an existing index:
			iwc.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(dir, iwc);
			
		
			for (int startPage = 1; startPage < numberOfPages ; startPage++) {

				doc = new Document();
				
				FieldType type = new FieldType();
		        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		        type.setStored(true);
		        type.setStoreTermVectors(true);
		        type.setTokenized(true);
		        type.setStoreTermVectorOffsets(true);
		
				doc.add(new StringField("title", file.getName(), Store.YES));
				
				endPage = startPage;
				pdfStripper.setStartPage(startPage);
				pdfStripper.setEndPage(endPage);

				text = pdfStripper.getText(pdDoc);

				String[] lines = text.split(lineSeperator);
				writer.newLine();
				writer.write("Page "  + startPage);
				writer.newLine();

				String pageRange =" Start Page: "+ startPage + " End page: "+ endPage;
				
				doc.add(new StringField("page", pageRange, Store.YES));
				
				
				StringBuilder pageContent = new StringBuilder();
				for (String line : lines) {
					line = line.trim();
					if(line.length() == 0||
						line.indexOf("Please post comments")!=-1 ||
						line.indexOf("http://www.manning-sandbox.com/forum.jspa?forumID=451")!=-1) continue;
					
					doc.add(new Field("line", line,type));
					
					
					pageContent.append(line);
					
					writer.write(line);
					writer.newLine();
				}
				
				doc.add(new TextField("contents", pageContent.toString(), Store.YES));
				
				indexWriter.updateDocument(new Term("page",pageRange), doc);
			
			}

		
			
			long elapseTime = System.currentTimeMillis() - startTime;

			logger.info("Finished parsing : " + endPage + " in  " + elapseTime +" milliseconds.");

		} catch (IOException e) {

			System.err.println(e);
			
		} finally {

			if (writer != null) {

				writer.flush();
				writer.close();

			}
			cosDoc.close();
			pdDoc.close();
			
			if(doc != null){
			
				doc.clear();
				doc = null;
			}
			
			if(indexWriter != null ){
				indexWriter.flush();
				indexWriter.commit();
				indexWriter.close();
			}
		}

	}

}
