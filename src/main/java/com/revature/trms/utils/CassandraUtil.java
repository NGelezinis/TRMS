package com.revature.trms.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

@Component
public class CassandraUtil {
	private static final Logger log = LogManager.getLogger(CassandraUtil.class);	
	
	@Bean
	public CqlSession getSession() {
		log.trace("Establishing connection with Cassandra");
		CqlSession session = null;
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		try {
			session = CqlSession.builder().withConfigLoader(loader).withKeyspace("TRMS").build();
		} catch(Exception e) {
			log.error("Method threw exception: "+e);
			for(StackTraceElement s : e.getStackTrace()) {
				log.warn(s);
			}
			throw e;
		}
		return session;
	}
}
