package com.giorgiosironi.gameoflife;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import com.giorgiosironi.gameoflife.domain.GenerationRepository;
import com.giorgiosironi.gameoflife.domain.InMemoryGenerationRepository;

public class WebApplicationResourceConfig extends ResourceConfig {
	
	public WebApplicationResourceConfig() {
	   	packages("com.giorgiosironi.gameoflife.web");
	   	property(MvcFeature.TEMPLATE_BASE_PATH, "templates");
	   	register(FreemarkerMvcFeature.class);
	   	register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(new InMemoryGenerationRepository()).to(GenerationRepository.class);
			}
	   	});
	}	    
}
