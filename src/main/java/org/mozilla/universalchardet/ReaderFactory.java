package org.mozilla.universalchardet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Create a reader from a file with correct encoding
 */
public final class ReaderFactory {

	private ReaderFactory() {
		throw new AssertionError("No instances allowed");
	}
	/**
	 * Create a reader from a file with correct encoding
	 * @param file The file to read from
	 * @param defaultCharset defaultCharset to use if can't be determined
	 */
	public static Reader createReaderFromFile(File file, Charset defaultCharset) throws IOException {
		String detectedEncoding = UniversalDetector.detectCharset(file);
		Charset cs = defaultCharset;
		if (detectedEncoding != null) {
			cs = Charset.forName(detectedEncoding);
		}
		if (!cs.toString().contains("UTF")) {
			return new InputStreamReader(new FileInputStream(file), cs);
		}
		return new InputStreamReader(new UnicodeBOMInputStream(new FileInputStream(file)), cs);
	}
	/**
	 * Create a reader from a file with correct encoding. If charset cannot be determined, 
	 * it uses the system default charset.
	 * @param file The file to read from
	 */
	public static Reader createReaderFromFile(File file) throws IOException {
		return createReaderFromFile(file, Charset.defaultCharset());
	}
}
