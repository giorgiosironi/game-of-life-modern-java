package com.giorgiosironi.gameoflife.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import com.giorgiosironi.gameoflife.domain.Cell;
import com.giorgiosironi.gameoflife.domain.Generation;
import com.giorgiosironi.gameoflife.domain.GenerationRepository;
import com.giorgiosironi.gameoflife.view.GenerationWindow;

@Path("planes")
public class PlaneSampleResource {
	@Context
    UriInfo uriInfo;
	
	@Inject
	GenerationRepository repository;
	
	@GET
	@Path("a-block-and-bar")
	@Produces("text/html")
	public Viewable getFirstGeneration() {
		return getGeneration("a-block-and-bar", 0);
	}
	
	@GET
	@Path("{plane}/generation/{generation}")
	// TODO: content negotiation of JSON
	@Produces("text/html")
	public Viewable getGeneration(
			@PathParam("plane") String plane,
			@PathParam("generation") int currentGenerationIndex
		) {
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
		
		Generation current;
		Generation cached = repository.get(plane, currentGenerationIndex).generation();
		if (cached != null) {
			current = cached;
		} else {
			current = original;
			for (int i = 1; i <= currentGenerationIndex; i++) {
				current = current.evolve();
			}
			repository.add(plane, currentGenerationIndex, current);
		}
		
		Map<String, Object> data = populateTemplateData(plane, current, currentGenerationIndex);
      
	    return new Viewable("/generation.ftl", data);
	}

	private Map<String, Object> populateTemplateData(String plane, Generation current, int currentGeneration) {
		GenerationWindow generationWindow = new GenerationWindow(current);
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("generation", generationWindow);
        data.put("generation_index", currentGeneration);
        Map<String, String> links = new HashMap<>();
        data.put("links", links);
        if (currentGeneration > 0) {
        	links.put(
        		"prev",
        		linkToAGeneration(plane, currentGeneration - 1)
        	);
        }
        links.put(
            "next", 
            linkToAGeneration(plane, currentGeneration + 1)
        );
		return data;
	}

	private String linkToAGeneration(String plane, int generationIndex) {
		return uriInfo.getBaseUriBuilder()
			.path(this.getClass())
			.path(this.getClass(), "getGeneration")
		    .build(plane, generationIndex)
		    .toASCIIString();
	}
}
