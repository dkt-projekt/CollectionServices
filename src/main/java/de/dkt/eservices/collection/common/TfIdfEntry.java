package de.dkt.eservices.collection.common;

public class TfIdfEntry {

	String word;
	Double tfidf;
	public TfIdfEntry(String word, Double tfidf) {
		super();
		this.word = word;
		this.tfidf = tfidf;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Double getTfidf() {
		return tfidf;
	}
	public void setTfidf(Double tfidf) {
		this.tfidf = tfidf;
	}
	
	
}
