package com.giorgiosironi.gameoflife;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Application implements Runnable {

	private Server server;
	private boolean started;
	private Object startedNotification;
	
	public Application() {
		startedNotification = new Object();
	}

	public void run() {	
		try {
			synchronized (startedNotification) {
				started = false;
				server = new Server(8080);
				
				ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
				context.setContextPath("/");
				context.addServlet(GameOfLifeServlet.class, "/plane");
				
			    ResourceHandler resource_handler = new ResourceHandler();
			    resource_handler.setDirectoriesListed(true);
			    // how to point to a dynamic page?
			    resource_handler.setWelcomeFiles(new String[]{ "index.html" });
				// TODO: security problem, should be a subfolder			 
			    resource_handler.setResourceBase("src/main/resources/static");
			    
			    HandlerList handlers = new HandlerList();
				handlers.setHandlers(new Handler[] { resource_handler, context });
			    server.setHandler(handlers);
			 	server.start();
				started = true;	
				startedNotification.notify();
			}
				//server.dumpStdErr();
			server.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
