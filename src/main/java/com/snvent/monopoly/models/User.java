package com.snvent.monopoly.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * User
 *
 * @author vicont
 */
@Entity
@Table(name = "User")
public class User {

    private Integer id;

    private String login;

    private String password;

    private String nick;

    private Integer registrationTime;

    private Integer lastLoginTime;

    public User() {
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "nick")
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Column(name = "registrationTime")
    public Integer getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Integer registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Column(name = "lastLoginTime")
    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

}
