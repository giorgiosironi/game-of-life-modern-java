package com.giorgiosironi.gameoflife;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import com.giorgiosironi.gameoflife.domain.Cell;
import com.giorgiosironi.gameoflife.domain.Generation;
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
				InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
				Generation original = Generation.withAliveCells(
						Cell.onXAndY(1, 1),
						Cell.onXAndY(1, 2),
						Cell.onXAndY(2, 1),
						Cell.onXAndY(2, 2),
						Cell.onXAndY(7, 1),
						Cell.onXAndY(7, 2),
						Cell.onXAndY(7, 3),
						Cell.onXAndY(7, 8)
				);
				repository.add("a-block-and-a-bar", 0, original);
				bind(repository).to(GenerationRepository.class);
			}
	   	});
	}	    
}
