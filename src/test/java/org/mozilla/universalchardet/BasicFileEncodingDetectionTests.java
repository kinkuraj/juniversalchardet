package org.mozilla.universalchardet;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class BasicFileEncodingDetectionTests {
	
	@Test
	public void testUTF8 () throws IOException {
		Assert.assertEquals("UTF-8", getFileEncoding("utf8.txt"));
	}
	@Test
	public void testUTF8N () throws IOException {
		Assert.assertEquals("UTF-8", getFileEncoding("utf8n.txt"));
	}
	@Test
	public void testUTF16LE () throws IOException {
		Assert.assertEquals("UTF-16LE", getFileEncoding("utf16le.txt"));
	}
	@Test
	public void testShifJis () throws IOException {
		Assert.assertEquals("SHIFT_JIS", getFileEncoding("shiftjis.txt"));
	}
	
	@Test
	public void testEUC () throws IOException {
		Assert.assertEquals("EUC-JP", getFileEncoding("euc.txt"));
	}	
	@Test
	public void testISO2022JP () throws IOException {
		Assert.assertEquals("ISO-2022-JP", getFileEncoding("iso2022jp.txt"));
	}
	
	
	
	
	
	private String getFileEncoding(String testFileName) throws IOException{
        String fileName = "src/test/resources/" + testFileName;
        return UniversalDetector.detectCharset(new File(fileName));
	}
}
