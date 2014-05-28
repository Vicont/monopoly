package com.snvent.monopoly.services.frontend;

import com.snvent.monopoly.models.User;
import com.snvent.monopoly.models.UserSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for logged users
 *
 * @author vicont
 */
public class UserSessionStorage {

    /**
     * Entity contains user with linked session
     */
    private class Entity {

        /**
         * User
         */
        private User user;

        /**
         * Session
         */
        private UserSession session;

        /**
         * Constructor
         *
         * @param user User
         * @param session UserSession
         */
        public Entity(User user, UserSession session) {
            this.user = user;
            this.session = session;
        }

        /**
         * Retrieve user
         *
         * @return User
         */
        public User getUser() {
            return user;
        }

        /**
         * Retrieve session
         *
         * @return UserSession
         */
        public UserSession getSession() {
            return session;
        }

    }

    /**
     * Entity map
     */
    private Map<Integer, Entity> users = new HashMap<>();

    /**
     * Add new entity to storage
     *
     * @param user User
     */
    public void add(User user, UserSession session) {
        Entity entity = new Entity(user, session);
        users.put(user.getId(), entity);
    }

    /**
     * Retrieve user by ID
     *
     * @param userId User ID
     * @return User
     */
    public User get(Integer userId) {
        Entity entity = users.get(userId);

        if (entity != null) {
            return entity.getUser();
        } else {
            return null;
        }
    }

    /**
     * Remove user from storage
     *
     * @param user User
     */
    public void remove(User user) {
        users.remove(user.getId());
    }

    /**
     * Check whether storage contains user with specified ID
     *
     * @param userId User ID
     * @return True if storage contains user
     */
    public boolean hasUser(Integer userId) {
        return users.containsKey(userId);
    }

    /**
     * Check whether storage contains session with specified ID
     *
     * @param sessionId Session ID
     * @return True if storage contains session
     */
    public boolean hasSession(String sessionId) {
        return users.entrySet()
                .stream()
                .anyMatch(entry -> entry.getValue().getSession().getId().equals(sessionId));
    }

}
