package de.dkt.eservices.collection.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import de.dkt.eservices.collection.common.TFIDFService;
import de.dkt.eservices.collection.common.TfIdfEntry;

@RestController
public class TFIdfRest {

	@Autowired
	TFIDFService tfIdfService;
	
	@RequestMapping(value = "/collections/tfidf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTop10(@RequestParam("uri") String uri){
		HashMap<String,Double> tfidf = tfIdfService.getTfIdf(uri);
		List<TfIdfEntry> list = tfIdfService.getTopTfIdf(tfidf, 10);
		return new Gson().toJson(list);
	}
}
