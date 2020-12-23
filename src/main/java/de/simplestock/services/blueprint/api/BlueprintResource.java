package de.simplestock.services.blueprint.api;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Collections;

/**
 * A sample REST resource.
 */
@Path("blueprint")
@RequestScoped
public class BlueprintResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Object> getBlueprints() {
        return Collections.emptyList();
    }

}
