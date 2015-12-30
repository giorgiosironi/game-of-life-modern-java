package com.giorgiosironi.gameoflife;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

public class MyJerseyApplication  extends ResourceConfig {
	
	public MyJerseyApplication() {
	   	packages("com.giorgiosironi.gameoflife.web");
	   	property(MvcFeature.TEMPLATE_BASE_PATH, "templates");
	   	register(FreemarkerMvcFeature.class);
	}	    
}
