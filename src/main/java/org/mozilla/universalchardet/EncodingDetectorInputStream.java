package org.mozilla.universalchardet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Stream that detect encoding while reading.
 * The normal usage is to fully read from inputstream and call close before check for charset.
 *
 */
public class EncodingDetectorInputStream extends InputStream {

	private InputStream in;
	private final UniversalDetector detector = new UniversalDetector(null);

	/**
	 * Create the stream
	 * @param in The InputStream to read from
	 */
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

	/**
	 * Gets the detected charset, null if not yet detected.
	 * @return The detected charset
	 */
	public String getDetectedCharset() {
		return detector.getDetectedCharset();
	}

}
