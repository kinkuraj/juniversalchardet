package org.mozilla.universalchardet;

import java.io.IOException;
import java.io.InputStream;

public class EncodingDetectorInputStream extends InputStream {

	private InputStream in;
	private final UniversalDetector detector = new UniversalDetector(null);

	public EncodingDetectorInputStream(InputStream in) {
		this.in = in;
	}

	public int available() throws IOException {
		return in.available();
	}

	public void close() throws IOException {
		in.close();
	}

	public void mark(int arg0) {
		in.mark(arg0);
	}

	public boolean markSupported() {
		return in.markSupported();
	}

	public int read() throws IOException {
		byte[] data = new byte[1];
		int nrOfBytesRead = this.read(data, 0, 1);
		if (nrOfBytesRead >= 0){
			return data[0];
		}
		return -1;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		final int nrOfBytesRead = in.read(b, off, len);
		if (!detector.isDone() && nrOfBytesRead > 0) {
			detector.handleData(b, off, nrOfBytesRead);
		}
		if (nrOfBytesRead == -1) {
			detector.dataEnd();
		}
		return nrOfBytesRead;
	}

	public int read(byte[] b) throws IOException {
		return this.read(b, 0, b.length);
	}

	public void reset() throws IOException {
		in.reset();
	}

	public long skip(long n) throws IOException {
		if (detector.isDone()) {
			return in.skip(n);
		}
		else {
			int lastRead = 0;
			long count = -1;
			for (long i = 0; i < n && lastRead >= 0; i++) {
				lastRead = this.in.read();
				count++;
			}		
			return count;
		}
	}

	public String getDetectedCharset() {
		return detector.getDetectedCharset();
	}

}
