package de.simplestock.services.stock;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

/**
 * Simple Jetty Server.
 * <p><strong>NOTE:</strong> this server was made only because it is for some
 * reason not possible to deploy the maven package on the tomcat (gives 404).
 * So use only for testing purposes.</p>
 */
public class SimpleJettyServer {
    private Server server;
	
    public void run() {
        JAXRSServerFactoryBean sfb = new JAXRSServerFactoryBean();
        sfb.setResourceClasses(StockResource.class);
        sfb.setResourceProvider(StockResource.class,
            new SingletonResourceProvider(new StockResource()));
        sfb.setAddress("http://localhost:8090/");
    	server = sfb.create();
    }
    
    public void stop() {
    	server.stop();
    	server.destroy();
    }

    public static void main(String[] args) throws Exception {
        new SimpleJettyServer().run();
    }
}