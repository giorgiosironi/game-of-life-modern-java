package com.giorgiosironi.gameoflife;

import org.glassfish.jersey.server.ResourceConfig;

public class MyJerseyApplication  extends ResourceConfig {
	
	public MyJerseyApplication() {
	   	packages("com.giorgiosironi.gameoflife.web");
	}	    
}
