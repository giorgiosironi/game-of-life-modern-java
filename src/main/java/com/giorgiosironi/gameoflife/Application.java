package com.giorgiosironi.gameoflife;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Application implements Runnable {

	private Server server;
	private boolean started;
	private Object startedNotification;
	private static final int PORT = 8080;
	
	public Application() {
		startedNotification = new Object();
	}

	public void run() {	
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
				try {
					// TODO: log exception somewhere
					System.out.println("Shutting down immediately");
					server.stop();
					System.out.println("Jetty stopped");
					return;
				} catch (Exception e1) {
					throw new RuntimeException("Cannot shutdown Jetty", e1);
				}
			}
			started = true;	
			startedNotification.notify();
		}
			//server.dumpStdErr();
		try {
			server.join();
		} catch (InterruptedException e) {
			// TODO: log this somewhere
			System.out.println("Application interrupted");
		}
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		new Application().run();
	}
}
