package com.snvent.monopoly.models;

/**
 * User DAO interface
 *
 * @author vicont
 */
public interface UserDAO {

    /**
     * Add new user
     *
     * @param user User
     */
    void add(User user);

    /**
     * Update specified user
     *
     * @param user User
     */
    void update(User user);

    /**
     * Remove specified user
     *
     * @param user User
     */
    void remove(User user);

    /**
     * Retrieve user by ID
     *
     * @param id ID
     * @return User
     */
    User getById(Integer id);

}
