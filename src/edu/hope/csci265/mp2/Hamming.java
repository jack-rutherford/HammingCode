package edu.hope.csci265.mp2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Hamming {
	public void printBits(byte[] bits) {
        for (int i=0; i<bits.length; i++) System.out.print(bits[i]);
    }
    
    public static void main(String[] args) {
    	Random rand = new Random();
    	ArrayList<String> room = new ArrayList<>();
    	room.add("Will");
    	room.add("Kyle");
    	System.out.println("Original Candidates: ");
    	for(String e : room) {
    		System.out.print(e + " ");
    	}
    	room.remove(rand.nextInt(2));
    	System.out.println("\n\nNew Candidate: ");
    	for(String e : room) {
    		System.out.print(e + " ");
    	}
    	
    	
    	//        final boolean ODDPARITY = true;
//        final boolean EVENPARITY = false;
//        
//        Hamming ham = new Hamming();
//        
//        // Test #1: 'A' should be 00100001001 in even parity
//        byte[] A = ham.encode('A', EVENPARITY);
//        System.out.print("'A' in even parity is ");
//        ham.printBits(A);
//        System.out.println();
//        
//        // Test #2: I should get the character back!
//        char AA = ham.decode(A, EVENPARITY);
//        System.out.println("We should get A back: "+AA);
//        
//        // Test #3: Flipping 1 bit should not matter
//        A[5] = (byte)(A[5]^1);
//        AA = ham.decode(A, EVENPARITY);
//        System.out.println("We should get A back: "+AA);
    }

    public char decode(byte[] array, boolean odd) {
    	byte[] c = new byte[7];
    	byte[] par = new byte[4]; 
    	
//    	reverse(array);
    	
    	int cCount = 0, parCount = 0;
    	for(int i = array.length-1; i >= 0; i--) {
			if(isPowerOfTwo(i+1)) {
				par[parCount] = array[i];
				parCount++;
			}
			else {
				c[cCount] = array[i];
				cCount++;
			}
		}
    	
    	int eight = 0, four = 0, two = 0, one = 0;
		for(int j = array.length-1; j >= 0; j--) {
			int curPos = j+1;
			if(!isPowerOfTwo(curPos) && array[j] == 1) {
				if(curPos >= 8) {
					eight++;
					curPos -= 8;
				}
				if(curPos >= 4) {
					four++;
					curPos-=4;
				}
				if(curPos >= 2) {
					two++;
					curPos-=2;
				}
				if(curPos >= 1) {
					one++;
					curPos-=1;
				}
			}
		}
    	byte cpar[] = new byte[4];
    	
		if(odd == false) {
			cpar[3] = (byte) (eight % 2);
			cpar[2] = (byte) (four % 2);
			cpar[1] = (byte) (two % 2);
			cpar[0] = (byte) (one % 2);
		}
		else{
			cpar[3] = (byte) (Math.abs((eight % 2)-1));
			cpar[2] = (byte) (Math.abs((four % 2)-1));
			cpar[1] = (byte) (Math.abs((two % 2)-1));
			cpar[0] = (byte) (Math.abs((one % 2)-1));
		}
		
//		int result = -1;
//		for(int i = 0; i < cpar.length; i++) {
//			
//		}
		
    	reverse(c);
    	char res = toChar(c);
    	
    	int ch = toDecimal(c);
//    	if(ch == 64) {
//    		return (char)'A';
//    	}
//    	if(ch == 127) {
//    		return (char)'d';
//    	}
    	
		return res;
	}

	public byte[] encode(char aCharacter, boolean odd)  {
		// odd is true
		byte[] c = toBinary(aCharacter);
		reverse(c);
		byte[] result = new byte[11];
		int index = 0;
		
		//store the character in the Hamming array, skip over check bits
		for(int i = result.length-1; i >= 0; i--) { // store in reverse order so check bits work out
			if(!isPowerOfTwo(i+1)) { // make sure the index isn't a check bit position
				result[i] = c[index];
				index++;
			}
		}
		
		int eight = 0, four = 0, two = 0, one = 0;
		for(int j = result.length-1; j >= 0; j--) {
			int curPos = j+1;
			if(!isPowerOfTwo(curPos) && result[j] == 1) {
				if(curPos >= 8) {
					eight++;
					curPos -= 8;
				}
				if(curPos >= 4) {
					four++;
					curPos-=4;
				}
				if(curPos >= 2) {
					two++;
					curPos-=2;
				}
				if(curPos >= 1) {
					one++;
					curPos-=1;
				}
			}
		}
		
		//decide if 
		if(odd == false) {
			result[7] = (byte) (eight % 2);
			result[3] = (byte) (four % 2);
			result[1] = (byte) (two % 2);
			result[0] = (byte) (one % 2);
		}
		else{
			result[7] = (byte) (Math.abs((eight % 2)-1));
			result[3] = (byte) (Math.abs((four % 2)-1));
			result[1] = (byte) (Math.abs((two % 2)-1));
			result[0] = (byte) (Math.abs((one % 2)-1));
		}
		
		return result;
	}
	
	/**
	 * uses mod and division to convert a character to binary
	 * @param ch
	 * @return array of binary represented character
	 */
	public byte[] toBinary(char ch) {
		byte result[] = new byte[7];
		int c = ch; 
		for(int i = result.length-1; i >= 0; i--) {
			result[i] = (byte) (c % 2);
			c = (byte) (c/2);
		}
		return result;
	}
	
	public int toDecimal(byte[] arr) {
		int result = 0;
		for(int i = 0; i < arr.length; i++) {
			result += Math.pow(2, i);
		}
		return result;
	}
	
	public char toChar(byte[] by) {
		int value = 0;
		int c = 0;
		for (int i = by.length-1; i >= 0; i--) {
			if(by[i] == 1) {
				value += Math.pow(2, c);
			}
			c++;
		}
		
		return (char) value;
	}
	
	public boolean isPowerOfTwo(int x)
	{
	    return (x & (x - 1)) == 0;
	}
	
	public void reverse(byte[] a) {
		for(int i = 0; i < a.length / 2; i++)
		{
		    byte temp = a[i];
		    a[i] = a[a.length - i - 1];
		    a[a.length - i - 1] = temp;
		}
	}
	
}
