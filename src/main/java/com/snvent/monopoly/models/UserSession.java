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

    @Column(name = "startDate")
    private Integer startDate;

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

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

}
