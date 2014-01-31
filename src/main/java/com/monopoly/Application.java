package com.monopoly;

import com.monopoly.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Game application
 *
 * @author vicont
 */
public class Application implements IApplication {

    /**
     * List of available services
     */
    private List<IService> services;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /**
     * Constructor
     */
    public Application() {
    }

    /**
     * Set available services
     *
     * @param services List of application services
     */
    public void setServices(List<IService> services) {
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

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath*:/beans/beans.xml"}, false);
        context.getEnvironment().setActiveProfiles("development");
        context.refresh();

        IApplication app = context.getBean("application", IApplication.class);
        app.start();

        log.info("Application started");
    }

    @Override
    public void start() {
        if (services == null || services.size() == 0) {
            throw new RuntimeException("Any application service hasn't been found");
        }

        for (IService service : services) {
            service.activate();
        }
    }

    @Override
    public void stop() {
        for (IService service : services) {
            service.shutdown();
        }
    }

}
