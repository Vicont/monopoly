package com.snvent.core;

import com.snvent.core.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Game application
 *
 * @author vicont
 */
public class GameApplication implements Application {

    /**
     * List of available services
     */
    private List<Service> services;

    /**
     * Server settings
     */
    private static ServerSettings settings;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(GameApplication.class);

    /**
     * Constructor
     */
    public GameApplication() {
    }

    /**
     * Set available services
     *
     * @param services List of application services
     */
    public void setServices(List<Service> services) {
        if (this.services == null) {
            this.services = services;
        }
    }

    /**
     * Entry point
     *
     * @param args Arguments list
     */
    public static void main(String[] args) {
        log.info("Application is starting...");

        settings = new ServerSettings();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"beans/beans.xml"}, false);
        context.getEnvironment().setActiveProfiles(settings.base.environment());
        context.refresh();

        Application app = context.getBean(Application.class);
        app.start();

        log.info("Application started");
    }

    @Override
    public void start() {
        if (services == null || services.size() == 0) {
            throw new RuntimeException("Any application service hasn't been found");
        }

        for (Service service : services) {
            service.activate();
        }
    }

    @Override
    public void stop() {
        for (Service service : services) {
            service.shutdown();
        }
    }

    @Override
    public ServerSettings getSettings() {
        return settings;
    }

}
