package com.pdf.parser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * 
 * @author kntran
 *
 */
public class PdfParserToTextFile {

	Logger logger = LoggerFactory.getLogger(PdfParserToTextFile.class);

	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;

	private String filePath;

	private BufferedWriter writer = null;
	
	private Path path =null;
	
	private SentenceDetector sentenceDetector;

	public PdfParserToTextFile(String filePath) throws IOException {

		InputStream modelStream = getClass().getResourceAsStream("/OpenNLP/en-sent.bin");
		SentenceModel model = new SentenceModel(modelStream);
		sentenceDetector = new SentenceDetectorME(model);
		
		this.filePath = filePath;
		this.path = parsePDFToTextFile();
		
	
	}

	public Path getTextFilePath(){
		return this.path;
	}
	
	private Path parsePDFToTextFile() throws IOException {
		
		Path path =null;
		
		long startTime = System.currentTimeMillis();
		
		try {

			File file = new File(filePath);
			parser = new PDFParser(new RandomAccessFile(file, "r"));

			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			int numberOfPages = pdDoc.getNumberOfPages();

			int endPage = 0;
			int range = 20;

			path = Paths.get("D:/OpenSource/lucene/" + file.getName() + ".txt");
			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
		
			//final BreakIterator sentIterator = BreakIterator.getSentenceInstance(Locale.US);
			
			StringBuilder sb = new StringBuilder();
			
			for (int index = 1, startPage = 1; startPage < numberOfPages; startPage = endPage + 1, index ++) {

				endPage = index * range;
				pdfStripper.setStartPage(startPage);
				pdfStripper.setEndPage(endPage);

				logger.info("Start page {} - End page: {}", startPage,endPage);
				String text = pdfStripper.getText(pdDoc);
				
				String[] result = sentenceDetector.sentDetect(text);
					
			
				
				for(String sentence : result) {
				
					System.out.println("Before.....: " + sentence);
					
					for(String removeLine : REMOVAL_SENTENCES){
					
						sentence = sentence.replaceAll(removeLine, "");
					}
					
					sentence = sentence.replaceAll("(\\r|\\n)", " ");
					
					if(sentence.length() == 0) continue;
				
					
					sb.append(sentence);
					
					System.out.println("After.....: " + sentence);
					
					if(sentence.length() < 8 ) continue;
					
					
					int pos = sentence.lastIndexOf(Constants.PERIOD);
					int length = sentence.length();
					
					if(pos == length - 1){
						writer.write(sb.toString());
						writer.newLine();
						sb = null;
						sb = new StringBuilder();
					}	
					
				}	
				
//				sentIterator.setText(text);
//				int start = sentIterator.first();
//				int end = -1;
//				
//				StringBuilder sb = new StringBuilder();
//				while ((end = sentIterator.next()) != BreakIterator.DONE) {
//				
//					String sentence = text.substring(start, end).trim();
//					sb.append(sentence);
//					start = end;
//					
//					
//					int pos = sentence.lastIndexOf(Constants.PERIOD);
//					int length = sentence.length();
//					
//					if(pos == length - 1){
//						writer.write(sb.toString());
//						writer.newLine();
//						sb = null;
//						sb = new StringBuilder();
//					}	
//					
//				}	

			}

			long elapseTime = System.currentTimeMillis() - startTime;

			logger.info("Finished parsing : " + endPage + " in  " + elapseTime + " milliseconds.");

		} catch (IOException e) {

			System.err.println(e);

		} finally {

			if (writer != null) {
				writer.flush();
				writer.close();
			}
			
			if(cosDoc != null) cosDoc.close();
			if(pdDoc != null) pdDoc.close();

		}
		
		return path;

	}
	
	public static final Set<String> REMOVAL_SENTENCES = new HashSet<String>(){
		static final long serialVersionUID = 1L;

	{
		add("www.it-ebooks.info");
	}
	};

}
