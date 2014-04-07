package com.snvent.monopoly.models;

/**
 * User session DAO interface
 *
 * @author vicont
 */
public interface UserSessionDAO {

    /**
     * Add new user session
     *
     * @param userSession UserSession
     */
    void add(UserSession userSession);

    /**
     * Remove specified user session
     *
     * @param userSession UserSession
     */
    void remove(UserSession userSession);

    /**
     * Retrieve user session by ID
     *
     * @param id ID
     * @return UserSession
     */
    UserSession getById(String id);

}
