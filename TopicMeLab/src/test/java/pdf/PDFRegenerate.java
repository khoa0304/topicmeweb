//package pdf;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.Field.Store;
//import org.apache.lucene.document.FieldType;
//import org.apache.lucene.document.IntPoint;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.IndexOptions;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexWriterConfig.OpenMode;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.pdfbox.cos.COSDocument;
//import org.apache.pdfbox.io.RandomAccessFile;
//import org.apache.pdfbox.pdfparser.PDFParser;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDDocumentInformation;
//import org.apache.pdfbox.text.PDFTextStripper;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.testng.annotations.Test;
//
//public class PDFRegenerate {
//
//	Logger logger = LoggerFactory.getLogger(PDFRegenerate.class);
//
//	private PDFParser parser;
//	private PDFTextStripper pdfStripper;
//	private PDDocument pdDoc;
//	private COSDocument cosDoc;
//
//	private String text;
//	//private String filePath = "D:/OpenSource/lucene/Manning.Lucene.in.Action.2nd.Edition.Jun.2010.MEAP.pdf";
//	private String filePath ="D:/KhoaBackupDoNoDelete/EBooks-2016-03-08/EBooks/Lucene 4 Cookbook.pdf";
//	private File file;
//
//	public static final String indexPath = "D:/OpenSource/lucene/index";
//	
//	
//	private BufferedWriter writer = null;
//
//	
//	@Test
//	public void parsePdfTest1() throws IOException {
//
//		try {
//
//			long startTime = System.currentTimeMillis();
//
//			this.pdfStripper = null;
//			this.pdDoc = null;
//			this.cosDoc = null;
//
//			file = new File(filePath);
//			parser = new PDFParser(new RandomAccessFile(file, "r")); 
//			
//			parser.parse();
//			cosDoc = parser.getDocument();
//			pdfStripper = new PDFTextStripper();
//			pdDoc = new PDDocument(cosDoc);
//			int numberOfPages = pdDoc.getNumberOfPages();
//			
//			PDDocumentInformation info = pdDoc.getDocumentInformation();
//			String title = info.getTitle();
//			
//			System.out.println("Title : " + title );
//			
//			String lineSeperator = pdfStripper.getLineSeparator();
//			int endPage = 0;
//
//			Path path = Paths.get("D:/OpenSource/lucene/"+file.getName());
//			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,StandardOpenOption.CREATE);
//
//			System.out.println("Indexing to directory '" + indexPath + "'...");
//
//			
//		
//			for (int startPage = 1; startPage < numberOfPages ; startPage++) {
//
//				PDDocument document = new PDDocument();
//				
//				endPage = startPage;
//				pdfStripper.setStartPage(startPage);
//				pdfStripper.setEndPage(endPage);
//
//				document.addPage(pdfStripper.getCurrentPage().);
//				text = pdfStripper.getText(pdDoc);
//
//				String[] lines = text.split(lineSeperator);
//				writer.newLine();
//				writer.write("Page "  + startPage);
//				writer.newLine();
//
//				String pageRange =" Start Page: "+ startPage + " End page: "+ endPage;
//				
//				
//				
//				StringBuilder pageContent = new StringBuilder();
//				for (String line : lines) {
//					line = line.trim();
//					if(line.length() == 0||
//						line.indexOf("Please post comments")!=-1 ||
//						line.indexOf("http://www.manning-sandbox.com/forum.jspa?forumID=451")!=-1) continue;
//					
//					
//					
//					pageContent.append(line);
//					
//					writer.write(line);
//					writer.newLine();
//				}
//				
//			
//			}
//
//		
//			
//			long elapseTime = System.currentTimeMillis() - startTime;
//
//			logger.info("Finished parsing : " + endPage + " in  " + elapseTime +" milliseconds.");
//
//		} catch (IOException e) {
//
//			System.err.println(e);
//			
//		} finally {
//
//			if (writer != null) {
//
//				writer.flush();
//				writer.close();
//
//			}
//			cosDoc.close();
//			pdDoc.close();
//		}
//
//	}
//
//}
