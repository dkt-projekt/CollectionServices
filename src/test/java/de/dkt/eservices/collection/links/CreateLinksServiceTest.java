package de.dkt.eservices.collection.links;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.dkt.eservices.collection.authorities.FindAuthoritiesConfiguration;
import de.dkt.eservices.collection.common.CollectionCommonConfig;
import eu.freme.common.FREMECommonConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {CollectionCommonConfig.class, LinksConfig.class})
public class CreateLinksServiceTest {

	@Autowired
	FindLinksService service;
	
	@Test
	public void test(){
		
		HashMap<String,HashMap<String,Integer>> entities = service.getEntities();
		ArrayList<String> persons = new ArrayList<String>();
		
		HashMap<String,Integer> personCounts = entities.get("http://dbpedia.org/ontology/Person");
		persons.addAll(personCounts.keySet());
		
		List<Set<String>> candidates = service.editDistanceCandidates(persons, 0.2);
		for( Set<String> set : candidates ){
			System.out.println(set);
		}
		
		
//		int i=0;
//		Iterator<String> itr = persons.keySet().iterator();
//		while(i++<10 && itr.hasNext() ){
//			System.out.println(itr.next());
//		}
	}
}
