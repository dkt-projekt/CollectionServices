package de.dkt.eservices.collection.common;

import org.junit.Test;

public class TFIDFServiceTest {

	@Test
	public void testTokenizer(){
		TFIDFService tfidf = new TFIDFService();
		String text = "a b c";
		String[] tokens = tfidf.tokenize(text);
		for( String token : tokens ){
			System.err.println(token);
		}
	}
}
