package com.giorgiosironi.gameoflife;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class EmbeddedJettyApplication implements Runnable {

	private Server server;
	private boolean startupFinished;
	private Object startupNotification;
	private static final int PORT = 8080;
	private Logger logger;
	private boolean running = false;
	
	public EmbeddedJettyApplication() {
		startupNotification = new Object();
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public void run() {	
		logger.info("Starting up");

		synchronized (startupNotification) {
			startupFinished = false;
			server = new Server(PORT);

			ServletContextHandler singleServletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
			singleServletContext.setContextPath("/");
			
	        ServletHolder jerseyServlet = singleServletContext.addServlet(ServletContainer.class, "/*");
	        jerseyServlet.setInitParameter(
	        	"javax.ws.rs.Application",
	        	WebApplicationResourceConfig.class.getCanonicalName()
	        );
	    
		    ResourceHandler resourceHandler = new ResourceHandler();
		    resourceHandler.setDirectoriesListed(true);
		    // how to point to a dynamic page?	
		    resourceHandler.setResourceBase(this.getClass().getResource("/static").toString());
		    HandlerList handlers = new HandlerList();
			handlers.setHandlers(new Handler[] { resourceHandler, singleServletContext });
		    server.setHandler(handlers);
		 	try {
				server.start();
			} catch (Exception e) {
				logger.error("Jetty could not start", e);
				try {
					logger.info("Shutting down immediately");
					server.stop();
					logger.info("Jetty is now stopped");
					startupFinished = true;
					startupNotification.notify();
					return;
				} catch (Exception e1) {
					throw new RuntimeException("Cannot shutdown Jetty", e1);
				}
			}
		 	running = true;
			startupFinished = true;	
			startupNotification.notify();
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			logger.info("Interrupted", e);
		}		
		logger.info("Shutting down cleanly");
		running = false;
	}
	
	public void waitForStartup() throws InterruptedException {
		synchronized (startupNotification) {
			while (!startupFinished) {
				this.startupNotification.wait();
			}
		}
	}
	
	public void stop() {
		logger.info("Received request to stop");
		try {
			server.stop();
		} catch (Exception e) {
			logger.error("Cannot stop Jetty", e);
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public static void main(String args[]) {
		
		EmbeddedJettyApplication application = new EmbeddedJettyApplication();
		Signal.handle(new Signal("INT"), new SignalHandler () {
		    public void handle(Signal sig) {
		        application.stop();
		    }
		});
		application.run();
	}
}
