package de.dkt.eservices.collection.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;

import de.dkt.eservices.collection.authorities.model.Entity;

@Service
public class SparqlService {

	String sparqlEndpoint = "http://dev.digitale-kuratierung.de:8890/sparql?default-graph-uri=http%3A%2F%2Fdigitale-kuratierung.de%2Fns%2Fgraphs%2Fmendelsohn";
	
	Logger logger = Logger.getLogger(SparqlService.class);

	/**
	 * Load a SPARQL query from a text file in folder src/main/resources/sparql.
	 * The text file can contain place holders like $0$, $1$ which will be
	 * replaced by args argument.
	 * 
	 * @param file
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public Query loadQuery(String file, String... args) {
		InputStream is = null;
		try {
			is = this.getClass().getClassLoader()
					.getResourceAsStream("sparql/" + file);
			String queryStr = new String(IOUtils.toByteArray(is));
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				queryStr = queryStr.replaceAll("\\$" + i + "\\$", arg);
			}

			return QueryFactory.create(queryStr);
		} catch (IOException e) {
			logger.error(e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	public QueryExecution createQueryExecution(Query query) {
		return QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
	}

	public QueryExecution createQueryExecution(String file, String... args) {
		Query query = loadQuery(file, args);
		return createQueryExecution(query);
	}

}
