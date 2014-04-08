package com.snvent.monopoly.commands.incoming;

import com.snvent.core.http.annotation.command.IncomingCommand;

/**
 * Login
 *
 * @author vicont
 */
@IncomingCommand
public class Login {

    public String login;

    public String password;

}
