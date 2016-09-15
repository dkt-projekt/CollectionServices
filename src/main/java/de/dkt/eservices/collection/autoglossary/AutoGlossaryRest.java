package de.dkt.eservices.collection.autoglossary;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoGlossaryRest {

	@Autowired
	AutoGlossaryService ags;
	
	@RequestMapping(value = "/glossary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGlossary(){
		HashMap<String,List<String>> map = ags.fetchGlossary();
		return ags.sortGlossary(map).toString();
	}
}
