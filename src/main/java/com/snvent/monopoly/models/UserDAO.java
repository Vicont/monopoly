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
     * Retrieve user by login and password
     *
     * @param login Login
     * @param password Password
     * @return User
     */
    User getByLoginAndPassword(String login, String password);

}
