package de.dkt.eservices.collection.links;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.dkt.eservices.collection.authorities.FindAuthoritiesService;

@RestController
public class FindLinksRest {
	
	@Autowired
	FindLinksService fls;

	/**
	 * Serve UI
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/find-links", method = RequestMethod.GET, produces="application/json")
	public String findLinks(@RequestParam Double threshold, @RequestParam String type)  {
		HashMap<String,Integer> personCounts = fls.getEntities().get(type);
		
		if(personCounts == null){
			return new JSONArray().toString();
		}
		// convert data to json arrays. the arrays are sorted by counts, highest counts on top
		List<String> data = new ArrayList<String>();
		data.addAll(personCounts.keySet());
		
		List<Set<String>> sets = fls.editDistanceCandidates(data, threshold);
		
		List<JSONArray> json = new ArrayList<JSONArray>();
		for(Set<String> set : sets ){
			List<JSONObject> subSet = new ArrayList<JSONObject>();
			for( String str : set ){
				JSONObject j = new JSONObject();
				j.put("word", str);
				j.put("count", personCounts.get(str));
				subSet.add(j);
			}
			
			Collections.sort(subSet, new Comparator<JSONObject>(){
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					Integer i1 = o1.getInt("count");
					Integer i2 = o2.getInt("count");
					return i1.compareTo(i2)*-1;
				}
			});
			JSONArray subSetJson = new JSONArray(subSet);
			json.add(subSetJson);
		}
		
		personCounts = null;
		sets = null;
		
		Collections.sort(json, new Comparator<JSONArray>(){

			@Override
			public int compare(JSONArray o1, JSONArray o2) {
				Integer i1 = o1.getJSONObject(0).getInt("count");
				Integer i2 = o2.getJSONObject(0).getInt("count");
				return i1.compareTo(i2)*-1;
			}
			
		});

		return new JSONArray(json).toString();
	}

}
