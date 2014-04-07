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

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "nick")
    private String nick;

    @Column(name = "registrationTime")
    private Integer registrationTime;

    @Column(name = "lastLoginTime")
    private Integer lastLoginTime = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Integer registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

}
