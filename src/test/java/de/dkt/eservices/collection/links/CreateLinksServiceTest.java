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

import de.dkt.eservices.collection.common.CollectionCommonConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {CollectionCommonConfig.class, LinksConfig.class})
public class CreateLinksServiceTest {

	@Autowired
	FindLinksService service;
	
	@Test
	public void test(){
		List<String> list = new ArrayList<String>();
		list.add("Klaus");
		list.add("Plaus");
		list.add("Claus");
		List<Set<String>> sim = service.editDistanceCandidates(list, 0.2);
		assertTrue(sim.size()==1);
		assertTrue(sim.get(0).size()==3);
	}
}
