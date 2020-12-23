package de.simplestock.services.blueprint.infrastructure.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

/**
 * Registers the service with the service registry.
 */
@ApplicationScoped
public class ServiceRegistryService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ServiceRegistryService.class);

    /**
     * Registers the service.
     */
    public void registerService() {
        // Only left in for reference purposes, not necessary for exams
        //
        // serviceRegistration = ServiceRegistry
        //         .createRegistration("this.service.id:v1", "/service/")
        //         .build();
        //
        // ServiceRegistry.submitRegistration(serviceRegistration);
        //
        // logger.info("Service registered as {}", serviceRegistration);
    }

}
