package com.snvent.monopoly.models;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.util.DigestUtils;

import java.io.Serializable;

/**
 * Generator for user session UID
 *
 * @author vicont
 */
public class UserSessionIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object object) throws HibernateException {
        UserSession userSession = (UserSession) object;
        byte[] bytes = (String.valueOf(System.nanoTime()) + "_" + userSession.getUserId().toString()).getBytes();
        return DigestUtils.md5DigestAsHex(bytes);
    }

}
