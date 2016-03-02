package org.mozilla.universalchardet;

import java.io.IOException;
import java.io.OutputStream;

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
	public String getDetectedCharset() {
        return detector.getDetectedCharset();
    }
	
}
