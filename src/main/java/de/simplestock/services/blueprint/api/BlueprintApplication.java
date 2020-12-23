package de.simplestock.services.blueprint.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import de.simplestock.services.stock.StockResource;

import java.util.Set;

/**
 * JAX-RS application. Any REST root resources must be listed here.
 */
@ApplicationPath("service")
public class BlueprintApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
    	return Set.of(StockResource.class);
    }

}
