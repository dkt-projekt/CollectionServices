package de.dkt.eservices.collection.stats;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.dkt.eservices.collection.common.CollectionCommonConfig;

@Configuration
@ComponentScan
@Import(CollectionCommonConfig.class)
public class StatsConfig {
	
}
