package pdf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pdf.parser.PdfParserToTextFile;

public class ParsePDFToTextFileTest {

	
	@Test
	public void testParsePdfToTest() throws IOException{
		
		//PdfParserToTextFile parserToTextFile = new PdfParserToTextFile("D:/KhoaBackupDoNoDelete/EBooks-2016-03-08/EBooks/Lucene 4 Cookbook.pdf");
		
		PdfParserToTextFile parserToTextFile = new PdfParserToTextFile("D:/KhoaBackupDoNoDelete/EBooks-2016-03-08/EBooks/4-1-16-23.pdf");
	
		Assert.assertNotNull(parserToTextFile.getTextFilePath());
		
		Path path = parserToTextFile.getTextFilePath();
		
		File modelFile = new File(path.toUri());
		
			
		
		RandomAccessFile aFile = new RandomAccessFile(modelFile, "r");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(2024);
        
        while(inChannel.read(buffer) > 0)
        {
            buffer.flip();
            for (int i = 0; i < buffer.limit(); i++)
            {
                System.out.print((char) buffer.get());
            }
            buffer.clear(); // do something with the data and clear/compact it.
        }
        inChannel.close();
        aFile.close();
		
	}
}
