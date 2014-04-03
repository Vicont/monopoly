package com.snvent.monopoly.models;

/**
 * DAO Factory
 *
 * @author vicont
 */
public class DAOFactory {

    /**
     * Instance of factory
     */
    private static DAOFactory instance;

    /**
     * User DAO instance
     */
    private static UserDAO userDAO;

    /**
     * Retrieve factory instance
     *
     * @return Factory instance
     */
    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    /**
     * Retrieve User DAO instance
     *
     * @return User DAO instance
     */
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }

}
