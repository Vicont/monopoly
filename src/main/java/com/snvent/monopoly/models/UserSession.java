package com.snvent.monopoly.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Session
 *
 * @author vicont
 */
@Entity
@Table(name = "UserSession")
public class UserSession {

    @Id
    @GeneratedValue(generator = "UserSessionIdGenerator")
    @GenericGenerator(name = "UserSessionIdGenerator", strategy = "com.snvent.monopoly.models.UserSessionIdGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "lastActivityTime")
    private Integer lastActivityTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Integer lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

}
