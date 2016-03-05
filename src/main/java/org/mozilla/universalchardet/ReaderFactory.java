package org.mozilla.universalchardet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public final class ReaderFactory {

	private ReaderFactory() {
		throw new AssertionError("No instances allowed");
	}
	
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
	public static Reader createReaderFromFile(File file) throws IOException {
		return createReaderFromFile(file, Charset.defaultCharset());
	}
}
