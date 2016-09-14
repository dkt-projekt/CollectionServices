package de.dkt.eservices.collection.authorities;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.dkt.eservices.collection.common.CollectionCommonConfig;
import de.dkt.eservices.collection.common.SparqlService;

@Configuration
@ComponentScan
@Import(CollectionCommonConfig.class)
public class FindAuthoritiesConfiguration {

}
