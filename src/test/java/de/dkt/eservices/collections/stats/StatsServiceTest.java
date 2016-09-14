package de.dkt.eservices.collections.stats;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.dkt.eservices.collection.authorities.FindAuthoritiesConfiguration;
import de.dkt.eservices.collection.authorities.FindAuthoritiesService;
import de.dkt.eservices.collection.authorities.model.Document;
import de.dkt.eservices.collection.authorities.model.Entity;
import de.dkt.eservices.collection.stats.StatisticsService;
import de.dkt.eservices.collection.stats.StatsConfig;
import eu.freme.common.FREMECommonConfig;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {StatsConfig.class, FREMECommonConfig.class})
public class StatsServiceTest {

	@Autowired
	StatisticsService ss;
	
	@Test
	public void testFetchStatistics() throws IOException {
		JSONObject json = ss.getEntityStats();
		assertTrue(json != null);
		System.out.println(json);
	}
	
	@Test
	public void testGetNumberOfTriples(){
		Integer count = ss.getNumberOfTriples();
		System.out.println(count);
		assertTrue(count!=null);
	}
	
	@Test
	public void testGetNumberOfContexts(){
		Integer count = ss.getNumberOfContexts();
		System.out.println(count);
		assertTrue(count!=null);
	}
}
