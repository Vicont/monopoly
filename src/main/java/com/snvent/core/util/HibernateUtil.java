package com.snvent.core.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate utility
 *
 * @author vicont
 */
public class HibernateUtil {

    /**
     * Hibernate session factory
     */
    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    /**
     * Retrieve hibernate session factory
     *
     * @return Session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
