package de.dkt.eservices.collection.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Service
public class TFIDFService {

	@Autowired
	SparqlService ss;
	
	public String[] tokenize(String text){
		return text.toLowerCase().split("[\\s\\.,!?]+");
	}
	
	public HashMap<String,Double> getIDf(){
		HashMap<String,Double> df = new HashMap<String, Double>();
		QueryExecution qexec = null;

		int docCounter=0;
		
		//calculate df
		try{
			qexec = ss.createQueryExecution("fetch-all-contexts-no-limit.txt");
			ResultSet rs = qexec.execSelect();
			while(rs.hasNext()){
				QuerySolution qs = rs.next();
				String text = qs.getLiteral("text").getString();
				String[] tokens = tokenize(text);
				HashSet<String> set = new HashSet<String>();
				for( String token : tokens ){
					set.add(token.trim());
				}
				for( String key : set) {
					Double i = df.get(key);
					if( i==null ){
						i=1.0;
					} else{
						i++;
					}
					df.put(key,  i);
				}
				
				docCounter++;
			}
		} finally{
			if( qexec != null ){
				qexec.close();
			}
		}
		
		// calculate idf
		for( String key : df.keySet() ){
			Double dfi = df.get(key);
			dfi = Math.log(docCounter / dfi);
			df.put(key,  dfi);
		}
		return df;
	}
	
	public HashMap<String,Double> getTfIdf(String docUri){
		QueryExecution qexec = null;
			
		try{
			qexec = ss.createQueryExecution("fetch-text.txt", docUri);
			ResultSet rs = qexec.execSelect();
			if( !rs.hasNext() ){
				return null; 
			} else{
				QuerySolution qs = rs.next();
				String text = qs.getLiteral("text").getString();
				String[] tokens = tokenize(text);
				
				HashMap<String,Integer> tf = new HashMap<String,Integer>();
				for( String token : tokens ){
					token = token.trim();
					Integer i = tf.get(token);
					if( i == null ){
						i=1;
					} else{
						i++;
					}
					tf.put(token,i);
				}
				
				HashMap<String,Double> tfidf = new HashMap<String, Double>();
				HashMap<String,Double> idf = getIDf();
				for( String key : tf.keySet() ){
					Double d = tf.get(key) * idf.get(key);
					tfidf.put(key,  d);
				}
				return tfidf;

			}
		} finally{
			if( qexec != null ){
				qexec.close();
			}
		}
	}
	
	public ArrayList<TfIdfEntry> getTopTfIdf(HashMap<String,Double> tfidf, int n){
		ArrayList<String> sorted = new ArrayList<String>(tfidf.keySet());
		Collections.sort(sorted, new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				Double v1 = tfidf.get(o1);
				Double v2 = tfidf.get(o2);
				return v1.compareTo(v2)*-1;
			}
			
		});
		
		ArrayList<TfIdfEntry> topN = new ArrayList<TfIdfEntry>();
		for( int i=0; i<sorted.size() && i<n; i++ ){
			String word = sorted.get(i);
			topN.add( new TfIdfEntry(word, tfidf.get(word)));
		}
		return topN;
	}
}
