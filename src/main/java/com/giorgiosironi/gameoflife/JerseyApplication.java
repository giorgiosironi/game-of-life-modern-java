package com.giorgiosironi.gameoflife;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.giorgiosironi.gameoflife.web.PingResource;

public class JerseyApplication {
	
    private static final URI BASE_URI = URI.create("http://localhost:8080/");


	public static void main(String[] args) {
        final ResourceConfig resourceConfig = new ResourceConfig(PingResource.class);
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdownNow();
            }
        }));
        try {
			server.start();
			System.out.println("Application started.\nStop the application using CTRL+C");
	        Thread.currentThread().join();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
