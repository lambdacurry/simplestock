package de.simplestock.services.blueprint.infrastructure.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Controls all steps that are necessary during startup of the service.
 */
@Singleton
@Startup
public class BlueprintStartupBean {

    private static final Logger logger = LoggerFactory.getLogger(BlueprintStartupBean.class);

    private final ServiceRegistryService serviceRegistryService;

    protected BlueprintStartupBean() {
        this.serviceRegistryService = null;
    }

    /**
     * Creates an instance and receives all class dependencies via CDI.
     *
     * @param serviceRegistryService The service registry service.
     */
    @Inject
    public BlueprintStartupBean(ServiceRegistryService serviceRegistryService) {
        this.serviceRegistryService = serviceRegistryService;
    }

    /**
     * Called by CDI after the instance creation.
     */
    @PostConstruct
    public void init() {
        logger.info("Starting service");
        serviceRegistryService.registerService();
        logger.info("Started service");
    }

}
