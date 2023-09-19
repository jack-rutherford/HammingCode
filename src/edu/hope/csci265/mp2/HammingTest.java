//------------------------------------------------------------------------
//
// JUnit for testing Machine Problem 2 on Hamming Code
// 
// Mike Jipping, Jan 2018

package edu.hope.csci265.mp2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HammingTest {
	
	final boolean ODDPARITY = true;
	final boolean EVENPARITY = false;
	
	Hamming hammy = new Hamming();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInstantiation() {
		assertNotNull(hammy);
	}
	
	private boolean equal(byte[] bits, String bitstring) {
		if (bits.length != bitstring.length()) return false;
		
		for (int i=0; i<bits.length; i++) {
			int bit = Integer.parseInt(""+bitstring.charAt(i));
			if (bits[i] != bit) return false;
		}
		return true;
	}
	
	@Test
	public void testEncode1() {
		byte[] bits1 = hammy.encode('A', ODDPARITY);
		byte[] bits2 = hammy.encode('A', EVENPARITY);
		assert(equal(bits1, "11110000001"));
		assert(equal(bits2, "00100001001"));
	}
	
	@Test
	public void testEncode2() {
		byte[] bits1 = hammy.encode('d', ODDPARITY);
		assert(equal(bits1, "00101000100"));
		byte[] bits2 = hammy.encode('M', EVENPARITY);
		assert(equal(bits2, "01110010101"));
		byte[] bits3 = hammy.encode('!', ODDPARITY);
		assert(equal(bits3, "10001000001"));
		byte[] bits4 = hammy.encode(' ', EVENPARITY);
		assert(equal(bits4, "10011000000"));
	}
	
	@Test
	public void testDecodeNoErrors() {
		byte[] bits1 = {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1};
		char c1 = hammy.decode(bits1, ODDPARITY);
		assert(c1 == 'A');
		byte[] bits2 = {0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0};
		char c2 = hammy.decode(bits2, ODDPARITY);
		assert(c2 == 'd');
		byte[] bits3 = {0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1};
		char c3 = hammy.decode(bits3, EVENPARITY);
		assert(c3 == 'M');
		byte[] bits4 = hammy.encode('T', EVENPARITY);
		char c4 = hammy.decode(bits4, EVENPARITY);
		assert(c4 == 'T');
	}
	
	@Test
	public void testDecodeWithErrors() {
		byte[] bits1 = {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1};
		bits1[2] = 0;
		char c1 = hammy.decode(bits1, ODDPARITY);
		assert(c1 == 'A');
		byte[] bits2 = {0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0};
		bits2[10] = 1;
		char c2 = hammy.decode(bits2, ODDPARITY);
		assert(c2 == 'd');
		byte[] bits3 = {0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1};
		bits3[7] = 0;
		char c3 = hammy.decode(bits3, EVENPARITY);
		assert(c3 == 'M');
		byte[] bits4 = hammy.encode('T', EVENPARITY);
		bits4[6] = (byte)(bits4[6]^1);
		char c4 = hammy.decode(bits4, EVENPARITY);
		assert(c4 == 'T');
	}
	
}