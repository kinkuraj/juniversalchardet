package org.mozilla.universalchardet;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Stream that detect encoding while reading.
 * The normal usage is to fully read from inputstream and call close before check for charset.
 *
 */
public class EncodingDetectorOutputStream extends OutputStream {
	private OutputStream out;
	private final UniversalDetector detector = new UniversalDetector(null);
	
	public EncodingDetectorOutputStream(OutputStream out) {
		super();
		this.out = out;
	}

	public void close() throws IOException {
		out.close();
		detector.dataEnd();
	}



	public void flush() throws IOException {
		out.flush();
	}

	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
		if (!detector.isDone()) {
			detector.handleData(b, off, len);
		}		
		
	}

	public void write(byte[] b) throws IOException {
		this.write(b,0, b.length);
	}

	public void write(int b) throws IOException {
		this.write(new byte[]{(byte) b});
	}
	/**
	 * Gets the detected charset, null if not yet detected.
	 * @return The detected charset
	 */
	public String getDetectedCharset() {
        return detector.getDetectedCharset();
    }
	
}
