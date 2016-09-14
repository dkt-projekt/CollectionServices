package de.dkt.eservices.collection.stats;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.dkt.eservices.collection.authorities.model.Document;

@RestController
public class StatisticsRestController {

	@Autowired
	StatisticsService ss;
	
	@RequestMapping(value = "/stats/entities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadEntities() throws IOException {
		return ss.getEntityStats().toString();
	}	
	
	@RequestMapping(value = "/stats/general", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String loadGeneralStats() throws IOException {
		return ss.getGeneralStats().toString();
	}	
}
