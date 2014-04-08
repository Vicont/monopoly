package com.snvent.monopoly.models;

import com.snvent.core.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * User session DAO implementation
 *
 * @author vicont
 */
public class UserSessionDAOImpl implements UserSessionDAO {

    @Override
    public void add(UserSession userSession) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(userSession);
        session.getTransaction().commit();
    }

    @Override
    public void remove(UserSession userSession) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(userSession);
        session.getTransaction().commit();
    }

    @Override
    public void removeByUserId(Integer userId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(UserSession.class).add(Restrictions.eq("userId", userId));
        session.delete(criteria.uniqueResult());
        session.getTransaction().commit();
    }

    @Override
    public UserSession getById(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        UserSession userSession = (UserSession) session.get(UserSession.class, id);
        session.getTransaction().commit();
        return userSession;
    }

}
