package com.giorgiosironi.gameoflife.web;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.server.mvc.Viewable;

@Path("hello")
public class HelloResource {
	@GET
	@Produces("text/plain")
	public Viewable getHello() {
	    return new Viewable("/hello.ftl", new HashMap<String,Object>());
	}
}
