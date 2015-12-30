package com.giorgiosironi.gameoflife.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.server.mvc.Viewable;

import com.giorgiosironi.gameoflife.domain.Cell;
import com.giorgiosironi.gameoflife.domain.Generation;
import com.giorgiosironi.gameoflife.view.GenerationWindow;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Path("planes")
public class PlaneSampleResource {
	@GET
	@Path("a-block-and-bar")
	@Produces("text/html")
	public Viewable getFirstGeneration() {
		return getGeneration("0");
	}
	
	@GET
	@Path("a-block-and-bar/generation/{generation}")
	// TODO: content negotiation of JSON
	@Produces("text/html")
	public Viewable getGeneration(@PathParam("generation") String requestedGenerationParameter) {
		Generation current = Generation.withAliveCells(
				Cell.onXAndY(1, 1),
				Cell.onXAndY(1, 2),
				Cell.onXAndY(2, 1),
				Cell.onXAndY(2, 2),
				Cell.onXAndY(7, 1),
				Cell.onXAndY(7, 2),
				Cell.onXAndY(7, 3),
				Cell.onXAndY(7, 8)
		);
		
		// TODO: read from URL
		
			int requestedGeneration;
			if (requestedGenerationParameter != null) {
				requestedGeneration = Integer.parseInt(requestedGenerationParameter);
				for (int i = 1; i <= requestedGeneration; i++) {
					current = current.evolve();
				}
			} else {
				requestedGeneration = 0;
			}
			
			GenerationWindow generationWindow = new GenerationWindow(current);
			Map<String, Object> data = new HashMap<String, Object>();
            data.put("generation", generationWindow);
            data.put("generation_index", requestedGeneration);
          
	    return new Viewable("/generation.ftl", data);
	}
}
