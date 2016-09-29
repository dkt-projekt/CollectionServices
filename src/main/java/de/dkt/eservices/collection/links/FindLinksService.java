package de.dkt.eservices.collection.links;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.dkt.eservices.collection.common.SparqlService;

@Service
public class FindLinksService {

	@Autowired
	SparqlService ss;

	public HashMap<String, HashMap<String, Integer>> getEntities() {

		QueryExecution qexec = null;
		HashMap<String, HashMap<String, Integer>> data = new HashMap<String, HashMap<String, Integer>>();
		try {
			qexec = ss
					.createQueryExecution("fetch-entities-create-links.sparql");
			ResultSet res = qexec.execSelect();
			while (res.hasNext()) {
				QuerySolution qs = res.next();
				
				String type = qs.getResource("class").getURI();
				String text = qs.getLiteral("text").getString();
				text = text.replaceAll("\\s+", " ");
				Integer count = qs.getLiteral("count").getInt();
				
				if( data.get(type) == null ){
					data.put(type, new HashMap<String,Integer>());
				}
				
				data.get(type).put(text, count);
			}
		} finally {
			if (qexec != null) {
				qexec.close();
			}
		}
		
		return data;
	}
	
	public List<Set<String>> editDistanceCandidates(List<String> inputData, Double threshold){
		
		List<Set<String>> candidates = new ArrayList<Set<String>>();
		LevenshteinDistance ld = new LevenshteinDistance(3);
		
		for( int i=0; i<inputData.size(); i++ ){
			String word1 = inputData.get(i);
			if( word1.length() <= 2 ) continue;
			Set<String> set = new HashSet<String>();
			for( int j=i+1; j<inputData.size(); j++ ){
				String word2 = inputData.get(j);
				if( word2.length() <= 2 ) continue;
				
				Double dist = new Double(ld.apply(word1, word2));
				dist /= Math.max(word1.length(), word2.length());
				if( dist>=0 && dist<=threshold ){
					set.add(word1);
					set.add(word2);
				}
			}
			if( set.size()>0 ){
				candidates.add(set);
			}
		}
		
		return candidates;
	}
}
