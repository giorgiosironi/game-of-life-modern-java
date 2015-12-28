package com.giorgiosironi.gameoflife;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application implements Runnable {

	private Server server;
	private boolean started;
	private Object startedNotification;
	private static final int PORT = 8080;
	private Logger logger;
	
	public Application() {
		startedNotification = new Object();
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public void run() {	
		logger.info("Starting up");

		synchronized (startedNotification) {
			started = false;
			server = new Server(PORT);
			
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");
			context.addServlet(GameOfLifeServlet.class, "/plane");
			
		    ResourceHandler resource_handler = new ResourceHandler();
		    resource_handler.setDirectoriesListed(true);
		    // how to point to a dynamic page?
		    resource_handler.setWelcomeFiles(new String[]{ "index.html" });	
		    resource_handler.setResourceBase(this.getClass().getResource("/static").toString());
		    
		    HandlerList handlers = new HandlerList();
			handlers.setHandlers(new Handler[] { resource_handler, context });
		    server.setHandler(handlers);
		 	try {
				server.start();
			} catch (Exception e) {
				logger.error("Jetty could not start", e);
				try {
					logger.info("Shutting down immediately");
					server.stop();
					logger.info("Jetty is now stopped");
					return;
				} catch (Exception e1) {
					throw new RuntimeException("Cannot shutdown Jetty", e1);
				}
			}
			started = true;	
			startedNotification.notify();
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			logger.info("Interrupted", e);
		}		
		logger.info("Shutting down cleanly");
	}
	
	public void waitForStartup() throws InterruptedException {
		synchronized (startedNotification) {
			while (!started) {
				this.startedNotification.wait();
			}
		}
	}
	
	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			logger.error("Cannot stop Jetty", e);
		}
	}
	
	public static void main(String args[]) {
		new Application().run();
	}
}
