package org.mozilla.universalchardet;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author ian
 * @since  Jul 13, 2011
 *
 */
public class GB18030SMFalsePositiveTest
{
	
	@Test
	@Ignore("not yet fixed")
	public void testFalsePositive() throws UnsupportedEncodingException
	{
		UniversalDetector detector = new UniversalDetector(null);
		byte[] buf = new byte[]{91, -80, 52, -80, 48, -80, 84, -80, 67, -80, 67, -80, 48, -80, 67, -80, 84};
		detector.handleData(buf, 0, buf.length);
		detector.dataEnd();
		
		String encoding = detector.getDetectedCharset();
		detector.reset();
		assertEquals("WINDOWS-1252", encoding);
	}
}