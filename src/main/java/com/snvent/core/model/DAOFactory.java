package com.snvent.core.model;

import java.util.Map;

/**
 * DAO Factory
 *
 * @author vicont
 */
public class DAOFactory {

    /**
     * All DAO instances used in application
     */
    private Map<Class<?>, Object> daoMap;

    /**
     * Set DAO instances map
     *
     * @param daoMap DAO map
     */
    public void setDaoMap(Map<Class<?>, Object> daoMap) {
        this.daoMap = daoMap;
    }

    /**
     * Get DAO instance by class
     *
     * @param daoClass DAO class
     * @param <T> DAO type
     * @return DAO instance
     */
    public <T> T getDAO(Class<T> daoClass) {
        Object dao = this.daoMap.get(daoClass);

        if (dao == null) {
            String message = String.format("DAO implementation of <%s> not found", daoClass.getSimpleName());
            throw new DAOException(message);
        }

        return daoClass.cast(dao);
    }

}
