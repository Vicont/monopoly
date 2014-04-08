package com.snvent.monopoly.services.frontend;

import com.snvent.monopoly.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for logged users
 *
 * @author vicont
 */
public class UserStorage {

    /**
     * Users map
     */
    private Map<Integer, User> users = new HashMap<>();

    /**
     * Add new user to storage
     *
     * @param user User
     */
    public void add(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Check whether storage contains user with specified ID
     *
     * @param userId User ID
     * @return True if storage contains user
     */
    public boolean has(Integer userId) {
        return users.containsKey(userId);
    }

    /**
     * Retrieve user by ID
     *
     * @param userId User ID
     * @return User
     */
    public User get(Integer userId) {
        return users.get(userId);
    }

    /**
     * Remove user from storage
     *
     * @param user User
     */
    public void remove(User user) {
        users.remove(user.getId());
    }

}
